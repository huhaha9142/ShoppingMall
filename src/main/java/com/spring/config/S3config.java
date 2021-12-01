package com.spring.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.Yaml; 

public class S3config { 
		public S3config() {
			try {
				Map<String, Object> propMap = new Yaml().load(new FileReader("aws.yml")); 
				HashMap<String, Object> cloud = (HashMap<String, Object>)propMap.get("cloud");
				HashMap<String, Object> aws = (HashMap<String, Object>)cloud.get("aws");
				HashMap<String, String> credentials = (HashMap<String, String>)aws.get("credentials");
				HashMap<String, String> s3 = (HashMap<String, String>)aws.get("s3");
				HashMap<String, String> region = (HashMap<String, String>)aws.get("region");
				HashMap<String, String> stack = (HashMap<String, String>)aws.get("stack");
				setAccessKey(credentials.get("accessKey"));
				setBucket(s3.get("bucket"));
				setRegion(region.get("static"));
				setSecretKey(credentials.get("secretKey"));
				System.out.println("값 세팅 확인 ex: "+ getBucket());
			} catch (FileNotFoundException e) {
				e.printStackTrace(); 
			}
		}
		private String accessKey;

	    public String getAccessKey() {
			return accessKey;
		}

		public void setAccessKey(String accessKey) {
			this.accessKey = accessKey;
		}

		public String getSecretKey() {
			return secretKey;
		}

		public void setSecretKey(String secretKey) {
			this.secretKey = secretKey;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getBucket() {
			return bucket;
		}

		public void setBucket(String bucket) {
			this.bucket = bucket;
		}

		private String secretKey;

	    private String region;
	    
	    private String bucket;
	
		
}
		
		
		
	

