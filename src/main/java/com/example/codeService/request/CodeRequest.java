package com.example.codeService.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeRequest {
    private String language;
    private String content;
}
