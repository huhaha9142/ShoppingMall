package com.spring.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.CartVO;
import com.spring.dto.ReviewVO;
import com.spring.service.CartServiceImpl;

@Controller
public class CartController {
	
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	private static String URL_PATH="http://ec2-3-37-117-153.ap-northeast-2.compute.amazonaws.com:8080/shoppingmall/com/product-image/";
	@Autowired
	private CartServiceImpl cartService;
		//	Cart SELECT
		@CrossOrigin(origins = "*", allowedHeaders = "*")
	    @RequestMapping(value = "/cart",method = RequestMethod.GET,produces = "application/json; charset=utf8")
	    @ResponseBody
		public String cartList(@RequestParam("userNumber") Long userNumber)
		{
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArarry = new JSONArray();
			CartVO vo = new CartVO();
			vo.setUsersNumber(userNumber);
			List<CartVO> sql=cartService.selectListCart(vo);
			
			for(int i=0;i<sql.size();i++)
			{
				JSONObject list = new JSONObject();
				list.put("images", URL_PATH + sql.get(i).getTitle_image());
				list.put("index", i);
				list.put("quantity", sql.get(i).getQuantity());
				list.put("usersNumber", sql.get(i).getUsersNumber());
				list.put("cartNumber", sql.get(i).getCartNumber());
				list.put("product", sql.get(i).getProduct());
				list.put("price", sql.get(i).getPrice());
				list.put("size", sql.get(i).getSize());
				list.put("color", sql.get(i).getColor());
				jsonArarry.add(list);
			}
			jsonObject.put("carts", jsonArarry);
			return jsonObject.toString();
		}
		//	Cart INSERT
		@CrossOrigin(origins = "*", allowedHeaders = "*")
	    @RequestMapping(value = "/cart",method = RequestMethod.POST,produces = "application/json; charset=utf8")
		@ResponseBody
		public String cartInsert(
				@RequestParam("quantity") Long quantity,
	    		@RequestParam("usersNumber") Long usersNumber,
	    		@RequestParam("productNumber") Long productNumber,
	    		@RequestParam("size") String size,
	    		@RequestParam("color") String color
				) throws IOException
		{
			CartVO vo = new CartVO();
			{
				vo.setQuantity(quantity);
				vo.setUsersNumber(usersNumber);
				vo.setProductsNumber(productNumber);
				vo.setSize(size);
				vo.setColor(color);
				JSONObject json = new JSONObject();
				boolean insert = cartService.insertCart(vo);
				String result = (insert==true)?"insert":"fail";
				json.put("result", result);
				return json.toString();
			}
		}
			
		//	Cart UPDATE
		@CrossOrigin(origins = "*", allowedHeaders = "*")
		@RequestMapping(value = "/cart/{cartNumber}",method = RequestMethod.POST,produces = "application/json; charset=utf8")
		@ResponseBody	
		public String cartUpdate(
	    		@PathVariable("cartNumber") String cartNumber,
	    		@RequestParam("quantity") Long quantity
	    	) throws IOException
		{
			JSONObject json = new JSONObject();
			CartVO vo = new CartVO();
			{
				vo.setQuantity(quantity);
				vo.setCartNumber(Long.valueOf(cartNumber));
				boolean insert = cartService.updateCart(vo);
				String result = (insert==true)?"update":"fail";
				json.put("result", result);
				return json.toString();
			}
			
	}
			
		// Cart DELETE
		@CrossOrigin(origins = "*", allowedHeaders = "*")  
		@RequestMapping(
		  		  value = "/cart/{cartNumber}",method = RequestMethod.DELETE,produces = "application/json; charset=utf8")
		@ResponseBody
		public String cartDelete(
				@PathVariable("cartNumber") String cartNumber)
		{
			JSONObject json = new JSONObject();
			CartVO vo = new CartVO();
			vo.setCartNumber(Long.valueOf(cartNumber));
			String result = (cartService.deleteCart(vo)==true)?"delete":"fail";
			json.put("result", result);
			return json.toString();
		}
		
		
}	
		

