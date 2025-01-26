package com.example.codeService.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("codes")
@Data
@AllArgsConstructor
@Builder
public class Code {

    @Id
    private String projectId;

    private String language;
    private String content;

    @Field("statuses")
    private List<Status> status;
}
