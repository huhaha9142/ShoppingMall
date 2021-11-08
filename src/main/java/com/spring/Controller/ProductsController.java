package com.spring.Controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
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

import com.spring.dto.ProductVO;
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
    //json 배열에 이미지byte[] 넣으면 데이터가 25%정도 뻥튀기됨..
    //심지어 디코딩도 시간이 약간이나마 소요됨
    
    
    //목적 : 모든 제품리스트를 반환
    // 제품명 가격 크기 사이즈 색상 이미지
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject productsList() throws IOException {
    	JSONObject jsonObject = new JSONObject();
    	
    	JSONArray jsonArarry = new JSONArray();
    	List<ProductVO> sql = proService.selectList();
    	
    	for(int i=0;i<sql.size();i++)
    	{
			JSONObject list = new JSONObject();
			JSONObject colorJ = new JSONObject();
			JSONArray jsoncolors = new JSONArray();
			//다중으로 저장된 이미지를 꺼내기 위해서 , split
			String[] image = sql.get(i).getImageSmall().split(",");    
			//데이터를 읽기 쉽게 인덱스 추가
			list.put("index", i);
			// 색의 경우는 #000000#fffffff 이런형식으로 저장 되어있기 때문에 구별을 위해 # split후 # 을 더해서 저장
			String colors[] = sql.get(i).getColor().split("#");
			// colors의 size만큼 반복
			for(String color:colors)
			{
				if(color!="") //0번 인덱스의 빈배열을 빼기위함 
					jsoncolors.add("#"+color);
			}
			colorJ.put("color",jsoncolors);
			list.put("size", sql.get(i).getSize());
			list.put("price", sql.get(i).getPrice());
			list.put("colors", colorJ);
			list.put("product", sql.get(i).getProduct());
			//이미지 API를 완성시키기 위해 URL_PATH 추가
			list.put("image", URL_PATH+image[0]);
			//완성된 리스트를 추가
			jsonArarry.add(list);
	    	jsonObject.put("products", jsonArarry);
    	}
    	return jsonObject;
    }
    
    //목적 : 제품을 등록한다.
    //사이즈, 색상, 종류, 수량, 내용, 이미지, 이미지2, 이미지3, 제품명, 가격
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject productsInsertOrUpdate(
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
    	// 특정 조건이 같다면 그 제품의 제품번호를 반환해준다. 없다면 0을 반환한다
    	Long productNumber = proService.selectCheckInsert(vo);
    	System.out.println(productNumber);
    	vo = new ProductVO(size, color, kind, quantity, price, content, product, productNumber);
    	JSONObject json = new JSONObject();
    	if(productNumber!=0)// 중복이 있다면 업데이트를 실행한다
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(fileSave(imageSmall,SAVE_PATH));
    		vo.setImageLazy(fileSave(imageLazy,SAVE_PATH));
    		vo.setProductImage(fileSave(productImage,SAVE_PATH));
    		boolean sqlUpdate = proService.updateProduct(vo);
    		json.put("result", "update");
    		return json;
    	}
    	if(productNumber==0)//중복이 없다면 삽입을 실행한다
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(fileSave(imageSmall,SAVE_PATH));
    		vo.setImageLazy(fileSave(imageLazy,SAVE_PATH));
    		vo.setProductImage(fileSave(productImage,SAVE_PATH));
    		// 시퀸스 대신 랜덤*시간을 이용해서 무작위 값을 제품번호로 저장한다
    		vo.setProductNumber((long)(Math.random()*System.currentTimeMillis())%10000000);
    		boolean sqlInsert = proService.insertProduct(vo);
    		json.put("result", "insert");
    		return json;
    	}
    	json.put("result", "fail");
    	return json;
    	
    }
    // 파일을 저장해주는 함수 
    public static String fileSave(List<MultipartFile> files ,String SAVE_PATH) throws IOException
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
			imageurl+="test"+ramdomCount+",";
			}
			catch (Exception e) {
				fos = new FileOutputStream(SAVE_PATH+"test1"+ramdomCount+".png");
				imageurl+="test1"+ramdomCount+",";
			}
			fos.write(fileData);
			
    	}
    	fos.close();
    	return imageurl;
    }
    //목적 : 각 제품의 세부정보 반환
    // 제품 테이블 특정 제품의 모든 정보를 반환한다.
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
    	jsonObject.put("sizes",sizeArray(vo.getSize()));
