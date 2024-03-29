package com.example.aws.demo.repositories.dynamo;

import com.example.aws.demo.entities.FileMetadata;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface FileMetadataRepository extends DynamoDBCrudRepository<FileMetadata, String> {
    List<FileMetadata> findByUserId(String userId);
}
