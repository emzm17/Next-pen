package com.example.codeService.repository;

import com.example.codeService.entity.Code;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CodeRepository extends MongoRepository<Code,String> {
    Optional<Code> findByProjectId(String projectId);

    List<Code> findAll();
}
