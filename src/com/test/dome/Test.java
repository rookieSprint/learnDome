package com.test.dome;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

import com.dyl.bean.Person;
import com.dyl.creatBean.ClassPathXmlApplicationContext;
import com.dyl.entity.Bean;
import com.dyl.utils.ConfigManager;

public class Test {

	private static  Map<String,Bean> map = null;
	public static void main(String[] args) {
		
		Person person = (Person) (new ClassPathXmlApplicationContext()).getBean("person");
		System.out.println(person.getName());
		
		
	}
	
	public void testReadXml(){
		map = ConfigManager.getConfig("/applicationContext.xml");
		/*for (String beanId : map.keySet()) {
			System.out.println(beanId);
			Bean bean = map.get(beanId);
			System.out.println(bean);
		}*/
		
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			//Map.Entry entry = (Map.Entry)iterator.next();
			Entry entry = (Entry) iterator.next();
			System.out.println(entry.getValue());
		}
		Map<String, String> testMap = new HashMap<>();
	}

}
