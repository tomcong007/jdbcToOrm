package com.havaFun.covert;
import com.havaFun.model.${pojoName};
import com.havaFun.meta.DataRow;
public class ${pojoName}Covert  extends BaseCovert<${pojoName}>{
public  ${pojoName} format(DataRow form){
if(form==null)return null;
${pojoName} item = new ${pojoName}();
<#if propertyList??>
    <#list propertyList as prop>
        <#if prop.prefix=="Integer">
        item.${prop.setter}(this.parseIntegerItem(form,"${prop.key}"));
        </#if>
        <#if prop.prefix=="Long">
        item.${prop.setter}(this.parseLongItem(form,"${prop.key}"));
        </#if>
        <#if prop.prefix=="Double">
        item.${prop.setter}(this.parseDoubleItem(form,"${prop.key}"));
        </#if>
        <#if prop.prefix=="String">
        item.${prop.setter}(this.parseStringItem(form, "${prop.key}"));
        </#if>
        <#if prop.prefix=="ErpDate">
        item.${prop.setter}(this.parseErpDateItem(form,"${prop.key}"));
        </#if>
    </#list>
</#if>
return item;
}
public  DataRow parse(${pojoName} item){
if(item==null)return null;
DataRow form = new DataRow();
<#if propertyList??>
    <#list propertyList as prop>
        <#if prop.prefix=="Integer">
        this.formatIntegerForm(form, "${prop.key}", item.${prop.getter}());
        </#if>
        <#if prop.prefix=="Long">
        this.formatLongForm(form, "${prop.key}", item.${prop.getter}());
        </#if>
        <#if prop.prefix=="Double">
        this.formatDoubleForm(form, "${prop.key}", item.${prop.getter}());
        </#if>
        <#if prop.prefix=="Float">
        this.formatDoubleForm(form, "${prop.key}", item.${prop.getter}());
        </#if>
        <#if prop.prefix=="String">
        this.formatStringForm(form, "${prop.key}", item.${prop.getter}());
        </#if>
        <#if prop.prefix=="ErpDate">
        this.formatDateForm(form,"${prop.key}",item.${prop.getter}());
        </#if>
    </#list>
</#if>
return form;
}
	

}
