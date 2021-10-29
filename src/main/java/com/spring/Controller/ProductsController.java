package com.spring.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dto.ProductVO;
import com.spring.service.ProductsServiceImpl;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductsController {
	private static String SAVE_PATH="c:/Users/kim/Desktop/project/ShoppingMall/src/main/java/com/image/";
    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Inject
    private ProductsServiceImpl proService;
    //µ•¿Ã≈Õ∞° 25%¡§µµ ªΩ∆¢±‚µ ..
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/products",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject productsList() throws IOException {
    	List<ProductVO> sql = proService.selectList();
    	System.out.println(sql.get(0).getProduct());
    	String[] image = sql.get(0).getImageLazy().split(",");
    	System.out.println(image[0]);
    	
    	JSONObject jsonObject = new JSONObject();
    	
    	
//    	 System.out.println(String.valueOf(IOUtils.toByteArray(in)));
		 jsonObject.put("product", sql.get(0).getProduct());
		 InputStream in = getClass().getResourceAsStream(image[0]);
		 byte[] imageByte = IOUtils.toByteArray(in);
		 System.out.println(imageByte);
		 jsonObject.put("image", imageByte); 
    	 return jsonObject;
    }
}
