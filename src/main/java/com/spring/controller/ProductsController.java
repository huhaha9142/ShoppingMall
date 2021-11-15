package com.spring.controller;

import java.io.IOException;
import java.io.InputStream;
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
	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample/com/product-image/";
//	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample";
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/";
    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
    
    //?ÑúÎ≤? ?ãú?ûë?ãú ?òπ?? API ?èô?ûë?óê ?î∞?ùº Í∞? Î≥??èô Î™©Ï†Å?? ?Öå?ù¥Î∏îÏóê Î≥??èô?ù¥ ?óÜ?ã§Î©? select ÏøºÎ¶¨?äî ?ã§?ñâ?ïà?ê®.
    private static boolean ProductsChange = true;
    private static List<ProductVO> sql=null;
    private static JSONObject JSONObPro = new JSONObject();
    private static Map<Long,ProductVO> mapSql = new HashMap<Long,ProductVO>();
    @Inject
    private ProductsServiceImpl proService;
    
    
    // ?†ú?íà?ùÑ Î™®Îëê Î≥¥ÎÇ¥Ï£ºÎäî API
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String productsList(
    		@RequestParam(value="kind",required=false) String kindP,
    		@RequestParam(value="color",required=false) String colorP,
    		@RequestParam(value="size",required=false) String sizeP,
    		@RequestParam(value="price",required=false) String priceP
    		) throws IOException {   	
    	if(!ProductsChange)
    	{
    		return JSONObPro.toString();
    	}
		sql = proService.selectList();
		ProductsChange=!ProductsChange;
		JSONArray jsonArarry = new JSONArray();
	
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
				// ?ù¥ÎØ∏Ï??äî ?ó¨?ü¨Í∞? Ï°¥Ïû¨ ?ï† ?àò ?ûàÍ∏? ?ïåÎ¨∏Ïóê ,Î°? Íµ¨Î∂Ñ?êò?ñ¥ ?ûà?ñ¥ ,Î°? SPLIT
				String[] image = sql.get(i).getImageSmall().split(",");    
				// ?é∏?ùò?Ñ±?ùÑ ?úÑ?ïú ?ù∏?ç±?ä§ Ï∂îÍ∏∞
				list.put("index", i);
				// ?Éâ?ÉÅÏΩîÎìú?äî ?ó¨?ü¨Í∞? Ï°¥Ïû¨ ?ï† ?àò ?ûàÍ∏? ?ïåÎ¨∏Ïóê #Î°? Íµ¨Î∂Ñ?êò?ñ¥ ?ûà?ñ¥ #Î°? SPLIT
				String colors[] = sql.get(i).getColor().split("#");
				// colors Î∞∞Ïó¥?ùÑ ???û•
				for(String color:colors)
				{
					if(color!="") { //Ï¥àÍ∏∞?óê ?ÉùÍ∏∞Îäî ÎπàÎ∞∞?ó¥?ùÑ ?†ú?ô∏?ïòÍ≥? Í∞? ?ï≠Î™©Ïóê #Ï∂îÍ?
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
				JSONObPro.put("products", jsonArarry); 	
    		}
    	}
	
    	
    	return JSONObPro.toString();
    }
    
    // ?†ú?íà?ùÑ ?ì±Î°ùÌïò?äî API
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
    	
    	// ?ù¥ Íµ¨Í∞Ñ?èÑ ÏµúÏÜå?ôî ?ûë?óÖ ..
    	ProductVO vo = new ProductVO();
    	vo.setProduct(product);
    	System.out.println(vo.toString());
    	
    	Long productNumber = proService.selectCheckInsert(vo);
    	System.out.println(productNumber);
    	vo = new ProductVO(size, color, kind, quantity, price, content, product, productNumber);
    	JSONObject json = new JSONObject();
    	String result ="fail";
    	if(productNumber!=0)// ?†ú?íàÎ≤àÌò∏ Ï°¥Ïû¨ ?ú†Î¨¥Ïóê ?î∞?ùº?Ñú UPDATE INSERT Í∞? ?Çò?âò?ñ¥ ?èô?ûë?ïú?ã§.
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(FunctionSpring.fileSave(imageSmall,SAVE_PATH));
    		vo.setImageLazy(FunctionSpring.fileSave(imageLazy,SAVE_PATH));
    		vo.setProductImage(FunctionSpring.fileSave(productImage,SAVE_PATH));
    		boolean sqlUpdate = proService.updateProduct(vo);
    		result = (sqlUpdate==true)?"update":"fail";
    		json.put("result", result);
    		
    	}
    	if(productNumber==0)
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(FunctionSpring.fileSave(imageSmall,SAVE_PATH));
    		vo.setImageLazy(FunctionSpring.fileSave(imageLazy,SAVE_PATH));
    		vo.setProductImage(FunctionSpring.fileSave(productImage,SAVE_PATH));
    		//?†ú?íàÎ≤àÌò∏Î•? ???û•?ïò?äîÍ±¥Îç∞ ?ù¥Í±? ?ïÑ?öî?óÜ?äî?ç∞?
//    		vo.setProductNumber((long)(Math.random()*System.currentTimeMillis())%10000000);
    		boolean sqlInsert = proService.insertProduct(vo);
    		result = (sqlInsert==true)?"insert":"fail";
    		json.put("result", result);
    	}
    	if(!result.equals("fail"))
    		ProductsChange=false;
    	// ?óÖ?ç∞?ù¥?ä∏ Î∞úÏÉù?ãú ?ï¥?ãπ ?ç∞?ù¥?Ñ∞Î•? ???û•?ïú ?Ç§Í∞íÏùÑ Ï¥àÍ∏∞?ôî
    	if(result.equals("update"))
    		mapSql.remove(productNumber);
    	return json.toString();
    	
    }
    
    // ?†ú?íà?ùò ?Ñ∏Î∂??†ïÎ≥¥Î?? Í∞??†∏?ò§?äî API
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/products/{productNumber}",method = RequestMethod.GET,produces = "application/json; charset=utf8"
    		)
    @ResponseBody 
    public String productCotent(@PathVariable("productNumber") String productNumber) {
    	
    	
    	JSONObject jsonObject= new JSONObject();
    	ProductVO vo = new ProductVO();
    	
    	try { //?à´?ûê ?ô∏ ?ûÖ?†•?óê ???ï¥?Ñú ?óê?ü¨?†Ñ?Ü°
    		vo.setProductNumber(Long.valueOf(productNumber));
    		mapSql.put(Long.valueOf(productNumber), vo);
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
		// colorsÔøΩÔøΩ sizeÔøΩÔøΩ≈≠ ÔøΩ›∫ÔøΩ
		for(String color:colors)
		{
			if(color!="") //0ÔøΩÔøΩ ÔøΩŒµÔøΩÔøΩÔøΩÔøΩÔøΩ ÔøΩÔøΩÔø??ÔøΩÔøΩ ÔøΩÔøΩÔøΩÔøΩÔøΩÔøΩÔøΩÔøΩ 
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
   
    // ?†ú?íà?ùò ?ù¥ÎØ∏Ï?Î•? Î≥¥ÎÇ¥Ï£ºÎäî API
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/com/product-image/{img}",method = RequestMethod.GET
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
