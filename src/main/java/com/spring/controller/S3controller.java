package com.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.service.S3Uploader;


@Controller
public class S3controller {
	  @Inject
	  private S3Uploader s3Uploader;

	  @CrossOrigin(origins = "*", allowedHeaders = "*")
      @RequestMapping(value="/images",method = RequestMethod.POST,produces = "application/json; charset=utf8")
      @ResponseBody
      public String upload(@RequestParam("images") List<MultipartFile> multipartFile) throws IOException {
	    	System.out.println("load");
	    	String url="";
	    	for(int i=0;i<multipartFile.size();i++)
	    	{
	    		url += s3Uploader.upload(multipartFile.get(i), "review");
	    		url +=",";
	    	}
	        return url;
	    }
	  @CrossOrigin(origins = "*", allowedHeaders = "*")
      @RequestMapping(value="/images",method = RequestMethod.GET,produces = "application/json; charset=utf8")
      @ResponseBody
      public String upload(@RequestParam("name") String name) throws IOException {
	    	System.out.println("load");
	    	
	        return "https://shoppingmal.s3.ap-northeast-2.amazonaws.com/"+name;
	    }
	}





