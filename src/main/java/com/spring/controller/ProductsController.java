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
import org.springframework.ui.Model;
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
@CrossOrigin(allowCredentials = "false")
public class ProductsController {
	private static String URL_PATH="http://ec2-3-37-117-153.ap-northeast-2.compute.amazonaws.com:8080/shoppingmall/com/product-image/";
//	private static String URL_PATH="http://pvpvpvpvp.gonetis.com:8080/sample";
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/";
    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
    
    
    private static boolean ProductsChange = true;
    //최소응답을 위한 저장장소
    private static Map<String,JSONObject> AllJson = new HashMap<String, JSONObject>();
    private static Map<Long,List<ProductVO>> Allsql = new HashMap<Long, List<ProductVO>>();
    private static List<ProductVO> Prosql = null;
    private static Map<Long,ProductVO> mapSql = new HashMap<Long,ProductVO>();
    
    @Inject
    private ProductsServiceImpl proService;
    @Inject 
    private FunctionSpring functionSpring;
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String productsList(
    		@RequestParam(value="kind",required=false) String kindP,
    		@RequestParam(value="color",required=false) String colorP,
    		@RequestParam(value="size",required=false) String sizeP,
    		@RequestParam(value="price",required=false) String priceP,
    		@RequestParam(value="product", defaultValue = "") String productP,
    		@RequestParam(value="page",defaultValue = "0") int pageP,
    		@RequestParam(value="count",defaultValue = "20") int countP
    		) throws IOException {
    		
    	boolean all = pageP==0;
    	if(all)
    	{
    		countP=99999;
    	}
    	
    	if(AllJson.get(kindP+colorP+sizeP+priceP+pageP+countP+productP)!=null)
			return AllJson.get(kindP+colorP+sizeP+priceP+pageP+countP+productP).toString();
    	JSONObject JSONObPro = new JSONObject();
    	if(Prosql==null)
    	{
    		Prosql = proService.selectList();	
    	}
//    	List<ProductVO> sql = proService.selectList();
    	ProductsChange = false;
    	if(ProductsChange)
    	{
    		AllJson.clear();
    		Prosql.clear();
    		Allsql.clear();
    	}
		JSONArray jsonArarry = new JSONArray();  
		// 페이지 제어용 인덱스
		int index = 0;
    	for(int i=0;i<Prosql.size();i++)
    	{
    		// Allsql 이 없다면 데이터를 넣는다 라는식임.
    		try {
    			if(Allsql.get(Long.valueOf(i)).isEmpty());
    		}
    		catch (Exception e) {
    			Allsql.put(Long.valueOf(i), proService.selectListColorAndSize(Prosql.get(i)));
			}			
    		Map<String,String> colorData = functionSpring.anyArray(Allsql.get(Long.valueOf(i)) , "color");
			Map<String,String> sizeData = functionSpring.anyArray(Allsql.get(Long.valueOf(i)), "size");

			//필터링 1순위 걸고 2순위로 페이지
    		if((kindP==null||(Prosql.get(i).getKind()).contains(kindP))
    				&&(sizeP==null||sizeData.toString().contains("="+sizeP))
    				&&Prosql.get(i).getProduct().contains(productP))   	
    		{   
    			index++;
    			// 페이지 값과 카운트 값에 띠라서 데이터를 넣어준다. 
    			if((pageP*countP>=index)&&(pageP-1)*countP<index||all)
    			{	
	    			JSONObject list = new JSONObject();
					JSONObject colorJ = new JSONObject();
					JSONArray jsoncolors = new JSONArray();
	    			for(String colorA:colorData.values())
	    			{
	    				jsoncolors.add(colorA);
	    			}   			
	    			String size = functionSpring.sizeString(sizeData);			
					String[] image = Prosql.get(i).getTitleImage().split(",");    
					colorJ.put("color",jsoncolors);
					list.put("colors", colorJ);									
					list.put("size", size);				
					list.put("index", index);
					list.put("kind", Prosql.get(i).getKind());				
					list.put("price", Prosql.get(i).getPrice());
					list.put("product", Prosql.get(i).getProduct());
					list.put("productNumber", Prosql.get(i).getProductNumber());			
					list.put("image", URL_PATH+image[0]);
					jsonArarry.add(list);		
    			}
    		}
    	}	
    	JSONObPro.put("products", jsonArarry);
    	JSONObPro.put("endPage", index/countP+1);
		AllJson.put(kindP+colorP+sizeP+priceP+pageP+countP, JSONObPro);
    	System.out.println("kind:"+kindP);
    	System.out.println("color:"+colorP);
    	System.out.println("size:"+sizeP);
    	System.out.println("price:"+priceP);
    	System.out.println("page:"+pageP);
    	System.out.println("count:"+countP);
//    	System.out.println(Allsql.get(1l).toString());
    	return JSONObPro.toString();
    }
    
    // 제품추가 API
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
    	if(productNumber!=0)// ?젣?뭹踰덊샇 議댁옱 ?쑀臾댁뿉 ?뵲?씪?꽌 UPDATE INSERT 媛? ?굹?돇?뼱 ?룞?옉?븳?떎.
    	{
    		vo.setRegDate(new Date());
    		vo.setProductImage(functionSpring.fileSave(productImage,SAVE_PATH,"ec2"));
    		boolean sqlUpdate = proService.updateProduct(vo);
    		result = (sqlUpdate==true)?"update":"fail";
    		json.put("result", result);
    		
    	}
    	if(productNumber==0)
    	{
    		vo.setRegDate(new Date());
    		vo.setProductImage(functionSpring.fileSave(productImage,SAVE_PATH,"ec2"));
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
    
    // 제품의 상세 정보! API
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
    	Map<String,String> colorData = functionSpring.anyArray(volist, "color");
		Map<String,String> sizeData = functionSpring.anyArray(volist, "size");
		Map<String,String> quantityData = functionSpring.anyArray(volist, "quantity");
		JSONObject colorJ = new JSONObject();
		JSONArray jsoncolors = new JSONArray();
		JSONObject sizeJ = new JSONObject();
		JSONArray jsonsizes = new JSONArray();
		
		JSONObject quantityJ = new JSONObject();
		JSONArray jsonquantitys = new JSONArray();
		
		for(String quantityA:quantityData.values())
		{
			jsonquantitys.add(quantityA);
		}		
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
		quantityJ.put("quantity", jsonquantitys);
    	
    	jsonObject.put("product", volist.get(0).getProduct());
    	
		jsonObject.put("id",volist.get(0).getProductNumber());
		jsonObject.put("sizes", sizeJ);
		jsonObject.put("colors", colorJ);
//    	jsonObject.put("quantity", quantityJ);
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
   
    // ?젣?뭹?쓽 ?씠誘몄?瑜? 蹂대궡二쇰뒗 API
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
	    byte[] data = IOUtils.toByteArray(in);
	    in.close();
	    return data;
	}
   
}
