package com.havaFun.model;
public class ${pojoName} extends FormatModel{
<#if propertyList??>
	<#list propertyList as prop>
	private ${prop.prefix} ${prop.value};
	</#list>
</#if>
<#if propertyList??>
	<#list propertyList as prop>
	public void ${prop.setter}(${prop.prefix}  ${prop.value}){
	    this.${prop.value} = ${prop.value};
	}
	public  ${prop.prefix} ${prop.getter}(){
	    return ${prop.value};
	}
	</#list>
</#if>

	
      
}
