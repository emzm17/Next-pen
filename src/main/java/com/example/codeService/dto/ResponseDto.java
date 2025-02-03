package com.example.codeService.dto;


import lombok.*;

import java.util.List;


@Getter
@Setter// Lombok will generate a constructor with all fields
@AllArgsConstructor
public class ResponseDto {

    private int statusId;
    private List<String> status;
    private String projectId;
}