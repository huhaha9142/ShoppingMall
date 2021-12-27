package com.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.CustomVO;
import com.spring.service.CustomServiceImpl;
import com.spring.service.S3Uploader;

@Controller
@CrossOrigin(allowCredentials = "false")
public class CustomController {
	private static String URL_PATH="https://shoppingmal.s3.ap-northeast-2.amazonaws.com/";
	private static final Logger logger = LoggerFactory.getLogger(CustomController.class);
	@Inject
	private S3Uploader s3Uploader;
	@Inject
	private CustomServiceImpl cusService;
	// custom한 제품을 등록하는  API
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/customs",method = RequestMethod.POST,produces = "application/json; charset=utf8")
    @ResponseBody
    public String customInsert(
    		@RequestParam("image") List <MultipartFile> image,
    		@RequestParam(value="productNumber",defaultValue = "0") Long productNumber,
    		HttpServletRequest httpServletRequest,
    		@RequestParam(value="size",required=false) String size,
    		@RequestParam(value="color",required=false) String color
    		) throws IOException 
    {
		String userNumber = (String) httpServletRequest.getAttribute("userNumber");
		JSONObject jsonObject = new JSONObject();
		String customNumber = null;
		CustomVO vo = new CustomVO();
		vo.setProductNumber(Long.valueOf(productNumber));
		// 로그인 이랑 연계
		vo.setUserNumber(Long.valueOf(userNumber));
		vo.setSize(size);
		vo.setColor(color);
		String url="";
		int imageCount = image.size();
		System.out.println(imageCount);
    	for(int i=0;i<imageCount;i++)
    	{
    		url += s3Uploader.upload(image.get(i), "custom");
    		if(imageCount>1)
    		url +=",";
    	}
    	vo.setImage(url);
		vo.setInDate(new Date());
		vo.setRegDate(new Date());
		
		if(!url.equals(""))
		{
			boolean insert = cusService.insertCustom(vo);
			String result =(insert==true)?"insert":"fail";
			jsonObject.put("result", result);
			jsonObject.put("url", URL_PATH+url);
			jsonObject.put("customNumber", vo.getCustomNumber());
		}
		return jsonObject.toString();
    }
	// 커스텀 제품의 목록을 불러옴!
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/customs",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
	public String customList()
	{
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArarry = new JSONArray();
		List<CustomVO> sql=cusService.selecCustomList();
		
		for(int i=0;i<sql.size();i++)
		{
			JSONObject list = new JSONObject();
			
			String image[] = sql.get(i).getImage().split(",");
			JSONObject imgJ = new JSONObject();
			JSONArray jsonimg = new JSONArray();
			for(String img:image)
			{
				if(img!="") 
					jsonimg.add(URL_PATH+img);
			}	
			imgJ.put("image", jsonimg);
			list.put("images", imgJ);
			list.put("index", i);
			list.put("inDate", sql.get(i).getInDate());
			list.put("regDate", sql.get(i).getRegDate());
			list.put("size", sql.get(i).getSize());
			list.put("color", sql.get(i).getColor());
			list.put("user_number", sql.get(i).getUserNumber());
			list.put("product", sql.get(i).getProduct());
			list.put("customNumber", sql.get(i).getCustomNumber());
			jsonArarry.add(list);
		}
		jsonObject.put("customs", jsonArarry);
		return jsonObject.toString();
	}
	//커스텀 이미지만 (유저용)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/customs-user",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String customUserImage(HttpServletRequest httpServletRequest)
    {
		String userNumber = (String) httpServletRequest.getAttribute("userNumber");
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArarry = new JSONArray();
		CustomVO vo = new CustomVO();
		vo.setUserNumber(Long.valueOf(userNumber));
		List<CustomVO> sql = cusService.selectImage(vo);
		for(int i=0;i<sql.size();i++)
		{
			JSONObject list = new JSONObject();
			String image[] = sql.get(i).getImage().split(",");
			JSONObject imgJ = new JSONObject();
			JSONArray jsonimg = new JSONArray();
			for(String img:image)
			{
				if(img!="") 
					jsonimg.add(URL_PATH+img);
			}	
			imgJ.put("image", jsonimg);
			list.put("images", imgJ);
			list.put("index", i);
			list.put("customNumber", sql.get(i).getCustomNumber());
			jsonArarry.add(list);
		}
		jsonObject.put("customs", jsonArarry);
		
		return jsonObject.toString();
    }
	
	// 커스텀 제품의 업데이트
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/customs/{customNumber}",method = RequestMethod.POST,produces = "application/json; charset=utf8")
    @ResponseBody
    public String customUpdate(
    		@RequestParam("image") List <MultipartFile> image,
    		@RequestParam("size") String size,
    		@RequestParam("color") String color,
    		@PathVariable("customNumber") String customNumber
    		) throws IOException 
    {
		JSONObject jsonObject = new JSONObject();
		CustomVO vo = new CustomVO();
		vo.setRegDate(new Date());
		vo.setSize(size);
		vo.setColor(color);
		vo.setCustomNumber(Long.valueOf(customNumber));
		String url="";
    	for(int i=0;i<image.size();i++)
    	{
    		url += s3Uploader.upload(image.get(i), "custom");
    		url +=",";
    	}
    	vo.setImage(url);
		boolean update = cusService.updateCustom(vo);
		String result =(update==true)?"update":"fail";
		jsonObject.put("result", result);
		// 쿼리가 실패한다면 저장된 파일을 삭제한다
//		if(result.equals("fail"))
//			functionSpring.fileDelete(vo.getImage(), ".");
		return jsonObject.toString();
    }
	
	// 커스텀 제품의 삭제
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/customs/{customNumber}",method = RequestMethod.DELETE,produces = "application/json; charset=utf8")
    @ResponseBody
    public String deleteCustom(@PathVariable("customNumber") String customNumber
    		,HttpServletRequest httpServletRequest) throws IOException {
		String userNumber = (String) httpServletRequest.getAttribute("userNumber");
		JSONObject jsonObject = new JSONObject();
		CustomVO vo = new CustomVO();
		vo.setUserNumber(Long.valueOf(userNumber));
		vo.setCustomNumber(Long.valueOf(customNumber));
		CustomVO sql = cusService.selectImageUrl(vo);
		boolean delete = cusService.deleteCustom(vo);
		String result =(delete==true)?"delete":"fail";
		if(result.equals("delete"))
		{
			s3Uploader.delete(sql.getImage(), "custom");
		}
		jsonObject.put("result", result);
		
//		if(result.equals("delete"))
//			FunctionSpring.fileDelete(customNumber, result)
		return jsonObject.toString();
	}
	
	
		// 커스텀 제품의 이미지를 보내주는 API
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/customs/{customNumber}",method = RequestMethod.GET
  		  ,produces = "application/json; charset=utf8"
  		  )
    @ResponseBody 
	public String getImageWithMediaType(@PathVariable("customNumber") Long customNumber
			,HttpServletRequest httpServletRequest) throws IOException {
    	String userNumber = (String) httpServletRequest.getAttribute("userNumber");
    	JSONObject jsonObject = new JSONObject();
		CustomVO vo = new CustomVO();
		vo.setCustomNumber(customNumber);
		vo.setUserNumber(Long.valueOf(userNumber));
		CustomVO sql = cusService.selectImageUrl(vo);
		jsonObject.put("url", URL_PATH+sql.getImage());
		return jsonObject.toString();
	}
	
	

}
