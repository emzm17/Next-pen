package com.example.codeService.controller;

import com.example.codeService.dto.CodeDto;
import com.example.codeService.dto.ResponseDto;
import com.example.codeService.entity.Status;
import com.example.codeService.request.CodeRequest;
import com.example.codeService.response.ApiResponse;
import com.example.codeService.service.CodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Frontend origin
public class CodeController {

    private final CodeServiceImpl codeService;

    @PostMapping(value="/api/v1/")
    public ResponseEntity<ApiResponse> pushCode(@RequestBody CodeRequest codeRequest) {
             String projectId=codeService.pushIntoQueue(codeRequest);
             Status status = codeService.getCurrentStatus(projectId);
             ApiResponse apiResponse = new ApiResponse("successful",new ResponseDto(status.getStatusId(),status.getStatus(),projectId));
             return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/api/v1/{projectId}")
    public ResponseEntity<ApiResponse> statusCode(@PathVariable("projectId") String projectId){
        Status status = codeService.getCurrentStatus(projectId);
        ApiResponse apiResponse = new ApiResponse("status",new ResponseDto(status.getStatusId(),status.getStatus(),projectId));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/api/v1/{projectId}/{statusId}")
    public ResponseEntity<ApiResponse> status(@PathVariable("projectId") String projectId, @PathVariable("statusId") int statusId){
        List<String> status = codeService.getStatus(projectId,statusId);
        ApiResponse apiResponse = new ApiResponse("status",new ResponseDto(4,status,projectId));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
