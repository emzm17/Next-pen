package com.example.codeService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Status {

    private int statusId;
    @Field("status")
    private List<String> status;

    @CreatedDate
    @Field("date")
    private Date date = new Date();

}
