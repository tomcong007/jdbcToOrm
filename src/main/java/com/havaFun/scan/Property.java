package com.havaFun.scan;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Property implements Serializable{
	private static final long serialVersionUID = 1L;
    private String value;
    private String prefix;
    private String setter;
    private String getter;
    private String key;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		if(StringUtils.isNotEmpty(value)&&value.length()>1){
             this.setter = String.format("set%s%s",value.substring(0,1).toUpperCase(),value.substring(1));
             this.getter = String.format("get%s%s",value.substring(0,1).toUpperCase(),value.substring(1));
             StringBuffer key = new StringBuffer();
			 for(int i=0;i<value.length();i++){
			 	  char c = value.charAt(i);
			 	  if(i==0){
			 	  	key.append(String.valueOf(c).toLowerCase());
				  }else{
			 	  	   if(Character.isUpperCase(c)){
                           key.append("_").append(String.valueOf(c).toLowerCase());
					   }else{
			 	  	   	  key.append(c);
					   }
				  }
			 }
			 this.setKey(key.toString());
		}
		this.value = value;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSetter() {
		return setter;
	}
	public void setSetter(String setter) {
		this.setter = setter;
	}
	public String getGetter() {
		return getter;
	}
	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		if(StringUtils.isNotEmpty(key)){
			String[] words = key.split("_");
			if(words!=null&&words.length>0){
				StringBuffer value = new StringBuffer();
				for(String word:words){
					if(value.length()==0){
						value.append(word.substring(0,1).toLowerCase());
					}else{
						value.append(word.substring(0,1).toUpperCase());
					}
					if(word.length()>1)value.append(word.substring(1).toLowerCase());
				}
				this.setter = String.format("set%s%s",value.substring(0,1).toUpperCase(),value.substring(1));
				this.getter = String.format("get%s%s",value.substring(0,1).toUpperCase(),value.substring(1));
				this.value = value.toString();
			}
		}
		this.key = key;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this, SerializerFeature.PrettyFormat);
	}
}
