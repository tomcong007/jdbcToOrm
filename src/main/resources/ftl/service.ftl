package com.havaFun.service;
import com.havaFun.covert.${pojoName}Covert;
import com.havaFun.model.${pojoName};
public class ${pojoName}Service extends <#if ali==0>PojoBaseService<#else>AliBaseService</#if><${pojoName}>{
    private static ${pojoName}Service service;
    private static ${pojoName}Covert covert = new ${pojoName}Covert();
	private final static String table = "${tableName}";
	private ${pojoName}Service(){
         super(table, covert);
    }
	public static ${pojoName}Service getInstance(){
	   if(service==null)service = new ${pojoName}Service();
	   return service;
	}

	

}
