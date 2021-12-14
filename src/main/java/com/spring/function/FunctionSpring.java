package com.spring.function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.ProductVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Service
public class FunctionSpring {
	
	// 파일을 저장해주는 함수 
    // 받은 파일을 SAVE_PATH위치에 임의의 이름으로 저장하고
    // 저장된 이름을 리턴한다.
    // TODO: 중복이 아예 일어날 수 없게 설계되거나 중복시 다시 시도 할 수 있게 짜야함(재귀)
    public String fileSave(List<MultipartFile> files ,String SAVE_PATH,String where) throws IOException
    {
    	FileOutputStream fos = null;

    	System.out.println(new File(".").toString());
    	System.out.println(files.size());
    	String imageurl = "";
    	for(int i=0;i<files.size();i++)
    	{
			byte[] fileData = files.get(i).getBytes();
			String uuid = UUID.randomUUID().toString();
			try {
			fos = new FileOutputStream(SAVE_PATH+uuid+".png");
			imageurl+=uuid+",";
			if(where.equals("s3"))
			{
				fileDelete(imageurl,SAVE_PATH);	
			}
			}
			catch (Exception e) {
				System.err.println(e);
				fos = new FileOutputStream(SAVE_PATH+uuid+".png");
				imageurl=uuid+",";
			}
			fos.write(fileData);	
    	}
    	fos.close();
    	return imageurl;
    }
    public boolean fileDelete(String imageName,String SAVE_PATH)
    {
    	String[] imageUrl = imageName.split(",");
    	boolean result = false;
    	for(String url:imageUrl)
    	{
    		if(url!=null)
    		{
    			File img = new File(SAVE_PATH+url+".png");
	    		result = img.delete();
    		}
    	}
    	return result;
    }
    //중복제거 사이즈 컬러 수량
    public Map<String,String> anyArray(List<ProductVO> sql,String type)
    {
		Map<String,String> anyData = new HashMap<String,String>();
		for(int j = 0; j<sql.size();j++)
		{
			if(type == "size")
				anyData.put(sql.get(j).getSize(), sql.get(j).getSize());
			if(type == "color")
				anyData.put(sql.get(j).getColor(), sql.get(j).getColor());
			if(type == "quantity")
				anyData.put(""+j, sql.get(j).getQuantity());			
		}
		return anyData;
    }  
    //사이즈 변환 배열
    public String sizes[] = {"XS","S","M","L","XL","2XL","3XL","4XL","5XL","6XL","7XL",
    								"XS(80)","S(85)","M(90)","L(95)","XL(100)","2XL(105)","3XL(110)","4XL(115)","5XL(120)",
    								"XS(85)","S(90)","M(95)","L(100)","XL(105)","2XL(110)","3XL(115)","4XL(120)","5XL(125)",
    								"XS(90)","S(95)","M(100)","L(105)","XL(110)","2XL(115)","3XL(120)","4XL(125)","5XL(130)",
    								"85","90","95","100","105","110","115","120","125","130","FREE"};
    public String sizeString(Map<String,String> size)
    {
    	
    	String result = "";
    	String endSize = "";
    	boolean addSize = false;
    	for(String s: sizes)
    	{
    		
    		for(String sizeList:size.values())
    		{
    			if(sizeList.equals(s))
    				endSize=s;
    			if(sizeList.equals(s)&&addSize==false)
    			{	
    				addSize=!addSize;
    				result=result+s;  				
    			}
    		}
    	}
    	if(!result.equals(endSize))
    		result = result+" ~ "+endSize;
    	return result;
    }
    public JSONObject sizeArray(String size) {
    	JSONArray Jarr = new JSONArray();
    	JSONObject Jobj = new JSONObject();
    	boolean addSize = false;
    	for(String s : sizes)
    	{
    		//시작 지점 찾기
    		if(size.contains(s+" ~"))
    		{
//    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    		//범위 데이터 등록
    		if(addSize)
    		{
//    			System.out.println(s);
    			Jarr.add(s);
    			
    		}
    		//끝 지점 찾기
    		if(addSize&&size.contains("~ "+s))
    		{
//    			System.out.println("contains:"+s);
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
    
    public ArrayList<String> sizeArray1(String size) {
    	boolean addSize = false;
    	ArrayList<String> size1 = new ArrayList<String>();
    	for(String s : sizes)
    	{
    		//시작 지점 찾기
    		if(size.contains(s+" ~"))
    		{
//    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    		//범위 데이터 등록
    		if(addSize)
    		{
//    			System.out.println(s);
    			size1.add(s);		
    		}
    		//끝 지점 찾기
    		if(addSize&&size.contains("~ "+s))
    		{
//    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    	}
    	if(size1.isEmpty())
		{
    		size1.add("FREE");
		}
    
		return size1;
    }
    
    public String key ="11";  // 따로 저장하고 .gitignore에 제외항목에 등록시킬것.!
    public String makeJwtToken(String id) {
        Date now = new Date();
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
            .setIssuer("kim") // (2) 발급자
            .setIssuedAt(now) // (3) 
            .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // (4) 만료시간
            .claim("id", id) // (5)
            .signWith(SignatureAlgorithm.HS256, key.getBytes()) // (6) 암호화 키 (노출되면 안된다.!)
            .compact();
      }
    public Claims parseringJwtToken(String jwtToken) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException
    {
    	return Jwts.parser()
    		.setSigningKey(key.getBytes("UTF-8"))
    		.parseClaimsJws(jwtToken)
    		.getBody();
    }
 // 이메일 난수 만드는 메서드
 	public String init(boolean lowerCheck,int size) {
 		Random ran = new Random();
 		StringBuffer sb = new StringBuffer();
 		int num = 0;

 		do {
 			num = ran.nextInt(75) + 48;
 			if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
 				sb.append((char) num);
 			} else {
 				continue;
 			}

 		} while (sb.length() < size);
 		if (lowerCheck) {
 			return sb.toString().toLowerCase();
 		}
 		return sb.toString();
 	}
}
