package com.dyl.entity;

import java.util.List;

public class Bean {

	private String name;
	private String className;
	private String scope = "singleton";
	private List<Property> property;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public List<Property> getProperty() {
		return property;
	}
	public void setProperty(List<Property> property) {
		this.property = property;
	}
	@Override
	public String toString() {
		return "Bean [name=" + name + ", className=" + className + ", property=" + property + "]";
	}
	
	
	
	
}
