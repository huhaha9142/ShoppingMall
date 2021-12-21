package com.spring.controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.MemberVO;
import com.spring.dto.ProductVO;
import com.spring.function.FunctionSpring;
import com.spring.service.MemberService;
import com.spring.service.ProductsServiceImpl;
 
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
    
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/";
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @Inject
    private ProductsServiceImpl proService;
    @Inject
    private MemberService service;
    @Inject ProductsServiceImpl servicePro;
    @Inject 
    private FunctionSpring functionSpring;
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) throws Exception{
 
        logger.info("home");
        
        List<MemberVO> memberList = service.selectMember();
        System.out.println(new Date().toLocaleString());
        model.addAttribute("memberList", memberList);
 
        return "home";
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(
    		  value = "/get"
    		  ,method = RequestMethod.GET
//    		  ,produces = MediaType.IMAGE_JPEG_VALUE
    		)
    @ResponseBody 
    		public byte[] getImageWithMediaType() throws IOException {
//    			String url = servicePro.selectImage();
    		    InputStream in = getClass().getResourceAsStream("/com/image/product001.png");
    		    return IOUtils.toByteArray(in);
    		}
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String imageLoding(@RequestParam("files") List<MultipartFile> files) throws IOException {
    	FileOutputStream fos = null;
    	System.out.println(files.size());
    	String imageSmall = "";
    	for(int i=0;i<files.size();i++)
    	{
			byte[] fileData = files.get(i).getBytes();
			System.out.println(fileData);
			fos = new FileOutputStream(SAVE_PATH+"test"+fileData.toString()+".png");
			imageSmall+="com/image/test"+fileData.toString()+",";
			fos.write(fileData);
    	}
    	fos.close();
    	System.out.println(imageSmall);
    	return "N";
	}
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(value = "/loginform", method = RequestMethod.POST)
    public String login(@RequestParam("id") String id,
    		@RequestParam("password") String password,
    		@RequestParam("email") String email) {
    	String id1 = id+" "+password+" "+email;
    	System.out.println(id1);
    	return id1;
    }
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(value = "/loginform1", method = RequestMethod.POST)
    public String login2(HttpServletRequest request) {
    	System.out.println(request.toString());
    	System.out.println(request.getAttributeNames());
    	String id1 = request.getParameter("");
//    	System.out.println(id1);
    	return id1; 
    }
    // 데이터 베이스 데이터 백업
    
    // 텍스트를 데이터 베이스에 넣기 (크롤링 데이터들)
    static final String rootPath = "C:\\Users\\kim\\Desktop\\project\\ShoppingMall\\files\\";
    static String sourceProducts = rootPath + "books.txt";
    @RequestMapping(value = "/putDatabase")
    public void putDatabase() {
    	// �ֽ�Ʈ�� ����
    	System.out.println("�����??");
    			Reader fr = null; 			
    			// ���� ��Ʈ��
    			BufferedReader br = null;
    			String colorall = "";
    			try {
    				fr= new FileReader(sourceProducts);    				
    				br  =new BufferedReader(fr);   				
    				int i= 0;
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
    					
    					priceD=data[2].split(",");   					
    					vo.setPrice(Long.valueOf(priceD[0]+priceD[1]));
    					ArrayList<String> size = functionSpring.sizeArray1(data[1]);
    					String[] color = data[3].split("#");
    					vo.setRegDate(new Date());
    					vo.setInDate(new Date());
    					vo.setProductNumber((long)i);
    					vo.setQuantity("100");
//    					vo.setSize(data[1]);
//    					vo.setColor(data[3]); 					
    					vo.setTitleImage(data[4]);   			
    					vo.setKind(data[5]);   			
    					proService.insertProduct(vo);
    					
    					for(int j = 0 ; j< size.size();j++)
    					{
    						vo.setSize(size.get(j));
    						for(int z = 0;z< color.length;z++)
    						{
    							if(color[z]!="") {
    								vo.setColor("#"+color[z]);
    								proService.insertProductData(vo);
    							}
    						}
    					}
    					
    					
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
//    			String[] colorArr = colorall.split("#");
//    			Map<String,String> colorData = new HashMap<String,String>();
//    			colorData.put("","");
//    			for(String colorZ: colorArr)
//    			{
//    				for(int i=0;i<colorData.size();i++)
//    					if(!colorZ.equals(colorData.get(i)))
//    						colorData.put(colorZ.toUpperCase(),colorZ.toUpperCase());   				
//    			}
//    			System.out.println(colorData.toString()+ "���� ����" + colorData.size());
    			
    }
    
    
}