//    	jsonObject.put("size", vo.getSize());
    	JSONObject colorJ = new JSONObject();
		JSONArray jsoncolors = new JSONArray();
    	String colors[] = vo.getColor().split("#");
		// colors의 size만큼 반복
		for(String color:colors)
		{
			if(color!="") //0번 인덱스의 빈배열을 빼기위함 
				jsoncolors.add("#"+color);
		}
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
    	return jsonObject;
    }
    //사이즈 변환 배열
    public static String sizes[] = {"XS","S","M","L","XL","2XL","3XL","4XL","5XL","6XL","7XL",
    								"XS(80)","S(85)","M(90)","L(95)","XL(100)","2XL(105)","3XL(110)","4XL(115)",
    								"XS(85)","S(90)","M(95)","L(100)","XL(105)","2XL(110)","3XL(115)","4XL(120)",
    								"XS(90)","S(95)","M(100)","L(105)","XL(110)","2XL(115)","3XL(120)","4XL(125)",
    								"85","90","95","100","105","110","115","120","125"};
   
    public JSONObject sizeArray(String size) {
    	JSONArray Jarr = new JSONArray();
    	JSONObject Jobj = new JSONObject();
    	boolean addSize = false;
    	for(String s : sizes)
    	{
    		//시작지점
    		if(size.contains(s+" ~"))
    		{
    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    		//시작되었으면 추가한다.!
    		if(addSize)
    		{
    			System.out.println(s);
    			Jarr.add(s);
    			
    		}
    		//종료 지점
    		if(addSize&&size.contains("~ "+s))
    		{
    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    	}
    	if(Jarr.isEmpty())
		{
			Jarr.add("FREE");
		}
    	Jobj.put("size", Jarr);
		return Jobj;
    }
    // 목적 : 물리저장소(서버컴)에 저장된 이미지를 반환
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/com/productImage/{img}",method = RequestMethod.GET
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
    // 목적 : 크롤링한 데이터(.txt)를 데이터 베이스에 저장
    static final String rootPath = "C:\\Users\\kim\\Desktop\\project\\ShoppingMall\\files\\";
    static String source = rootPath + "books.txt";
    @RequestMapping(value = "/putDatabase")
    public void putDatabase() {
    	// 주스트림 구역
    	System.out.println("실행됨");
    			Reader fr = null; 			
    			// 보조 스트림
    			BufferedReader br = null;		
    			try {
    				fr= new FileReader(source);    				
    				br  =new BufferedReader(fr);   				
    				int i= 100;
    				ProductVO vo = new ProductVO();
    				String line="";
    				String priceD[];
    				while((line = br.readLine())!=null) //여긴 없으면 null값이 반환
    				{
    					i++;
    					//index 0~4
    					// product size price color image
    					String data[] = line.split("\"");
    					vo.setProduct(data[0]);
    					vo.setSize(data[1]);
    					priceD=data[2].split(",");   					
    					vo.setPrice(Long.valueOf(priceD[0]+priceD[1]));
    					vo.setColor(data[3]);
    					vo.setImageSmall(data[4]);
    					vo.setKind(data[5]);
    					vo.setRegDate(new Date());
    					vo.setProductNumber((long)i);
    					proService.insertProduct(vo);
    					System.out.println(vo.toString());
    				}
    			}catch (Exception e) {
    				e.printStackTrace();
				}finally {
					try {
						br.close();				
					}catch (Exception e) {
						
					}
				}
    			
    }
}
