package com.spring.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.spring.dto.ProductVO;
import com.spring.function.FunctionSpring;
import com.spring.service.ProductsServiceImpl;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductsController {
	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample/com/productImage/";
//	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample";
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/";
    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Inject
    private ProductsServiceImpl proService;
    
    
    // 제품을 모두 보내주는 API
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String productsList(
    		@RequestParam(value="kind",required=false) String kindP,
    		@RequestParam(value="color",required=false) String colorP,
    		@RequestParam(value="size",required=false) String sizeP,
    		@RequestParam(value="price",required=false) String priceP
    		) throws IOException {
    	JSONObject jsonObject = new JSONObject();
    	
    	JSONArray jsonArarry = new JSONArray();
    	List<ProductVO> sql = proService.selectList();
    	System.out.println("kind:"+kindP);
    	System.out.println("color:"+colorP);
    	System.out.println("size:"+sizeP);
    	System.out.println("price:"+priceP);
    	
    	for(int i=0;i<sql.size();i++)
    	{
    		if((kindP==null||kindP.equals(sql.get(i).getKind())))
    		{
//    			System.out.println(sql.get(i).getKind());
				JSONObject list = new JSONObject();
				JSONObject colorJ = new JSONObject();
				JSONArray jsoncolors = new JSONArray();
				// 이미지는 여러개 존재 할 수 있기 때문에 ,로 구분되어 있어 ,로 SPLIT
				String[] image = sql.get(i).getImageSmall().split(",");    
				// 편의성을 위한 인덱스 추기
				list.put("index", i);
				// 색상코드는 여러개 존재 할 수 있기 때문에 #로 구분되어 있어 #로 SPLIT
				String colors[] = sql.get(i).getColor().split("#");
				// colors 배열을 저장
				for(String color:colors)
				{
					if(color!="") { //초기에 생기는 빈배열을 제외하고 각 항목에 #추가
						jsoncolors.add("#"+color);
					
					}
				}
				colorJ.put("color",jsoncolors);
				list.put("colors", colorJ);
				
				list.put("kind", sql.get(i).getKind());
				list.put("size", sql.get(i).getSize());
				list.put("price", sql.get(i).getPrice());
				list.put("product", sql.get(i).getProduct());
				list.put("productNumber", sql.get(i).getProductNumber());			
				list.put("image", URL_PATH+image[0]);
				jsonArarry.add(list);
		    	jsonObject.put("products", jsonArarry);
    		}
    	}
    	
    	return jsonObject.toString();
    }
    
    // 제품을 등록하는 API
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.POST,produces = "application/json; charset=utf8")
    @ResponseBody
    public String productsInsertOrUpdate(
    		@RequestParam("size") String size,
    		@RequestParam("color") String color,
    		@RequestParam("kind") String kind,
    		@RequestParam("quantity") String quantity,
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
    	if(productNumber!=0)// 제품번호 존재 유무에 따라서 UPDATE INSERT 가 나뉘어 동작한다.
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(FunctionSpring.fileSave(imageSmall,SAVE_PATH));
    		vo.setImageLazy(FunctionSpring.fileSave(imageLazy,SAVE_PATH));
    		vo.setProductImage(FunctionSpring.fileSave(productImage,SAVE_PATH));
    		boolean sqlUpdate = proService.updateProduct(vo);
    		json.put("result", "update");
    		return json.toString();
    	}
    	if(productNumber==0)
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(FunctionSpring.fileSave(imageSmall,SAVE_PATH));
    		vo.setImageLazy(FunctionSpring.fileSave(imageLazy,SAVE_PATH));
    		vo.setProductImage(FunctionSpring.fileSave(productImage,SAVE_PATH));
    		//제품번호를 저장하는건데 이거 필요없는데?
//    		vo.setProductNumber((long)(Math.random()*System.currentTimeMillis())%10000000);
    		boolean sqlInsert = proService.insertProduct(vo);
    		json.put("result", "insert");
    		return json.toString();
    	}
    	json.put("result", "fail");
    	return json.toString();
    	
    }
    
    // 제품의 세부정보를 가져오는 API
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/products/{productNumber}",method = RequestMethod.GET,produces = "application/json; charset=utf8"
    		)
    @ResponseBody 
    public String productCotent(@PathVariable("productNumber") String productNumber) {
    	JSONObject jsonObject= new JSONObject();
    	
    	ProductVO vo = new ProductVO();
    	try { //숫자 외 입력에 대해서 에러전송
    		vo.setProductNumber(Long.valueOf(productNumber));
    	}catch(Exception e)
    	{
    		jsonObject.put("error", e);
    		return jsonObject.toString();
    	}
    	
    	vo = proService.selectProduct(vo);
    	
    	jsonObject.put("product", vo.getProduct());
    	jsonObject.put("sizes",FunctionSpring.sizeArray(vo.getSize()));
//    	jsonObject.put("size", vo.getSize());
    	JSONObject colorJ = new JSONObject();
		JSONArray jsoncolors = new JSONArray();
    	String colors[] = vo.getColor().split("#");
		// colors�� size��ŭ �ݺ�
		for(String color:colors)
		{
			if(color!="") //0�� �ε����� ���?�� �������� 
				jsoncolors.add("#"+color);
		}
		jsonObject.put("id",vo.getProductNumber());
		colorJ.put("color",jsoncolors);
		jsonObject.put("colors", colorJ);
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
    	return jsonObject.toString();
    }
   
    // 제품의 이미지를 보내주는 API
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/com/productImage/{img}",method = RequestMethod.GET
  		 ,produces = MediaType.IMAGE_JPEG_VALUE
  		  )
    @ResponseBody 
	public byte[] getImageWithMediaType(@PathVariable("img") String img) throws IOException {
    	String url = "/com/image/"+img+".png";
//    	System.out.println(url);
	    InputStream in = getClass().getResourceAsStream(url);
//	    System.out.println(img+".png");
	    return IOUtils.toByteArray(in);
	}
   
}
