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
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.spring.config.S3config;

@Service
public class S3Uploader {

	private AmazonS3Client amazonS3Client;
    
	S3config s3config = new S3config();
   
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    	
    	File uploadFile = convert(multipartFile)  // ���� ��ȯ�� �� ������ ����
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
    	
        return upload(uploadFile, dirName);
    }
    
    public String delete(String fileName,String dirName) throws IOException
    {
    	deleteS3(fileName,dirName);
    	return "";
    }

    // S3�� ���� ���ε��ϱ�
    private String upload(File uploadFile, String dirName) throws IOException {
        String fileName = dirName + "/" + UUID.randomUUID();   // S3�� ����� ���� �̸� 
        String uploadImageUrl = putS3(uploadFile, fileName); // s3�� ���ε�
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3�� ���ε�
    private String putS3(File uploadFile, String fileName) throws IOException {	
    	this.amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard().withRegion(s3config.getRegion())
    			.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(s3config.getAccessKey(), s3config.getSecretKey())))
    			.build();
    	amazonS3Client.putObject(new PutObjectRequest(s3config.getBucket(), fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return fileName;
    }
    private String deleteS3(String fileName,String dirName) throws IOException {	
    	this.amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard().withRegion(s3config.getRegion())
    			.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(s3config.getAccessKey(), s3config.getSecretKey())))
    			.build();
    	amazonS3Client.deleteObject(new DeleteObjectRequest(s3config.getBucket(), dirName + "/" + fileName));
        return fileName;
    }
    
    // ���ÿ� ����� �̹��� �����
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
           
            return;
        }
    
    }

    // ���ÿ� ���� ���ε� �ϱ�
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // �ٷ� ������ ������ ��ο� File�� ������ (��ΰ� �߸��Ǿ��ٸ� ���� �Ұ���)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream �����͸� ���Ͽ� ����Ʈ ��Ʈ������ �����ϱ� ����
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
    
}