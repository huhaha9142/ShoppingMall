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
    
    
    private static boolean ProductsChange = true;
    private static Map<String,JSONObject> AllJson = new HashMap<String, JSONObject>();
    private static Map<Long,ProductVO> mapSql = new HashMap<Long,ProductVO>();
    @Inject
    private ProductsServiceImpl proService;
    
    

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String productsList(
    		@RequestParam(value="kind",required=false) String kindP,
    		@RequestParam(value="color",required=false) String colorP,
    		@RequestParam(value="size",required=false) String sizeP,
    		@RequestParam(value="price",required=false) String priceP
    		) throws IOException {
    	
    	if(AllJson.get(kindP+colorP+sizeP+priceP)!=null)
			return AllJson.get(kindP+colorP+sizeP+priceP).toString();
    	JSONObject JSONObPro = new JSONObject();
    	List<ProductVO> sql = proService.selectList();
    	ProductsChange = false;
    	if(ProductsChange)
    	{
    		AllJson.clear();
    	}
		JSONArray jsonArarry = new JSONArray();
	
    	
    	
    	for(int i=0;i<sql.size();i++)
    	{
    		if((kindP==null||kindP.equals(sql.get(i).getKind())))   	
    		{   
    			List<ProductVO> sql2 = proService.selectListColorAndSize(sql.get(i));
    			Map<String,String> colorData = FunctionSpring.anyArray(sql2 , "color");
    			Map<String,String> sizeData = FunctionSpring.anyArray(sql2, "size");
    			JSONObject list = new JSONObject();
				JSONObject colorJ = new JSONObject();
				JSONArray jsoncolors = new JSONArray();
    			for(String colorA:colorData.values())
    			{
    				jsoncolors.add(colorA);
    			}   			
    			String size = FunctionSpring.sizeString(sizeData);			
				String[] image = sql.get(i).getTitleImage().split(",");    
				colorJ.put("color",jsoncolors);
				list.put("colors", colorJ);									
				list.put("size", size);				
				list.put("index", i);
				list.put("kind", sql.get(i).getKind());				
				list.put("price", sql.get(i).getPrice());
				list.put("product", sql.get(i).getProduct());
				list.put("productNumber", sql.get(i).getProductNumber());			
				list.put("image", URL_PATH+image[0]);
				jsonArarry.add(list);
				JSONObPro.put("products", jsonArarry);
				AllJson.put(kindP+colorP+sizeP+priceP, JSONObPro);
    		}
    	}	
    	System.out.println("kind:"+kindP);
    	System.out.println("color:"+colorP);
    	System.out.println("size:"+sizeP);
    	System.out.println("price:"+priceP);
    	return JSONObPro.toString();
    }
    
    // ¡¶«∞√ﬂ∞° API
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
    		vo.setProductImage(FunctionSpring.fileSave(productImage,SAVE_PATH));
    		boolean sqlUpdate = proService.updateProduct(vo);
    		result = (sqlUpdate==true)?"update":"fail";
    		json.put("result", result);
    		
    	}
    	if(productNumber==0)
    	{
    		vo.setRegDate(new Date());
    		vo.setProductImage(FunctionSpring.fileSave(productImage,SAVE_PATH));
//    		vo.setProductNumber((long)(Math.random()*System.currentTimeMillis())%10000000);
    		boolean sqlInsert = proService.insertProduct(vo);
    		result = (sqlInsert==true)?"insert":"fail";
    		json.put("result", result);
    	}
    	if(!result.equals("fail"))
    		ProductsChange=false;
    	if(result.equals("update"))
    		mapSql.remove(productNumber);
    	return json.toString();
    	
    }
    
    // ¡¶«∞¿« ªÛºº ¡§∫∏! API
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/products/{productNumber}",method = RequestMethod.GET,produces = "application/json; charset=utf8"
    		)
    @ResponseBody 
    public String productCotent(@PathVariable("productNumber") String productNumber) {
    	
    	
    	JSONObject jsonObject= new JSONObject();
    	ProductVO vo = new ProductVO();
    	
    	try { 
    		vo.setProductNumber(Long.valueOf(productNumber));
    		mapSql.put(Long.valueOf(productNumber), vo);
    	}catch(Exception e)
    	{
    		jsonObject.put("error", e);
    		return jsonObject.toString();
    	}
    	List<ProductVO> volist = proService.selectProduct(vo);
    	Map<String,String> colorData = FunctionSpring.anyArray(volist, "color");
		Map<String,String> sizeData = FunctionSpring.anyArray(volist, "size");
		JSONObject colorJ = new JSONObject();
		JSONArray jsoncolors = new JSONArray();
		JSONObject sizeJ = new JSONObject();
		JSONArray jsonsizes = new JSONArray();
		
		for(String colorA:colorData.values())
		{
			jsoncolors.add(colorA);
		}
		for(String sizeA:sizeData.values())
		{
			jsonsizes.add(sizeA);
		}

		colorJ.put("color",jsoncolors);
		sizeJ.put("size", jsonsizes);
    	
    	
    	jsonObject.put("product", volist.get(0).getProduct());
    	
		jsonObject.put("id",volist.get(0).getProductNumber());
		jsonObject.put("sizes", sizeJ);
		jsonObject.put("colors", colorJ);
//    	jsonObject.put("quantity", vo.getQuantity());
    	jsonObject.put("price", volist.get(0).getPrice());
    	jsonObject.put("kind", volist.get(0).getKind());
    	jsonObject.put("content", volist.get(0).getContent());
//    	jsonObject.put("image", vo.getProduct());
    	String[] image = volist.get(0).getTitleImage().split(","); 
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
