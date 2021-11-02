package com.spring.Controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.ProductVO;
import com.spring.service.ProductsServiceImpl;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductsController {
	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample";
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/";
    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Inject
    private ProductsServiceImpl proService;
    //json 배열에 이미지byte[] 넣으면 데이터가 25%정도 뻥튀기됨..
    //심지어 디코딩도 시간이 약간이나마 소요됨
    //모든 제품을 반환하는 api+ 중복제거.
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject productsList() throws IOException {
    	JSONObject jsonObject = new JSONObject();
    	
    	JSONArray jsonArarry = new JSONArray();
    	List<ProductVO> sql = proService.selectList();
    	
    	for(int i=0;i<sql.size();i++)
    	{
    		//중복되는 product는 추가하지 않는다.
    		String array = jsonArarry.toJSONString();   
    		System.out.println();
    		if(!array.contains(sql.get(i).getProduct()))
    		{
	    		JSONObject list = new JSONObject();
				String[] image = sql.get(i).getImageSmall().split(",");    	    	
				list.put("index", i);
				list.put("product", sql.get(i).getProduct());
				list.put("image", URL_PATH+image[0]);				
				jsonArarry.add(list);
	    	}
    	}
    	jsonObject.put("products", jsonArarry);
    	return jsonObject;
    }
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject productsInsertOrUpdate(
    		@RequestParam("size") Long size,
    		@RequestParam("color") String color,
    		@RequestParam("kind") String kind,
    		@RequestParam("quantity") Long quantity,
    		@RequestParam("content") String content,
    		@RequestParam("imageSmall") List<MultipartFile> imageSmall,
    		@RequestParam("imageLazy") List<MultipartFile> imageLazy,
    		@RequestParam("productImage") List<MultipartFile> productImage,
    		@RequestParam("product") String product,
    		@RequestParam("price") Long price
    		) throws IOException
    {
    	ProductVO vo = new ProductVO(size, color, kind, price, product);
    	System.out.println(vo.toString());
    	Long productNumber = proService.selectCheckInsert(vo);
    	System.out.println(productNumber);
    	vo = new ProductVO(size, color, kind, quantity, price, content, product, productNumber);
    	JSONObject json = new JSONObject();
    	if(productNumber!=0)// 중복이 있다면
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(fileSave(imageSmall));
    		vo.setImageLazy(fileSave(imageLazy));
    		vo.setProductImage(fileSave(productImage));
    		boolean sqlUpdate = proService.updateProduct(vo);
    		json.put("result", "update");
    		return json;
    	}
    	if(productNumber==0)//중복이 없다면
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(fileSave(imageSmall));
    		vo.setImageLazy(fileSave(imageLazy));
    		vo.setProductImage(fileSave(productImage));
    		vo.setProductNumber((long)(Math.random()*System.currentTimeMillis())%10000000);
    		boolean sqlInsert = proService.insertProduct(vo);
    		json.put("result", "insert");
    		return json;
    	}
    	json.put("result", "fail");
    	return json;
    	
    }
    public String fileSave(List<MultipartFile> files) throws IOException
    {
    	FileOutputStream fos = null;
    	System.out.println(files.size());
    	String imageurl = "";
    	for(int i=0;i<files.size();i++)
    	{
			byte[] fileData = files.get(i).getBytes();
			int ramdomCount= (int)(Math.random()*System.currentTimeMillis()%10000000);
			try {
			fos = new FileOutputStream(SAVE_PATH+"test"+ramdomCount+".png");
			imageurl+="/com/image/test"+ramdomCount+",";
			}
			catch (Exception e) {
				fos = new FileOutputStream(SAVE_PATH+"test1"+ramdomCount+".png");
				imageurl+="/com/image/test1"+ramdomCount+",";
			}
			fos.write(fileData);
			
    	}
    	fos.close();
    	return imageurl;
    }
    
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/products/{product}",method = RequestMethod.GET
    		)
    @ResponseBody 
    public JSONObject productCotent(@PathVariable("product") String product) {
    	JSONObject jsonObject= new JSONObject();
    	
    	ProductVO vo = new ProductVO();
    	vo.setProduct(product);
    	vo = proService.selectProduct(vo);
    	
    	jsonObject.put("product", vo.getProduct());
    	jsonObject.put("size", vo.getSize());
    	jsonObject.put("color", vo.getColor());
    	jsonObject.put("kind", vo.getKind());
    	jsonObject.put("quantity", vo.getQuantity());
    	jsonObject.put("price", vo.getPrice());
    	jsonObject.put("content", vo.getContent());
//    	jsonObject.put("image", vo.getProduct());
    	String[] image = vo.getImageSmall().split(","); 
    	JSONObject imagelist = new JSONObject();
    	JSONArray imageArr = new JSONArray();
    	for(int i=0;i<image.length;i++)
    	{   		
    		imageArr.add(i, URL_PATH+image[i]);	
    	}
    	
    	jsonObject.put("image", imageArr);
    	return jsonObject;
    }
    
    // 이미지만 보내는 api
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/com/image/{img}",method = RequestMethod.GET
  		 ,produces = MediaType.IMAGE_JPEG_VALUE
  		  )
    @ResponseBody 
	public byte[] getImageWithMediaType(@PathVariable("img") String img) throws IOException {
    	String url = "/com/image/"+img+".png";
    	System.out.println(url);
	    InputStream in = getClass().getResourceAsStream(url);
	    System.out.println(img+".png");
	    return IOUtils.toByteArray(in);
	}
}
