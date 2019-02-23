package util;

import java.awt.image.SampleModel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import model.Patent;

public class Try {

	public static void main(String[] args) throws IOException {
		
		Map<String,String> map = new HashMap();
		map.put("1", "a");
		map.put("2", "b");
		map.put("3", "c");
		JSONObject json = new JSONObject(map);
//		System.out.println(json);
		
		String s = "{\"a\":\"1\",\"b\":\"2\"}";
		json = new JSONObject(s);
//		System.out.println(json);
		FileUtils util = new FileUtils();
		String sss = util.readFileToString(new File("D:\\1\\java\\Tomcat\\apache-tomcat-9.0.12\\webapps\\TeacherWeb\\src\\util\\a.json"));
		System.out.println(sss);
	}
	
	public  void demo() throws IOException {
		InputStream in = super.getClass().getClassLoader().getResourceAsStream("a.json");
		byte[] b = new byte[10];
		int len = -1;
		StringBuffer s = new StringBuffer();
		while(in.read(b) != -1) {
			String ss = new String(b,0,len);
			s.append(ss);
		}
		System.out.println(s);
	}

	}


