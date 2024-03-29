package com.example.aws.demo.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.aws.demo.entities.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
public class S3Service {
 @Autowired
 private AmazonS3 s3Client;
 @Autowired
 private FileMetaDataService fileMetaDataService;

 @Value("${application.bucket.name}")
 private String bucketName;

 public String uploadFile(MultipartFile multipartFile, String userId){
     String fileName=this.getFileName(multipartFile);
     try{
         File fileObj=this.convertMultiPartFileToFile(multipartFile);
         s3Client.putObject(new PutObjectRequest(bucketName, fileName,fileObj));
         FileMetadata fileMetadata=FileMetadata.builder()
                 .fileName(fileName)
                 .fileSize(multipartFile.getSize())
                 .userId(userId)
                 .build();
         fileMetaDataService.save(fileMetadata);
         fileObj.delete();
         return fileName;
     }catch(AmazonServiceException e){
         throw new RuntimeException("Error during file upload to S3", e);
     }
 }
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error converting multipart file to file", e);
        }
        return convertedFile;
    }
 private String getFileName(MultipartFile multipartFile){
     return new Date().getTime()+"-"+ Objects.requireNonNull(multipartFile.getOriginalFilename()).replace(" ","-");
 }
}
