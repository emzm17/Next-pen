package com.example.codeService.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
public class CodeDto {
    private String language;
    private String content;
    private String projectId;

    private String input;
}
