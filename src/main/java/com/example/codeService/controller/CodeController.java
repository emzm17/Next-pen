package com.example.codeService.controller;

import com.example.codeService.dto.CodeDto;
import com.example.codeService.dto.ResponseDto;
import com.example.codeService.request.CodeRequest;
import com.example.codeService.response.ApiResponse;
import com.example.codeService.service.CodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CodeController {

    private final CodeServiceImpl codeService;

    @PostMapping("/api/v1/")
    public ResponseEntity<ApiResponse> pushCode(@RequestBody CodeRequest codeRequest) {
             String projectId=codeService.pushIntoQueue(codeRequest);
             CodeDto codeDto = codeService.getCurrentStatus(projectId);
             ApiResponse apiResponse = new ApiResponse("successful",new ResponseDto(codeDto.getContent(),projectId));
             return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/api/v1/{projectId}")
    public ResponseEntity<ApiResponse> statusCode(@PathVariable("projectId") String projectId){
        CodeDto codeDto = codeService.getCurrentStatus(projectId);
        ApiResponse apiResponse = new ApiResponse("status",new ResponseDto(codeDto.getContent(),projectId));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
