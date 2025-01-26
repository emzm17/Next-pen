package com.example.codeService.dto;


import lombok.*;


@Getter
@Setter// Lombok will generate a constructor with all fields
@AllArgsConstructor
public class ResponseDto {
    private String status;
    private String projectId;
}