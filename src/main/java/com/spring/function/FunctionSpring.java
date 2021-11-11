package com.spring.function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class FunctionSpring {
	// 파일을 저장해주는 함수 
    // 받은 파일을 SAVE_PATH위치에 임의의 이름으로 저장하고
    // 저장된 이름을 리턴한다.
    // TODO: 중복이 아예 일어날 수 없게 설계되거나 중복시 다시 시도 할 수 있게 짜야함(재귀)
    public static String fileSave(List<MultipartFile> files ,String SAVE_PATH) throws IOException
    {
    	FileOutputStream fos = null;
    	System.out.println(files.size());
    	String imageurl = "";
    	for(int i=0;i<files.size();i++)
    	{
			byte[] fileData = files.get(i).getBytes();
			int ramdomCount= (int)(Math.random()*System.currentTimeMillis()%10000000);
			try {
			fos = new FileOutputStream(SAVE_PATH+"test"+ramdomCount+".png");
			imageurl+="test"+ramdomCount+",";
			}
			catch (Exception e) {
				fos = new FileOutputStream(SAVE_PATH+"test1"+ramdomCount+".png");
				imageurl+="test1"+ramdomCount+",";
			}
			fos.write(fileData);
			
    	}
    	fos.close();
    	return imageurl;
    }
    public static boolean fileDelete(String imageName,String SAVE_PATH)
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
    //사이즈 변환 배열
    public static String sizes[] = {"XS","S","M","L","XL","2XL","3XL","4XL","5XL","6XL","7XL",
    								"XS(80)","S(85)","M(90)","L(95)","XL(100)","2XL(105)","3XL(110)","4XL(115)",
    								"XS(85)","S(90)","M(95)","L(100)","XL(105)","2XL(110)","3XL(115)","4XL(120)",
    								"XS(90)","S(95)","M(100)","L(105)","XL(110)","2XL(115)","3XL(120)","4XL(125)",
    								"85","90","95","100","105","110","115","120","125"};
   
    public static JSONObject sizeArray(String size) {
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
}
