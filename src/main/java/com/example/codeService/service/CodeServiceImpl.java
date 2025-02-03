package com.example.codeService.service;


import com.example.codeService.dto.CodeDto;
import com.example.codeService.entity.Code;
import com.example.codeService.entity.Status;
import com.example.codeService.exceptions.CustomException;
import com.example.codeService.exceptions.DatabaseException;
import com.example.codeService.repository.CodeRepository;
import com.example.codeService.request.CodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService{
    private final RedisQueueProducer redisQueueProducer;
    private final CodeRepository codeRepository;

    @Override
    public String pushIntoQueue(CodeRequest codeRequest) {
        try{
        List<String> statusList = new ArrayList<>();
        statusList.add("in Queue"); // Adding the first status update
        Status status = new Status( 1,statusList, new Date());
        List<Status> statuses = Arrays.asList(status);
        // Map CodeRequest to CodeDto
        CodeDto codeDto = CodeDto.builder()
                .content(codeRequest.getContent())
                .language(codeRequest.getLanguage())
                .build();

        // Build Code object
        Code code = Code.builder()
                .language(codeRequest.getLanguage())
                .content(codeRequest.getContent())
                .status(statuses)
                .build();

        // Save Code object to the database
        Code savedCode = codeRepository.save(code);

        // Set the projectId in CodeDto
        codeDto.setProjectId(savedCode.getProjectId());

        // Send message to the Redis queue
        redisQueueProducer.sendMessageToQueue(codeDto);

        // Return the saved projectId
        return savedCode.getProjectId();
      } catch (Exception  e) {
        // Catch-all for other unexpected exceptions
        throw  new CustomException("An unexpected error occurred while pushing the message into queue",e);
    }

    }

    @Override
    public Status getCurrentStatus(String projectId) {
        try{
            Code currCode = getCodeById(projectId);
            int n=currCode.getStatus().size();
            return  Status.builder()
                    .statusId(currCode.getStatus().get(n-1).getStatusId())
                    .status(currCode.getStatus().get(n-1).getStatus())
                    .build();

        } catch (Exception e) {
            throw new CustomException("Failed to fetch current status for projectId: " +projectId, e);
        }
    }

    @Override
    public Code getCodeById(String projectId) {
        try{
            return codeRepository.findByProjectId(projectId).orElseThrow(()-> new CustomException("Code not found with projectId "+ projectId));
        }catch(Exception e){
            throw new DatabaseException("Failed to retrieve code",e);
        }

    }

    @Override
    public List<String> getStatus(String projectId, int statusId) {
        List<String> statuses=new ArrayList<>();
        try{
            Code currCode = getCodeById(projectId);

            for(int i=0;i<currCode.getStatus().size();i++){
                         if(currCode.getStatus().get(i).getStatusId()==statusId){
                               statuses.addAll(currCode.getStatus().get(i).getStatus());
                         }
            }
        } catch (Exception e) {
            throw new CustomException("Failed to fetch current status for projectId: " +projectId, e);
        }
        return statuses;
    }

}
