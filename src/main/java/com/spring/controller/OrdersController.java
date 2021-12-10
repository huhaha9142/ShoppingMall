package com.spring.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.spring.dto.OrderVO;
import com.spring.service.OrdersServiceImpl;

@Controller
@CrossOrigin(allowCredentials = "false")
public class OrdersController {
	@Inject
	private OrdersServiceImpl orderService;
	
	private static String KAKAO_PAYMENT_URL_PATH="https://kapi.kakao.com/v1/payment";
	private static String KAKAO_READY="/ready";
	private static String KAKAO_APPROVE="/approve";
	private static String KAKAO_APPROVAL_URL="http://localhost:3000/approval";
	private static String KAKAO_CANCEL_URL="http://localhost:3000/cancel";
	private static String KAKAO_FAIL_URL="http://localhost:3000/fail";	
	private static String PRODUCT_URL_PATH="http://ec2-3-37-117-153.ap-northeast-2.compute.amazonaws.com:8080/shoppingmall/com/product-image/";
	private static String CUSTOM_URL_PATH="https://shoppingmal.s3.ap-northeast-2.amazonaws.com/";
	private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
	String[] orResult = {"???? ???","??? ???","??? ???","??? ???","??? ??",
            "??? ???","??? ??","??? ???","??? ??","??? ???",
            "??? ????"};
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
    		@RequestParam(value="product") String product,
    		@RequestParam(value="quantity") String quantity,  		
    		@RequestParam(value="usersNumber") String usersNumber,
    		@RequestParam(value="productsNumber") String productsNumber,
    		@RequestParam(value="productCustomNumber") String productCustomNumber,
    		@RequestParam(value="productCount", defaultValue = "1") int productCount
    		) {
		Date time = new Date();
		String uuId = "buy"+UUID.randomUUID();
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		String tid = null;
		String paymentUrl = null;
		String orderNumber = null;
		String result = null;
		int totalPrice = 0;
		List<OrderVO> vo = new ArrayList<OrderVO>();
		for(int i = 0;i<productCount;i++)
		{
			vo.add(new OrderVO());
			String[] priceA = price.split(",");
			if(i==0)
			{
				for(String pp:priceA)
				{
					totalPrice +=Integer.valueOf(pp);
				}
			}
			String[] quantityA = quantity.split(",");
			String[] productA = product.split(",");
			String[] productsNumberA = productsNumber.split(",");
			String[] productCustomNumberA = productCustomNumber.split(",");
			vo.get(i).setUuid(uuId);
			vo.get(i).setPrice(Long.valueOf(priceA[i]));
			vo.get(i).setQuantity(Long.valueOf(quantityA[i]));
			vo.get(i).setUsersNumber(Long.valueOf(usersNumber));
			vo.get(i).setProductsNumber(Long.valueOf(productsNumberA[i]));
			vo.get(i).setProductCustomNumber(Long.valueOf(productCustomNumberA[i]));
			vo.get(i).setProduct(productA[i]);
			vo.get(i).setResult(0l);
			vo.get(i).setInDate(time);
			vo.get(i).setRegDate(time);
			orderService.insertOrder(vo.get(i));
		}
		String itemName = "";
		String itemCode = "";
		if(productCount!=1)
		{
			itemName= " ?? "+(productCount-1)+"??";
			itemCode= "+";

		}
		orderNumber = String.valueOf(vo.get(0).getOrdersNumber());
		
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		try {
			MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
			map.add("cid", "TC0ONETIME");
			map.add("partner_order_id", orderNumber);
			map.add("partner_user_id", usersNumber);
			map.add("item_name", vo.get(0).getProduct()+itemName);
			map.add("item_code", vo.get(0).getProductsNumber()+itemCode);
			map.add("quantity", String.valueOf(vo.get(0).getQuantity()));
			map.add("total_amount", String.valueOf(totalPrice));
			map.add("tax_free_amount", String.valueOf(totalPrice/10));
			map.add("approval_url", KAKAO_APPROVAL_URL+"?uuid="+uuId+"&orderNumber="+orderNumber);
			map.add("cancel_url", KAKAO_CANCEL_URL+"?uuid="+uuId);
			map.add("fail_url", KAKAO_FAIL_URL+"?uuid="+uuId);
			//TODO: ??????? ??????!
			headers.add("Authorization", "KakaoAK 808e27a6a5ec182559cd3332439f68fd");
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(map,headers);
			String answer = restTemplate.postForObject(KAKAO_PAYMENT_URL_PATH+KAKAO_READY, entity, String.class);
			System.out.println(answer);
			JSONObject token = (JSONObject) jsonParser.parse(answer);
			tid = (String)token.get("tid");
			paymentUrl = (String)token.get("next_redirect_pc_url");
			vo.get(0).setTid(tid);

			result = (true==orderService.updateOrderTid(vo.get(0)))?"PaymentDone":"PaymentFail";
		} catch (Exception e) {
			System.err.println(e);
		}
		jsonObject.put("result", result);
		jsonObject.put("paymentURL", paymentUrl);
		
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/orders/approve",method = RequestMethod.POST,produces = "application/json; charset=utf8")
    @ResponseBody
    public String orderApprove(
    		@RequestParam(value="cid", defaultValue = "TC0ONETIME") String cid,
    		@RequestParam(value="userNumber") String usersNumber,
    		@RequestParam(value="orderNumber") String orderNumber,
    		@RequestParam(value="pgToken") String pgToken,
    		@RequestParam(value="uuid") String uuId) {
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		String result = null;
		OrderVO vo = new OrderVO();
		vo.setUuid(uuId);
		vo.setRegDate(new Date());
		List<OrderVO> sql = orderService.selectOrderTid(vo);

		try {
			MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
			map.add("cid", cid);
			map.add("partner_order_id", orderNumber);
			map.add("partner_user_id", usersNumber);
			map.add("pg_token", pgToken);
			map.add("tid", sql.get(0).getTid());
		
			//TODO: ??????? ??????!
			headers.add("Authorization", "KakaoAK 808e27a6a5ec182559cd3332439f68fd");
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(map,headers);
			String answer = restTemplate.postForObject(KAKAO_PAYMENT_URL_PATH+KAKAO_APPROVE, entity, String.class);
			System.out.println(answer);
			JSONObject token = (JSONObject) jsonParser.parse(answer);
			vo.setResult(2L);
			result = (true == orderService.updateOrderResultByUuid(vo))?"Success":"Fail";
			System.out.println(result);
			jsonObject.put("result", result);
			
		} catch (Exception e) {
			System.err.println(e);
			jsonObject.put("result", "Fail");
			return jsonObject.toString();
		}	
		
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
