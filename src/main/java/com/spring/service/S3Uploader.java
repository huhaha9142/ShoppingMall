package com.spring.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;

import com.amazonaws.services.s3.model.PutObjectRequest;

import com.spring.config.S3config;

@Service
public class S3Uploader {

	private AmazonS3Client amazonS3Client;
    
	S3config s3config = new S3config();
   
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    	
    	File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        return upload(uploadFile, dirName);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) throws IOException {
        String fileName = dirName + "/" + UUID.randomUUID();   // S3에 저장된 파일 이름 
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) throws IOException {	
    	this.amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard().withRegion(s3config.getRegion())
    			.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(s3config.getAccessKey(), s3config.getSecretKey())))
    			.build();
    	amazonS3Client.putObject(new PutObjectRequest(s3config.getBucket(), fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return fileName;
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
           
            return;
        }
    
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
    
}