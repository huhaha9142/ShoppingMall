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
	
	// ������ �������ִ� �Լ� 
    // ���� ������ SAVE_PATH��ġ�� ������ �̸����� �����ϰ�
    // ����� �̸��� �����Ѵ�.
    // TODO: �ߺ��� �ƿ� �Ͼ �� ���� ����ǰų� �ߺ��� �ٽ� �õ� �� �� �ְ� ¥����(���)
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
    //�ߺ����� ������ �÷� ����
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
    //������ ��ȯ �迭
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
    		//���� ���� ã��
    		if(size.contains(s+" ~"))
    		{
//    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    		//���� ������ ���
    		if(addSize)
    		{
//    			System.out.println(s);
    			Jarr.add(s);
    			
    		}
    		//�� ���� ã��
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
    		//���� ���� ã��
    		if(size.contains(s+" ~"))
    		{
//    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    		//���� ������ ���
    		if(addSize)
    		{
//    			System.out.println(s);
    			size1.add(s);		
    		}
    		//�� ���� ã��
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
    
    public String key ="11";  // ���� �����ϰ� .gitignore�� �����׸� ��Ͻ�ų��.!
    public String makeJwtToken(String id) {
        Date now = new Date();
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
            .setIssuer("kim") // (2) �߱���
            .setIssuedAt(now) // (3) 
            .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30000).toMillis())) // (4) ����ð�
            .claim("id", id) // (5)
            .signWith(SignatureAlgorithm.HS256, key.getBytes()) // (6) ��ȣȭ Ű (����Ǹ� �ȵȴ�.!)
            .compact();
      }
    public Claims parseringJwtToken(String jwtToken) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException
    {
    	return Jwts.parser()
    		.setSigningKey(key.getBytes("UTF-8"))
    		.parseClaimsJws(jwtToken)
    		.getBody();
    }
 // �̸��� ���� ����� �޼���
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
