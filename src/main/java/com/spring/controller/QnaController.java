package com.spring.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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
import com.spring.dto.QnaVO;
import com.spring.service.AnswerServiceImpl;
import com.spring.service.QnaServiceImpl;
import com.spring.service.S3Uploader;

@Controller
public class QnaController {
	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	private static String URL_PATH="https://shoppingmal.s3.ap-northeast-2.amazonaws.com/";	
	@Autowired
	private QnaServiceImpl qnaService;
	@Autowired
	private AnswerServiceImpl answerService;
	@Inject
	private S3Uploader s3Uploader;
	
	String[] orState = {"답변 대기", "답변 완료", "FAQ"};
	
	
		//	Qna SELECT
		//	비로그인시 qna게시판 목록
		@CrossOrigin(origins = "*", allowedHeaders = "*")
		@RequestMapping(value = "/qna",method = RequestMethod.GET,produces = "application/json; charset=utf8")
		@ResponseBody
		public String qnaList()
		{
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArarry = new JSONArray();
			List<QnaVO> sql = qnaService.selectListQna();
		
			for(int i=0;i<sql.size();i++)
			{
				String[] image = sql.get(i).getImage().split(",");
				JSONObject list = new JSONObject();
				JSONObject imgJ = new JSONObject();
				JSONArray jsonimg = new JSONArray();
				for(String img:image)
				{
					if(img!="") 
						jsonimg.add(URL_PATH+img);
				}	
				String state = orState[sql.get(i).getState().intValue()];
				list.put("state", state);				
				imgJ.put("image", jsonimg);
				list.put("images", imgJ);
				list.put("index", i);
				list.put("content", sql.get(i).getContent());
				list.put("title", sql.get(i).getTitle());
				list.put("usersNumber", sql.get(i).getUsersNumber());
				list.put("qnaNumber", sql.get(i).getQnaNumber());
//				list.put("inDate", sql.get(i).getInDate());
//				list.put("regDate", sql.get(i).getRegDate());
				list.put("userId", sql.get(i).getId());
				list.put("userName", sql.get(i).getName());
				JSONObject jsonObjectAns = new JSONObject();
				JSONArray jsonArarryAns = new JSONArray();
				AnswerVO vo = new AnswerVO();
				vo.setQnaNumber(sql.get(i).getQnaNumber());
				List<AnswerVO> sqlAns = answerService.selectListAnswer(vo);
				
				for(int j=0; j<sqlAns.size(); j++)
				{
					String[] imageAns = sql.get(j).getImage().split(",");
					JSONObject listAns = new JSONObject();
					JSONObject imgJAns = new JSONObject();
					JSONArray jsonimgAns = new JSONArray();
					for(String img:imageAns)
					{
						if(img!="")
							jsonimgAns.add(URL_PATH + img);
					}	
					imgJAns.put("image", jsonimgAns);
					listAns.put("images", imgJAns);
					listAns.put("index", j);
					listAns.put("content", sqlAns.get(j).getContent());
					listAns.put("title", sqlAns.get(j).getTitle());
					listAns.put("answerNumber", sqlAns.get(j).getAnswerNumber());
					listAns.put("qnaNumber", sqlAns.get(j).getQnaNumber());
//					listAns.put("inDate", sqlAns.get(j).getInDate());
//					listAns.put("regDate", sqlAns.get(j).getRegDate());
					jsonArarryAns.add(listAns);
				}
				
				jsonObjectAns.put("answer", jsonArarryAns);
				list.put("answers", jsonObjectAns);
				jsonArarry.add(list);
			}
			
		jsonObject.put("qna", jsonArarry);
		return jsonObject.toString(); 
	
		}
		//	Qna SELECT
		//	로그인시 qna 게시판 목록
		@CrossOrigin(origins = "*", allowedHeaders = "*")
		@RequestMapping(value = "/qnauser",method = RequestMethod.GET,produces = "application/json; charset=utf8")
		@ResponseBody
		public String qnaListUser(@RequestParam("userNumber") Long userNumber)
		{
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArarry = new JSONArray();
			QnaVO vo = new QnaVO();
			vo.setUsersNumber(userNumber);
			List<QnaVO> sql = qnaService.selectListUserQna(vo);
			
			for(int i=0;i<sql.size();i++)
			{
				String[] image = sql.get(i).getImage().split(",");
				JSONObject list = new JSONObject();
				JSONObject imgJ = new JSONObject();
				JSONArray jsonimg = new JSONArray();
				for(String img:image)
				{
					if(img!="")
						jsonimg.add(URL_PATH + img);
				}
				String state = orState[sql.get(i).getState().intValue()];
				list.put("state", state);	
				imgJ.put("image", jsonimg);
				list.put("images", imgJ);
				list.put("index", i);
				list.put("content", sql.get(i).getContent());
				list.put("title", sql.get(i).getTitle());
				list.put("usersNumber", sql.get(i).getUsersNumber());
				list.put("qnaNumber", sql.get(i).getQnaNumber());
//				list.put("inDate", sql.get(i).getInDate());
//				list.put("regDate", sql.get(i).getRegDate());
				list.put("userId", sql.get(i).getId());
				list.put("userName", sql.get(i).getName());
				JSONObject jsonObjectAns = new JSONObject();
				JSONArray jsonArarryAns = new JSONArray();
				AnswerVO AnsVo = new AnswerVO();
				vo.setQnaNumber(sql.get(i).getQnaNumber());
				List<AnswerVO> sqlAns = answerService.selectListAnswer(AnsVo);
				
				for(int j=0; j<sqlAns.size(); j++)
				{
					String[] imageAns = sql.get(j).getImage().split(",");
					JSONObject listAns = new JSONObject();
					JSONObject imgJAns = new JSONObject();
					JSONArray jsonimgAns = new JSONArray();
					for(String img:imageAns)
					{
						if(img!="")
							jsonimgAns.add(URL_PATH + img);
					}	
					imgJAns.put("image", jsonimgAns);
					listAns.put("images", imgJAns);
					listAns.put("index", j);
					listAns.put("content", sqlAns.get(j).getContent());
					listAns.put("title", sqlAns.get(j).getTitle());
					listAns.put("answerNumber", sqlAns.get(j).getAnswerNumber());
					listAns.put("qnaNumber", sqlAns.get(j).getQnaNumber());
//					listAns.put("inDate", sqlAns.get(j).getInDate());
//					listAns.put("regDate", sqlAns.get(j).getRegDate());
					jsonArarryAns.add(listAns);
				}
				
				jsonObjectAns.put("answer", jsonArarryAns);
				list.put("answers", jsonObjectAns);
				jsonArarry.add(list);
			}
			
		jsonObject.put("qna", jsonArarry);
		return jsonObject.toString();
		}
		
		
		//	Qna INSERT
		@CrossOrigin(origins = "*", allowedHeaders = "*")
		@RequestMapping(value="/qna",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	    @ResponseBody
	    public String qnaInsert(
				@RequestParam("content") String content,
	    		@RequestParam("title") String title,
	    		HttpServletRequest httpServletRequest,
	    		@RequestParam(value="image", required=false) List<MultipartFile> imageQna
	    		) throws IOException
	    {
			Long userNumber  = Long.valueOf((String)httpServletRequest.getAttribute("userNumber"));
			JSONObject json = new JSONObject();
			QnaVO vo = new QnaVO();
			if(imageQna.size()==0)
			{
				vo.setContent(content);
				vo.setInDate(new Date());
				vo.setTitle(title);
				vo.setUsersNumber(userNumber);
				vo.setState(0L);
				vo.setRegDate(new Date());
				vo.setImage("noData");
				boolean insert = qnaService.insertQna(vo);
				String result = (insert==true)?"insert":"fail";
				json.put("result", result);
				return json.toString();
			}
			
			String url="";
	    	for(int i=0;i<imageQna.size();i++)
			{
		   		url += s3Uploader.upload(imageQna.get(i), "qna");
	    		url +=",";
			}
				vo.setContent(content);
				vo.setInDate(new Date());
				vo.setTitle(title);
				vo.setUsersNumber(userNumber);
				vo.setState(0L);
				vo.setRegDate(new Date());
				vo.setImage(url);
				boolean insert = qnaService.insertQna(vo);
				String result = (insert==true)?"insert":"fail";
				json.put("result", result);
				return json.toString();
			}
	    
		//TODO: 쿼리에 유저넘버 추가하세용!
		//	Qna UPDATE
		@CrossOrigin(origins = "*", allowedHeaders = "*")  
	    @RequestMapping(
	  		  value = "/qna/{qnaNumber}",method = RequestMethod.POST,produces = "application/json; charset=utf8")
		@ResponseBody
		public String qnaUpdate(@PathVariable("qnaNumber") String qnaNumber,
	    		@RequestParam("content") String content,
	    		@RequestParam("title") String title,
	    		@RequestParam(value="image",required=false) List<MultipartFile> imageQna,
	    		HttpServletRequest httpServletRequest
				) throws IOException
		{
			Long userNumber  = Long.valueOf((String)httpServletRequest.getAttribute("userNumber"));
			JSONObject json = new JSONObject();
			QnaVO vo = new QnaVO();
			if(imageQna.size()==0)
			{
				vo.setContent(content);
				vo.setTitle(title);
				vo.setRegDate(new Date());
				vo.setImage("noData");
				vo.setQnaNumber(Long.valueOf(qnaNumber));
				boolean update = qnaService.updateQna(vo);
				String result = (update==true)?"update":"fail";
				json.put("result",result);
				return json.toString();
			}
			String url="";
			for(int i=0; i<imageQna.size(); i++)
			{
				url += s3Uploader.upload(imageQna.get(i), "qna");
				url += ",";
			}
			vo.setContent(content);
			vo.setTitle(title);
			vo.setRegDate(new Date());
			vo.setImage(url);
			vo.setQnaNumber(Long.valueOf(qnaNumber));
			boolean update = qnaService.updateQna(vo);
			String result = (update==true)?"update":"fail";
			json.put("result",result);
			return json.toString();
		
		}
		
		//TODO: 쿼리문에 유저넘버 추가해주세용.
		//	Qna DELETE
		@CrossOrigin(origins = "*", allowedHeaders = "*")  
	    @RequestMapping(
	  		  value = "/qna/{qnaNumber}",method = RequestMethod.DELETE,produces = "application/json; charset=utf8")
		@ResponseBody
		public String qnaDelete(@PathVariable("qnaNumber") String qnaNumber,
				HttpServletRequest httpServletRequest)
		{
			Long userNumber  = Long.valueOf((String)httpServletRequest.getAttribute("userNumber"));
			JSONObject json = new JSONObject();
			QnaVO vo = new QnaVO();
			try {
				vo.setQnaNumber(Long.valueOf(qnaNumber));
			} catch(Exception e)
			{
				json.put("error", e);
				return json.toString();
			}
			String imgurl = qnaService.selectImage(vo);
			boolean delete = qnaService.deleteQna(vo);
			
			String result = (delete==true)?"delete":"fail";
			json.put("result", result);
			return json.toString();

		}
		
}
