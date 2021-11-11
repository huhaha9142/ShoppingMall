package com.spring.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
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
//	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample/com/image/custom/";
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/custom/";

	@Inject
	private CustomServiceImpl cusService;
	
	// custom한 제품을 등록하는  API
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/custom",method = RequestMethod.POST,produces = "application/json; charset=utf8")
    @ResponseBody
    public String customInsert(
    		@RequestParam("quantity") Long quantity,
    		@RequestParam("price") Long price,
    		@RequestParam("image") List <MultipartFile> image,
    		@RequestParam("productNumber") Long productNumber,
    		@RequestParam("userNumber") Long userNumber,
    		@RequestParam("size") String size,
    		@RequestParam("color") String color
    		) throws IOException 
    {
		JSONObject jsonObject = new JSONObject();
		CustomVO vo = new CustomVO();
		vo.setQuantity(Long.valueOf(quantity));
		vo.setPrice(Long.valueOf(price));
		vo.setProductNumber(Long.valueOf(productNumber));
		// 로그인 이랑 연계
		vo.setUserNumber(Long.valueOf(userNumber));
		vo.setSize(size);
		vo.setColor(color);
		vo.setImage(FunctionSpring.fileSave(image, SAVE_PATH));
		boolean insert = cusService.insertCustom(vo);
		String result =(insert==true)?"insert":"fail";
		jsonObject.put("result", result);
		// 쿼리가 실패한다면 저장된 파일을 삭제한다
		if(result.equals("fail"))
			FunctionSpring.fileDelete(vo.getImage(), SAVE_PATH);
		return jsonObject.toString();
    }
	
	
	
	
	
	// 리뷰의 이미지를 보내주는 API
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/com/customImage/{img}",method = RequestMethod.GET
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
