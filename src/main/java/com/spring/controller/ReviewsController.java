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

import com.spring.dto.ReviewVO;
import com.spring.function.FunctionSpring;
import com.spring.service.ReviewServiceImpl;

@Controller
@CrossOrigin(allowCredentials = "false")
public class ReviewsController {
	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample/com/review-image/";
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/review/";
	
	@Inject
	private ReviewServiceImpl reService;
	@Inject 
	private FunctionSpring functionSpring;
	// ALL Reviews API
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/reviews",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String reviewList()
    {
		System.out.println("reviews.GET");
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
			list.put("title", sql.get(i).getTitle());
//			list.put("indate", sql.get(i).getInDate());
			for(String img:image)
			{
				if(img!="") 
					jsonimg.add(URL_PATH+img);
			}			
			list.put("userId", sql.get(i).getId());
			imgJ.put("image", jsonimg);
			list.put("images", imgJ);
			list.put("userName",sql.get(i).getName());
			list.put("product", sql.get(i).getProduct());
			list.put("reviewsNumber", sql.get(i).getReviewsNumber());
			jsonArarry.add(list);	
			System.out.println(jsonArarry.toString());
    	}
    	jsonObject.put("reviews", jsonArarry);
    	return jsonObject.toString();
    }
	// Review INSERT API
	@CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/reviews",method = RequestMethod.POST,produces = "application/json; charset=utf8"
  		  )
    @ResponseBody
    public String reviewInsert(
    		@RequestParam("content") String content,
    		@RequestParam("title") String title,
    		@RequestParam(value="image",required=false) List<MultipartFile> imageReview,
    		@RequestParam("productNumber") Long productNumber
    	) throws IOException
    {
		System.out.println(content);
		System.out.println(imageReview);
		//content title image indate hit(0), usersNumber, productsNumber
		System.out.println("사이즈"+imageReview.size());
		ReviewVO vo = new ReviewVO();
		if(imageReview.size()==0)
		{
			vo = new ReviewVO(content, title, 
					"noData",
					new Date(), (long)1, productNumber);	
			JSONObject json = new JSONObject();	
			boolean insert = reService.insertReview(vo);
			String result = (insert==true)?"insert":"fail";
			json.put("result",result);
			return json.toString();
		}
		vo = new ReviewVO(content, title, 
				functionSpring.fileSave(imageReview,SAVE_PATH,"s3"),
				new Date(), (long)1, productNumber);	
		JSONObject json = new JSONObject();	
		boolean insert = reService.insertReview(vo);
		String result = (insert==true)?"insert":"fail";
		json.put("result",result);
		return json.toString();

    }
	
	// Review Update API
	@CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/reviews/{reviewsNumber}",method = RequestMethod.POST,produces = "application/json; charset=utf8"
  		  )
    @ResponseBody
    public String reviewUpdate(
    		@PathVariable("reviewsNumber") String reviewsNumber,
    		@RequestParam("content") String content,
    		@RequestParam("title") String title,
    		@RequestParam(value="image",required=false) List<MultipartFile> imageReview	
    	) throws IOException
    {
		
		JSONObject json = new JSONObject();
		ReviewVO vo = new ReviewVO();
		if(imageReview.size()==0)
		{
			vo.setContent(content);
			vo.setTitle(title);
			vo.setRegDate(new Date());
			vo.setImage("noData");
			vo.setReviewsNumber(Long.valueOf(reviewsNumber));
			boolean insert = reService.insertReview(vo);
			String result = (insert==true)?"update":"fail";
			json.put("result",result);
			return json.toString();
		}
		vo.setContent(content);
		vo.setTitle(title);
		vo.setRegDate(new Date());
		vo.setImage(functionSpring.fileSave(imageReview, SAVE_PATH,"s3"));
		vo.setReviewsNumber(Long.valueOf(reviewsNumber));
		boolean update = reService.updateReview(vo);
		String result = (update==true)?"update":"fail";
		json.put("result",result);
		return json.toString();
	
    }
	// Review Delete API
	// TODO: jwt
	@CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/reviews/{reviewsNumber}",method = RequestMethod.DELETE,produces = "application/json; charset=utf8"
  		  )
    @ResponseBody
    public String reviewUpdate(
    		@PathVariable("reviewsNumber") String reviewsNumber)
    {
		JSONObject json = new JSONObject();
		ReviewVO vo = new ReviewVO();
		try { 
			vo.setReviewsNumber(Long.valueOf(reviewsNumber));
    	}catch(Exception e)
    	{
    		json.put("error", e);
    		return json.toString();
    	}
		String imgurl= reService.selectImage(vo);
		boolean delete = reService.deleteReview(vo);
		if(delete)
		{
			boolean fdelete = functionSpring.fileDelete(imgurl, SAVE_PATH);
			System.out.println("delete File?:"+fdelete);
		}
		String result = (delete==true)?"delete":"fail";
		json.put("result",result);
		return json.toString();
		
    }
	
	// TODO : 보류
	// 자기가 작성한 모든 리뷰 보기 (By JWT cookie)
	@CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/reviews/user",method = RequestMethod.GET,produces = "application/json; charset=utf8"
  		  )
    @ResponseBody
    public String reviewMyReviews()
    {
		JSONObject json = new JSONObject();
		
		return json.toString();
    }
	// 제품의 리뷰 제품키랑 연결헌 모든 리뷰 보기 
	@CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/product-reviews/{reviewsNumber}",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String productReviewList(
    		@PathVariable("reviewsNumber") String reviewsNumber)
    {
		System.out.println("reviews.GET");
		JSONObject jsonObject = new JSONObject();
    	JSONArray jsonArarry = new JSONArray();
    	ReviewVO vo = new ReviewVO();
    	vo.setProductsNumber(Long.valueOf(reviewsNumber));
    	List<ReviewVO> sql = reService.selectProductReviews(vo);
    	
    	for(int i=0;i<sql.size();i++)
    	{
    		String[] image = sql.get(i).getImage().split(",");    
			JSONObject list = new JSONObject();
			JSONObject imgJ = new JSONObject();
			JSONArray jsonimg = new JSONArray();
			list.put("index", i);
			list.put("content", sql.get(i).getContent());
			list.put("title", sql.get(i).getTitle());
//			list.put("indate", sql.get(i).getInDate());
			for(String img:image)
			{
				if(img!="") 
					jsonimg.add(URL_PATH+img);
			}
			
			list.put("userId", sql.get(i).getId());
			imgJ.put("image", jsonimg);
			list.put("images", imgJ);
			list.put("userName",sql.get(i).getName());
			list.put("product", sql.get(i).getProduct());
			list.put("reviewsNumber", sql.get(i).getReviewsNumber());
			jsonArarry.add(list);		
    	}
    	jsonObject.put("reviews", jsonArarry);
		System.out.println(jsonArarry.toString());
    	return jsonObject.toString();
    }
	
	// reviewNumber 수정용 데이터 불러가기 
	@CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/reviews/{reviewsNumber}",method = RequestMethod.GET,produces = "application/json; charset=utf8"
  		  )
    @ResponseBody
    public String reviewOne(
    		@PathVariable("reviewsNumber") String reviewsNumber)
    {
		JSONObject json = new JSONObject();
		ReviewVO vo = new ReviewVO();
		System.out.println(reviewsNumber);
		vo.setReviewsNumber(Long.valueOf(reviewsNumber));
		ReviewVO sql=reService.selectOne(vo);
		
		String[] image = sql.getImage().split(",");    
		JSONObject imgJ = new JSONObject();
		JSONArray jsonimg = new JSONArray();
		json.put("content", sql.getContent());
		json.put("title", sql.getTitle());
//		list.put("indate", sql.get(i).getInDate());
		for(String img:image)
		{
			if(img!="") 
				jsonimg.add(URL_PATH+img);
		}
		
		json.put("userId", sql.getId());
		imgJ.put("image", jsonimg);
		json.put("images", imgJ);
		json.put("userName",sql.getName());
		json.put("product", sql.getProduct());
		json.put("reviewsNumber", sql.getReviewsNumber());
		return json.toString();
    }
		
	// 사진만 보내주는 API
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/com/review-image/{img}",method = RequestMethod.GET
  		 ,produces = MediaType.IMAGE_JPEG_VALUE
  		  )
    @ResponseBody 
	public byte[] getImageWithMediaType(@PathVariable("img") String img) throws IOException {
    	String url = "/com/image/review/"+img+".png";
    	System.out.println(url);
	    InputStream in = getClass().getResourceAsStream(url);
	    System.out.println(img+".png");
	    byte[] data = IOUtils.toByteArray(in);
	    in.close();
	    return data;
	}
	
	
}


