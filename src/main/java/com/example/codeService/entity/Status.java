package com.example.codeService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class Status {

    private String status;

    @CreatedDate
    @Field("date")
    private Date date = new Date();

}
