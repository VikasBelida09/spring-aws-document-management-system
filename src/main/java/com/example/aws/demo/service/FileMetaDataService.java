package com.example.aws.demo.service;

import com.example.aws.demo.entities.FileMetadata;
import com.example.aws.demo.repositories.dynamo.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileMetaDataService {
    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    public FileMetadata save(FileMetadata fileMetadata){
        return fileMetadataRepository.save(fileMetadata);
    }
    public List<FileMetadata> findAllByUserId(String userId){
        return fileMetadataRepository.findByUserId(userId);
    }
}
