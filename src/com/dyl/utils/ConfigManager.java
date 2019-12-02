package com.dyl.utils;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dyl.entity.Bean;
import com.dyl.entity.Property;

public class ConfigManager {
	private static Map<String,Bean> map = new HashMap<>();
	
	public static Map<String,Bean> getConfig(String configPath){
		//1.创建一个解析器
		SAXReader saxReader = new SAXReader();
		//2.加载配置文件，得到document对象
		InputStream iStream = ConfigManager.class.getResourceAsStream(configPath);
		Document document = null;
		try {
			document= saxReader.read(iStream);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//3.定义xpath表达式，取出所以普的bean元素
		String xpath = "//bean";
		List<Element> list = document.selectNodes(xpath);
		addBeanToMap(list);
		return map;
	}
	
	
	
	private static void addBeanToMap(List<Element> list){
		if (list!=null) {
			for (Element bean : list) {
				Bean bean2 = new Bean();
				String bname = bean.attributeValue("name");
				String bclazz = bean.attributeValue("class");
				String bScope = bean.attributeValue("scope");
				bean2.setClassName(bclazz);
				bean2.setName(bname);
				if (bScope != null) {
					bean2.setScope(bScope);
				}
				List<Element> children = bean.elements("property");
				bean2.setProperty(getProperty(children));
				map.put(bname,bean2);
			}
		}
	}
	
	private static List<Property> listProperty = new ArrayList<>();
	private static List<Property> getProperty(List<Element> children){
		if (children!=null) {
			for (Element child : children) {
				Property property = new Property();
				String pName = child.attributeValue("name");
				String pValue = child.attributeValue("value");
				String pRef = child.attributeValue("ref");
				property.setName(pName);
				property.setValue(pValue);
				property.setRef(pRef);
				listProperty.add(property);
			}
			return listProperty;
		}
		return null;
	}
}
