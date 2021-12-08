package com.spring.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dto.OrderVO;
import com.spring.service.OrdersServiceImpl;

@Controller
@CrossOrigin(allowCredentials = "false")
public class OrdersController {
	@Inject
	private OrdersServiceImpl orderService;
	
	
	private static String PRODUCT_URL_PATH="http://ec2-3-37-117-153.ap-northeast-2.compute.amazonaws.com:8080/shoppingmall/com/product-image/";
	private static String CUSTOM_URL_PATH="https://shoppingmal.s3.ap-northeast-2.amazonaws.com/custom/";
	private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
	String[] orResult = {"결제 대기","입금 대기","주문 확인","상품 준비","배송 중",
            "배송 완료","반품 중","반품 완료","환불 중","환불 완료",
            "재고 부족"};
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/orders",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String orderList()
    {
		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArarry = new JSONArray();
		List<OrderVO> sql = orderService.selectList();
		for(int i=0;i<sql.size();i++)
    	{
			
			JSONObject list = new JSONObject();
			String result = orResult[sql.get(i).getResult().intValue()];
			list.put("result", result);			
			list.put("price", sql.get(i).getPrice());
			list.put("quantity", sql.get(i).getQuantity());
			list.put("userNumber", sql.get(i).getUsersNumber());
			list.put("productNumbet",sql.get(i).getProductsNumber());
			list.put("orderNumber", sql.get(i).getOrdersNumber());
			list.put("productCustomNumber", sql.get(i).getProductCustomNumber());
			list.put("indate", sql.get(i).getInDate());
			list.put("regdate", sql.get(i).getRegDate());
			list.put("product", sql.get(i).getProduct());
			list.put("titleImage",PRODUCT_URL_PATH+sql.get(i).getTitleImage());
			list.put("size", sql.get(i).getSize());
			list.put("color", sql.get(i).getColor());
			list.put("image",CUSTOM_URL_PATH+sql.get(i).getCustomImage());
			jsonArarry.add(list);
    	}
		jsonObject.put("orders", jsonArarry);
		return jsonObject.toString();
    }
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/orders/user",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String orderUserList()
    {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArarry = new JSONArray();
		OrderVO vo = new OrderVO();
		vo.setUsersNumber(1l);
		List<OrderVO> sql = orderService.selectUserOrderList(vo);
		for(int i=0;i<sql.size();i++)
    	{		
			JSONObject list = new JSONObject();
			String result = orResult[sql.get(i).getResult().intValue()];
			list.put("result", result);			
			list.put("price", sql.get(i).getPrice());
			list.put("quantity", sql.get(i).getQuantity());
			list.put("userNumber", sql.get(i).getUsersNumber());
			list.put("productNumbet",sql.get(i).getProductsNumber());
			list.put("orderNumber", sql.get(i).getOrdersNumber());
			list.put("productCustomNumber", sql.get(i).getProductCustomNumber());
			list.put("indate", sql.get(i).getInDate());
			list.put("regdate", sql.get(i).getRegDate());
			list.put("product", sql.get(i).getProduct());
			list.put("titleImage",sql.get(i).getTitleImage());
			list.put("size", sql.get(i).getSize());
			list.put("color", sql.get(i).getColor());
			list.put("image", sql.get(i).getCustomImage());
			jsonArarry.add(list);
    	}
		jsonObject.put("orders", jsonArarry);
		
		return jsonObject.toString();
    }
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/orders/{orderNumber}",method = RequestMethod.GET,produces = "application/json; charset=utf8")
    @ResponseBody
    public String orderOne(@PathVariable("orderNumber") String orderNumber)
    {
		JSONObject jsonObject = new JSONObject();
		OrderVO vo = new OrderVO();
		vo.setOrdersNumber(Long.valueOf(orderNumber));
		OrderVO sql = orderService.selectUserOrderOne(vo);
		String result = orResult[sql.getResult().intValue()];
		jsonObject.put("result", result);			
		jsonObject.put("price", sql.getPrice());
		jsonObject.put("quantity", sql.getQuantity());
		jsonObject.put("userNumber", sql.getUsersNumber());
		jsonObject.put("productNumbet",sql.getProductsNumber());
		jsonObject.put("orderNumber", sql.getOrdersNumber());
		jsonObject.put("productCustomNumber", sql.getProductCustomNumber());
		jsonObject.put("indate", sql.getInDate());
		jsonObject.put("regdate", sql.getRegDate());
		jsonObject.put("product", sql.getProduct());
		jsonObject.put("titleImage",sql.getTitleImage());
		jsonObject.put("size", sql.getSize());
		jsonObject.put("color", sql.getColor());
		jsonObject.put("image", sql.getCustomImage());
		
		
		return jsonObject.toString();
    }
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/orders/{orderNumber}",method = RequestMethod.POST,produces = "application/json; charset=utf8")
    @ResponseBody
    public String orderUpdate(@PathVariable("orderNumber") String orderNumber,
    		@RequestParam(value="orderResult") String orderResult)
    {
		JSONObject jsonObject = new JSONObject();
		OrderVO vo = new OrderVO();
		vo.setOrdersNumber(Long.valueOf(orderNumber));
		for(int i = 0 ;i < orResult.length;i++)
		{
			if (orderResult.equals(orResult[i]))
			{
				vo.setResult(Long.valueOf(i));
			}
		}
		String result = (true==orderService.updateOrderResult(vo))?"update":"fail";
		jsonObject.put("result", result);
		
		
		return jsonObject.toString();
    }
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/orders",method = RequestMethod.POST,produces = "application/json; charset=utf8")
    @ResponseBody
    public String orderInsert(
    		@RequestParam(value="price") String price,
    		@RequestParam(value="quantity") String quantity,
    		@RequestParam(value="usersNumber") String usersNumber,
    		@RequestParam(value="productsNumber") String productsNumber,
    		@RequestParam(value="productCustomNumber") String productCustomNumber
    		) {
		JSONObject jsonObject = new JSONObject();
		OrderVO vo = new OrderVO();
		vo.setPrice(Long.valueOf(price));
		vo.setQuantity(Long.valueOf(quantity));
		vo.setUsersNumber(Long.valueOf(usersNumber));
		vo.setProductsNumber(Long.valueOf(productsNumber));
		vo.setProductCustomNumber(Long.valueOf(productCustomNumber));
		vo.setResult(0l);
		vo.setInDate(new Date());
		vo.setRegDate(new Date());
		
		String result =  (true==orderService.insertOrder(vo))?"insert":"fail";
		jsonObject.put("result", result);
		
		
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/orders/{orderNumber}",method = RequestMethod.DELETE,produces = "application/json; charset=utf8")
    @ResponseBody
    public String orderDelete(@PathVariable("orderNumber") String orderNumber) {
		JSONObject jsonObject = new JSONObject();
		OrderVO vo = new OrderVO();
		System.out.println(orderNumber);
		vo.setOrdersNumber(Long.valueOf(orderNumber));
		String result =  (true==orderService.deleteOrder(vo))?"delete":"fail";
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
}
