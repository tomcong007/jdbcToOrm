package com.havaFun.model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
public class FormatModel implements Serializable{
    private static final long serialVersionUID = 1L;
    public Map<String,String> greaterMap = new HashMap<String,String>();
    public Map<String,String> letterMap = new HashMap<String, String>();
    public Map<String,String> notEqualsMap = new HashMap<String,String>();

    public FormatModel notEquals(String fieldName, Object value){
        notEqualsMap.put(fieldName,String.valueOf(value));
        return this;
    }

    public FormatModel greater(String fieldName, String value){
        greaterMap.put(fieldName,value);
        return this;
    }
    public FormatModel greater(String fieldName, Object value){
        greaterMap.put(fieldName,String.valueOf(value));
        return this;
    }
    public FormatModel letter(String fieldName, String value){
        letterMap.put(fieldName,value);
        return this;
    }
    public FormatModel letter(String fieldName, Object value){
        letterMap.put(fieldName,String.valueOf(value));
        return this;
    }

    public Map<String, String> getGreaterMap() {
        return greaterMap;
    }

    public void setGreaterMap(Map<String, String> greaterMap) {
        this.greaterMap = greaterMap;
    }

    public Map<String, String> getLetterMap() {
        return letterMap;
    }

    public void setLetterMap(Map<String, String> letterMap) {
        this.letterMap = letterMap;
    }

    public String getFieldName(String key){
        if(key!=null&&!key.trim().equals("")){
            key = key.trim();
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
                return value.toString();
            }
            return key;
        }
        return null;
    }
    public String getKey(String fieldName){
        if(fieldName==null||!fieldName.trim().equals(""))return null;
        StringBuffer key = new StringBuffer();
        for(int i=0;i<fieldName.length();i++){
            char c = fieldName.charAt(i);
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
        return key.toString();
    }

}
