package com.spring.Controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.xdevapi.JsonString;
import com.spring.dto.MemberVO;
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
    private MemberService service;
    @Inject ProductsServiceImpl servicePro;
    
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) throws Exception{
 
        logger.info("home");
        
        List<MemberVO> memberList = service.selectMember();
        
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
    
    
}