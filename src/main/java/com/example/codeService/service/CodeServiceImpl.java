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
        // Create initial status
        Status status = new Status("started compiling.....", new Date());
        List<Status> statuses = Arrays.asList(status);

        // Map CodeRequest to CodeDto
        CodeDto codeDto = CodeDto.builder()
                .content(codeRequest.getContent())
                .language(codeRequest.getLanguage())
                .build();

        // Build Code object
        Code code = Code.builder()
                .language(codeDto.getLanguage())
                .content(codeDto.getContent())
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
    public CodeDto getCurrentStatus(String projectId) {
        try{
            Code currCode = getCodeById(projectId);
            return  CodeDto.builder()
                    .content(currCode.getStatus().get(currCode.getStatus().size()-1).getStatus())
                    .language(currCode.getLanguage())
                    .projectId(currCode.getProjectId())
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

}
