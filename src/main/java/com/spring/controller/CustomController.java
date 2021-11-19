package com.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

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
import com.spring.function.FunctionSpring;
import com.spring.service.CustomServiceImpl;

@Controller
public class CustomController {
	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample/com/custom-image/";
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/custom/";

	@Inject
	private CustomServiceImpl cusService;
	
	// custom한 제품을 등록하는  API
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/customs",method = RequestMethod.POST,produces = "application/json; charset=utf8")
    @ResponseBody
    public String customInsert(
    		@RequestParam("image") List <MultipartFile> image,
    		@RequestParam("productNumber") Long productNumber,
    		@RequestParam("userNumber") Long userNumber,
    		@RequestParam("size") String size,
    		@RequestParam("color") String color
    		) throws IOException 
    {
		JSONObject jsonObject = new JSONObject();
		CustomVO vo = new CustomVO();
		vo.setProductNumber(Long.valueOf(productNumber));
		// 로그인 이랑 연계
		vo.setUserNumber(Long.valueOf(userNumber));
		vo.setSize(size);
		vo.setColor(color);
		vo.setImage(FunctionSpring.fileSave(image, SAVE_PATH));
		vo.setInDate(new Date());
		vo.setRegDate(new Date());
		boolean insert = cusService.insertCustom(vo);
		String result =(insert==true)?"insert":"fail";
		jsonObject.put("result", result);
		// 쿼리가 실패한다면 저장된 파일을 삭제한다
		if(result.equals("fail"))
			FunctionSpring.fileDelete(vo.getImage(), SAVE_PATH);
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
			list.put("index", i);
			list.put("inDate", sql.get(i).getInDate());
			list.put("regDate", sql.get(i).getRegDate());
			list.put("image", URL_PATH+image[0]);
			list.put("size", sql.get(i).getSize());
			list.put("color", sql.get(i).getColor());
			list.put("user_number", sql.get(i).getUserNumber());
			list.put("product", sql.get(i).getProduct());
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
		vo.setImage(FunctionSpring.fileSave(image, SAVE_PATH));
		boolean update = cusService.updateCustom(vo);
		String result =(update==true)?"update":"fail";
		jsonObject.put("result", result);
		// 쿼리가 실패한다면 저장된 파일을 삭제한다
		if(result.equals("fail"))
			FunctionSpring.fileDelete(vo.getImage(), SAVE_PATH);
		return jsonObject.toString();
    }
	
	// 커스텀 제품의 삭제
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/customs/{customNumber}",method = RequestMethod.DELETE,produces = "application/json; charset=utf8")
    @ResponseBody
    public String deleteCustom(@PathVariable("customNumber") String customNumber) {
		JSONObject jsonObject = new JSONObject();
		CustomVO vo = new CustomVO();
		vo.setCustomNumber(Long.valueOf(customNumber));
		boolean delete = cusService.deleteCustom(vo);
		String result =(delete==true)?"delete":"fail";
		jsonObject.put("result", result);
		
//		if(result.equals("delete"))
//			FunctionSpring.fileDelete(customNumber, result)
		return jsonObject.toString();
	}
	
	
	
	// TODO: 아무나 커스텀 이미지에 접근 할 수 없도록 로그인 조건을 추가해 봐야겠음.!
	// 커스텀 제품의 이미지를 보내주는 API
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/com/custom-image/{img}",method = RequestMethod.GET
  		 ,produces = MediaType.IMAGE_JPEG_VALUE
  		  )
    @ResponseBody 
	public byte[] getImageWithMediaType(@PathVariable("img") String img) throws IOException {
    	String url = "/com/image/custom/"+img+".png";
    	System.out.println(url);
	    InputStream in = getClass().getResourceAsStream(url);
	    System.out.println(img+".png");
	    return IOUtils.toByteArray(in);
	}
	
	

}
