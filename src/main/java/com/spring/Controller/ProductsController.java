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
    //json �迭�� �̹���byte[] ������ �����Ͱ� 25%���� ��Ƣ���..
    //������ ���ڵ��� �ð��� �ణ�̳��� �ҿ��
    
    
    //���� : ��� ��ǰ����Ʈ�� ��ȯ
    // ��ǰ�� ���� ũ�� ������ ���� �̹���
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject productsList(
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
    			System.out.println(sql.get(i).getKind());
				JSONObject list = new JSONObject();
				JSONObject colorJ = new JSONObject();
				JSONArray jsoncolors = new JSONArray();
				//�������� ����� �̹����� ������ ���ؼ� , split
				String[] image = sql.get(i).getImageSmall().split(",");    
				//�����͸� �б� ���� �ε��� �߰�
				list.put("index", i);
				// ���� ���� #000000#fffffff �̷��������� ���� �Ǿ��ֱ� ������ ������ ���� # split�� # �� ���ؼ� ����
				String colors[] = sql.get(i).getColor().split("#");
				// colors�� size��ŭ �ݺ�
				for(String color:colors)
				{
					if(color!="") { //0�� �ε����� ��迭�� �������� 
						jsoncolors.add("#"+color);
					
					}
				}
				colorJ.put("color",jsoncolors);
				list.put("kind", sql.get(i).getKind());
				list.put("size", sql.get(i).getSize());
				list.put("price", sql.get(i).getPrice());
				list.put("colors", colorJ);
				list.put("product", sql.get(i).getProduct());
				list.put("productNumber", sql.get(i).getProductNumber());
				//�̹��� API�� �ϼ���Ű�� ���� URL_PATH �߰�
				list.put("image", URL_PATH+image[0]);
				//�ϼ��� ����Ʈ�� �߰�
				jsonArarry.add(list);
		    	jsonObject.put("products", jsonArarry);
    		}
    	}
    	
    	return jsonObject;
    }
    
    //���� : ��ǰ�� ����Ѵ�.
    //������, ����, ����, ����, ����, �̹���, �̹���2, �̹���3, ��ǰ��, ����
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
    	// Ư�� ������ ���ٸ� �� ��ǰ�� ��ǰ��ȣ�� ��ȯ���ش�. ���ٸ� 0�� ��ȯ�Ѵ�
    	Long productNumber = proService.selectCheckInsert(vo);
    	System.out.println(productNumber);
    	vo = new ProductVO(size, color, kind, quantity, price, content, product, productNumber);
    	JSONObject json = new JSONObject();
    	if(productNumber!=0)// �ߺ��� �ִٸ� ������Ʈ�� �����Ѵ�
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(FunctionSpring.fileSave(imageSmall,SAVE_PATH));
    		vo.setImageLazy(FunctionSpring.fileSave(imageLazy,SAVE_PATH));
    		vo.setProductImage(FunctionSpring.fileSave(productImage,SAVE_PATH));
    		boolean sqlUpdate = proService.updateProduct(vo);
    		json.put("result", "update");
    		return json;
    	}
    	if(productNumber==0)//�ߺ��� ���ٸ� ������ �����Ѵ�
    	{
    		vo.setRegDate(new Date());
    		vo.setImageSmall(FunctionSpring.fileSave(imageSmall,SAVE_PATH));
    		vo.setImageLazy(FunctionSpring.fileSave(imageLazy,SAVE_PATH));
    		vo.setProductImage(FunctionSpring.fileSave(productImage,SAVE_PATH));
    		// ������ ��� ����*�ð��� �̿��ؼ� ������ ���� ��ǰ��ȣ�� �����Ѵ�
    		vo.setProductNumber((long)(Math.random()*System.currentTimeMillis())%10000000);
    		boolean sqlInsert = proService.insertProduct(vo);
    		json.put("result", "insert");
    		return json;
    	}
    	json.put("result", "fail");
    	return json;
    	
    }
    
    //���� : �� ��ǰ�� �������� ��ȯ
    // ��ǰ ���̺� Ư�� ��ǰ�� ��� ������ ��ȯ�Ѵ�.
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
  		  value = "/products/{productNumber}",method = RequestMethod.GET
    		)
    @ResponseBody 
    public JSONObject productCotent(@PathVariable("productNumber") String productNumber) {
    	JSONObject jsonObject= new JSONObject();
    	
    	ProductVO vo = new ProductVO();
    	vo.setProductNumber(Long.valueOf(productNumber));
    	vo = proService.selectProduct(vo);
    	
    	jsonObject.put("product", vo.getProduct());
    	jsonObject.put("sizes",sizeArray(vo.getSize()));
//    	jsonObject.put("size", vo.getSize());
    	JSONObject colorJ = new JSONObject();
		JSONArray jsoncolors = new JSONArray();
    	String colors[] = vo.getColor().split("#");
		// colors�� size��ŭ �ݺ�
		for(String color:colors)
		{
			if(color!="") //0�� �ε����� ��迭�� �������� 
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
    	return jsonObject;
    }
    //������ ��ȯ �迭
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
    		//��������
    		if(size.contains(s+" ~"))
    		{
    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    		//���۵Ǿ����� �߰��Ѵ�.!
    		if(addSize)
    		{
    			System.out.println(s);
    			Jarr.add(s);
    			
    		}
    		//���� ����
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
    // ���� : ���������(������)�� ����� �̹����� ��ȯ
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
    // ���� : ũ�Ѹ��� ������(.txt)�� ������ ���̽��� ����
    static final String rootPath = "C:\\Users\\kim\\Desktop\\project\\ShoppingMall\\files\\";
    static String source = rootPath + "books.txt";
    @RequestMapping(value = "/putDatabase")
    public void putDatabase() {
    	// �ֽ�Ʈ�� ����
    	System.out.println("�����");
    			Reader fr = null; 			
    			// ���� ��Ʈ��
    			BufferedReader br = null;
    			String colorall = "";
    			try {
    				fr= new FileReader(source);    				
    				br  =new BufferedReader(fr);   				
    				int i= 100;
    				ProductVO vo = new ProductVO();
    				String line="";
    				String priceD[];
    				
    				while((line = br.readLine())!=null) //���� ������ null���� ��ȯ
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
    					colorall+=data[3];
    					vo.setImageSmall(data[4]);
    					vo.setKind(data[5]);
    					vo.setRegDate(new Date());
    					vo.setProductNumber((long)i);
//    					proService.insertProduct(vo);
//    					System.out.println(vo.toString());
    				}
    			}catch (Exception e) {
    				e.printStackTrace();
				}finally {
					try {
						br.close();				
					}catch (Exception e) {
						
					}
				}
    			String[] colorArr = colorall.split("#");
    			Map<String,String> colorData = new HashMap<String,String>();
    			colorData.put("","");
    			for(String colorZ: colorArr)
    			{
    				for(int i=0;i<colorData.size();i++)
    					if(!colorZ.equals(colorData.get(i)))
    						colorData.put(colorZ.toUpperCase(),colorZ.toUpperCase());   				
    			}
    			System.out.println(colorData.toString()+ "���� ����" + colorData.size());
    			
    }
}
