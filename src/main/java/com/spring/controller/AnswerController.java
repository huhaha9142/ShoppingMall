package com.spring.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.AnswerVO;
import com.spring.service.AnswerServiceImpl;
import com.spring.service.S3Uploader;

@Controller
public class AnswerController {
	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	private static String URL_PATH="https://shoppingmal.s3.ap-northeast-2.amazonaws.com/";	

	@Autowired
	private AnswerServiceImpl answerService;
	@Inject
	private S3Uploader s3Uploader;
	
		//	Answer SELECT
		@CrossOrigin(origins = "*", allowedHeaders = "*")
		@RequestMapping(value = "/answer",method = RequestMethod.GET,produces = "application/json; charset=utf8")
		@ResponseBody
		public String answerList(@RequestParam("qnaNumber") Long qnaNumber)
		{
			JSONObject jsonObject1 = new JSONObject();
			JSONArray jsonArarry1 = new JSONArray();
			AnswerVO vo = new AnswerVO();
			vo.setQnaNumber(qnaNumber);
			List<AnswerVO> sql = answerService.selectListAnswer(vo);
			
			for(int i=0;i<sql.size();i++)
			{
				String[] image1 = sql.get(i).getImage().split(",");
				JSONObject list1 = new JSONObject();
				JSONObject imgJ1 = new JSONObject();
				JSONArray jsonimg1 = new JSONArray();
				for(String img:image1)
				{
					if(img!="")
						jsonimg1.add(URL_PATH + img);
				}	
				imgJ1.put("image", jsonimg1);
				list1.put("images", imgJ1);
				list1.put("index", i);
				list1.put("content", sql.get(i).getContent());
				list1.put("title", sql.get(i).getTitle());
				list1.put("answerNumber", sql.get(i).getAnswerNumber());
				list1.put("qnaNumber", sql.get(i).getQnaNumber());
				list1.put("inDate", sql.get(i).getInDate());
				list1.put("regDate", sql.get(i).getRegDate());
				jsonArarry1.add(list1);
			}
			
		jsonObject1.put("answer", jsonArarry1);
		return jsonObject1.toString();
		}
		
		//	Answer INSERT
		@CrossOrigin(origins = "*", allowedHeaders = "*")
		@RequestMapping(value="/answer",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	    @ResponseBody
	    public String answerInsert(
	    		@RequestParam("title") String title,
	    		@RequestParam("content") String content,
	    		@RequestParam("qnaNumber") Long qnaNumber,
	    		@RequestParam(value="image", required=false) List<MultipartFile> imageAnswer
	    		) throws IOException
	    {
			JSONObject json = new JSONObject();
			AnswerVO vo = new AnswerVO();
			if(imageAnswer.size()==0)
			{
				vo.setTitle(title);
				vo.setContent(content);
				vo.setInDate(new Date());
				vo.setRegDate(new Date());
				vo.setQnaNumber(qnaNumber);
				vo.setImage("noData");
				boolean insert = answerService.insertAnswer(vo);
				String result = (insert==true)?"insert":"fail";
				json.put("result", result);
				return json.toString();
				
			}
			
			String url="";
	    	for(int i=0;i<imageAnswer.size();i++)
			{
		   		url += s3Uploader.upload(imageAnswer.get(i), "answer");
	    		url +=",";
			}
		    	vo.setTitle(title);
				vo.setContent(content);
				vo.setInDate(new Date());
				vo.setRegDate(new Date());
				vo.setQnaNumber(qnaNumber);
				vo.setImage("noData");
				boolean insert = answerService.insertAnswer(vo);
				String result = (insert==true)?"insert":"fail";
				json.put("result", result);
				return json.toString();
			
			}
		
		//	Answer UPDATE
		@CrossOrigin(origins = "*", allowedHeaders = "*")  
	    @RequestMapping(
	  		  value = "/answer/{answerNumber}",method = RequestMethod.POST,produces = "application/json; charset=utf8")
		@ResponseBody
		public String answerUpdate(@PathVariable("answerNumber") String answerNumber,
	    		@RequestParam("title") String title,
	    		@RequestParam("content") String content,
	    		@RequestParam(value="image",required=false) List<MultipartFile> imageAnswer	
				) throws IOException
		{
			JSONObject json = new JSONObject();
			AnswerVO vo = new AnswerVO();
			if(imageAnswer.size()==0)
			{
				vo.setTitle(title);
				vo.setContent(content);
				vo.setRegDate(new Date());
				vo.setImage("noData");
				vo.setAnswerNumber(Long.valueOf(answerNumber));
				boolean insert = answerService.insertAnswer(vo);
				String result = (insert==true)?"update":"fail";
				json.put("result", result);
				return json.toString();
			}
			String url="";
			for(int i=0; i<imageAnswer.size(); i++)
			{
				url += s3Uploader.upload(imageAnswer.get(i), "answer");
				url += ",";
			}
			vo.setTitle(title);
			vo.setContent(content);
			vo.setRegDate(new Date());
			vo.setImage(url);
			vo.setAnswerNumber(Long.valueOf(answerNumber));
			boolean insert = answerService.insertAnswer(vo);
			String result = (insert==true)?"update":"fail";
			json.put("result", result);
			return json.toString();
		}
		
		//	Answer DELETE
		@CrossOrigin(origins = "*", allowedHeaders = "*")  
	    @RequestMapping(
	  		  value = "/answer/{answerNumber}",method = RequestMethod.DELETE,produces = "application/json; charset=utf8")
		@ResponseBody
		public String answerDelete(@PathVariable("answerNumber") String answerNumber)
		{
			JSONObject json = new JSONObject();
			AnswerVO vo = new AnswerVO();
			try {
				vo.setAnswerNumber(Long.valueOf(answerNumber));
			} catch(Exception e)
			{
				json.put("error", e);
				return json.toString();
			}
			String imgurl = answerService.selectImage(vo);
			boolean delete = answerService.deleteAnswer(vo);
			
			String result = (delete==true)?"delete":"fail";
			json.put("result", result);
			return json.toString();

		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	 }

