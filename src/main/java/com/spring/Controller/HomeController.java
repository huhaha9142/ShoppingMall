package com.spring.Controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

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
    @GetMapping(
    		  value = "/get",
    		  produces = MediaType.IMAGE_JPEG_VALUE
    		)
    		public @ResponseBody byte[] getImageWithMediaType() throws IOException {
    			String url = servicePro.selectImage();
    		    InputStream in = getClass().getResourceAsStream(url);
    		    return IOUtils.toByteArray(in);
    		}
    @CrossOrigin(origins = "*", allowedHeaders = "*")  
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String imageLoding(@RequestParam("files") MultipartFile files) throws IOException {
    	byte[] fileData = files.getBytes();
    	System.out.println(fileData);
    	FileOutputStream fos = new FileOutputStream(SAVE_PATH+"test"+".png");
    	fos.write(fileData);
    	fos.close();
    	if(fileData==null)
    	return "N";
    	return "S";
	} 
}