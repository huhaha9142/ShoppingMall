package com.spring.Controller;

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

import com.spring.dto.ReviewVO;
import com.spring.service.ReviewServiceImpl;
import com.spring.Controller.ProductsController;

@Controller
public class ReviewsController {
//	private static String URL_
	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample/com/reviewImage/";
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/review/";
	
	@Inject
	private ReviewServiceImpl reService;
	
	//전체 리뷰 불러오기
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/reviews",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject reviewList()
    {
		JSONObject jsonObject = new JSONObject();
    	JSONArray jsonArarry = new JSONArray();
    	List<ReviewVO> sql = reService.selectList();
    	
    	for(int i=0;i<sql.size();i++)
    	{
    		String[] image = sql.get(i).getImage().split(",");    
			JSONObject list = new JSONObject();
			JSONObject imgJ = new JSONObject();
			JSONArray jsonimg = new JSONArray();
			list.put("index", i);
			list.put("content", sql.get(i).getContent());
			list.put("title", sql.get(i).getContent());
			list.put("indate", sql.get(i).getInDate());
			for(String img:image)
			{
				if(img!="") //0번 인덱스의 빈배열을 빼기위함 
					jsonimg.add(URL_PATH+img);
			}
			imgJ.put("image", jsonimg);
			list.put("images", imgJ);
			list.put("hit", sql.get(i).getHit());
			list.put("usersNumber", sql.get(i).getUsersNumber());
			list.put("productsNumber", sql.get(i).getProductsNumber());
			list.put("reviewsNumber", sql.get(i).getReviewsNumber());
			jsonArarry.add(list);
			jsonObject.put("reviews", jsonArarry);
    	}
    	
    	return jsonObject;
    }
	// 리뷰 작성
	@CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/reviews",method = RequestMethod.POST
  		  )
    @ResponseBody
    public JSONObject reviewInsert(
    		@RequestParam("content") String content,
    		@RequestParam("title") String title,
    		@RequestParam("image") List<MultipartFile> imageReview,
    		@RequestParam("productNumber") Long productNumber
    	) throws IOException
    {
		
		//content title image indate hit(0), usersNumber, productsNumber
		//유저 정보는 세션이나 JWT 로 받아오고, 제품 번호는 직전에 누른 파라미터 값 이용
		ReviewVO vo = new ReviewVO(content, title, 
				ProductsController.fileSave(imageReview,SAVE_PATH),
				new Date(), (long)0, (long)1, productNumber);
		JSONObject json = new JSONObject();
		boolean insert = reService.insertReview(vo);
		String result = (insert==true)?"insert":"fail";
		json.put("result",result);
		return json;
    }
	
	//reviewNumber로 하나의 리뷰 불러오기
	@CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/com/review/{reviewNumber}",method = RequestMethod.GET
  		  )
    @ResponseBody 
    public JSONObject reviewContent(@PathVariable("reviewNumber") String reviewNumber)
    {
	 	return null;
    }
	
	
	
	
	
	// 목적 : 물리저장소(서버컴)에 저장된 이미지를 반환
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/com/reviewImage/{img}",method = RequestMethod.GET
  		 ,produces = MediaType.IMAGE_JPEG_VALUE
  		  )
    @ResponseBody 
	public byte[] getImageWithMediaType(@PathVariable("img") String img) throws IOException {
    	String url = "/com/image/review/"+img+".png";
    	System.out.println(url);
	    InputStream in = getClass().getResourceAsStream(url);
	    System.out.println(img+".png");
	    return IOUtils.toByteArray(in);
	}
	
	
}


