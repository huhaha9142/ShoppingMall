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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
				String[] image = sql.get(i).getImageLazy().split(",");    	    	
				list.put("index", i);
				list.put("product", sql.get(i).getProduct());
				list.put("image", URL_PATH+image[1]);				
				jsonArarry.add(list);
	    	}
    	}
    	jsonObject.put("products", jsonArarry);
    	return jsonObject;
    }
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.POST)
    @ResponseBody
    public void productsInsertOrUpdate(
    		@RequestParam("size") Long size,
    		@RequestParam("color") String color,
    		@RequestParam("kind") String kind,
    		@RequestParam("quantity") Long quantity,
    		@RequestParam("content") String content,
    		@RequestParam("imageSmall") String imageSmall,
    		@RequestParam("imageLazy") String imageLazy,
    		@RequestParam("productImage") String productImage,
    		@RequestParam("product") String product,
    		@RequestParam("productNumber") Long productNumber,
    		@RequestParam("price") Long price,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    		@RequestParam("regDate") Date regDate
    		)
    {
    	ProductVO vo = new ProductVO(size, color, kind, price, product);
    	System.out.println(vo.toString());
    	boolean sql = proService.selectCheckInsert(vo);
    	System.err.println(sql);
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
	    InputStream in = getClass().getResourceAsStream(url);
	    System.out.println(img+".png");
	    return IOUtils.toByteArray(in);
	}
}
