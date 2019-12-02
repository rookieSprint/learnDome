package com.dyl.creatBean;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.dyl.entity.Bean;
import com.dyl.entity.Property;
import com.dyl.utils.ConfigManager;

public class ClassPathXmlApplicationContext implements BeanFactory{

	//bean信息
	private Map<String,Bean> beanMap;
	
	//ioc容器
	private Map<String,Object> iocMap = new HashMap<String, Object>();
	
	//初始化容器
	public ClassPathXmlApplicationContext() {
		//获得配置文件中bean的map信息
		beanMap = ConfigManager.getConfig("/applicationContext.xml");
		Set<Entry<String, Bean>> set = beanMap.entrySet();
		//初始化bean并加入IOC容器
		for (Entry<String, Bean> beanId : set) {
			String beanName = beanId.getKey();
			Bean bean = beanId.getValue();
			Object iocBean = iocMap.get(beanName);
			if (iocBean == null || bean.getScope().equals("singleton")) {
				Object objBean = createBean(bean);
				iocMap.put(beanName, objBean);
			}
		}
	}
	public  Object createBean(Bean bean){
		// 创建该类对象
        Class clazz = null;
        try {
            clazz = Class.forName(bean.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object beanObj = null;
        try {
            beanObj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bean.getProperty() != null) {
			for (Property prop : bean.getProperty()) {
				String name = prop.getName();
                String value = prop.getValue();
                try {
					beanObj = setField(name,value,clazz);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IntrospectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        
        
      /* // 获得bean的属性,将其注入
        if (bean.getProperty() != null) {
            for (Property prop : bean.getProperty()) {
                String name = prop.getName();
                String value = prop.getValue();
                String ref = prop.getRef();
                // 使用BeanUtils工具类完成属性注入,可以自动完成类型转换
                // 如果value不为null,说明有
                if (value != null) {
                    try {
						BeanUtils.setProperty(beanObj, name, value);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        }*/
        return beanObj;
	}
	
	public static Object setField(String name,Object value, Class clazz) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object obj = clazz.newInstance();
		PropertyDescriptor descriptor = new PropertyDescriptor(name, clazz);
		Method setMethod = descriptor.getWriteMethod();
		setMethod.invoke(obj, value);
		return obj;
	}
	
	@Override
	public Object getBean(String name) {
		Object bean = iocMap.get(name);
		if (bean == null) {
			 bean = createBean(beanMap.get(name));
		}
		return bean;
	}

}
