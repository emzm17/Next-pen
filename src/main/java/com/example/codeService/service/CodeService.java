package com.example.codeService.service;

import com.example.codeService.dto.CodeDto;
import com.example.codeService.entity.Code;
import com.example.codeService.entity.Status;
import com.example.codeService.request.CodeRequest;

public interface CodeService {

    String pushIntoQueue(CodeRequest codeRequest);

     CodeDto getCurrentStatus(String projectId);

     Code getCodeById(String projectId);


}
