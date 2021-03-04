package com.havaFun.covert;
import com.havaFun.model.TUser;
import com.havaFun.meta.DataRow;
public class TUserCovert  extends BaseCovert<TUser>{
public  TUser format(DataRow form){
if(form==null)return null;
TUser item = new TUser();
        item.setId(this.parseIntegerItem(form,"id"));
        item.setPid(this.parseIntegerItem(form,"pid"));
        item.setUsername(this.parseStringItem(form, "username"));
        item.setName(this.parseStringItem(form, "name"));
        item.setPassword(this.parseStringItem(form, "password"));
        item.setUrl(this.parseStringItem(form, "url"));
        item.setPhone(this.parseStringItem(form, "phone"));
        item.setContent(this.parseStringItem(form, "content"));
        item.setRoles(this.parseStringItem(form, "roles"));
        item.setSystemName(this.parseStringItem(form, "system_name"));
        item.setState(this.parseIntegerItem(form,"state"));
        item.setSex(this.parseIntegerItem(form,"sex"));
        item.setImgUrl(this.parseStringItem(form, "img_url"));
return item;
}
public  DataRow parse(TUser item){
if(item==null)return null;
DataRow form = new DataRow();
        this.formatIntegerForm(form, "id", item.getId());
        this.formatIntegerForm(form, "pid", item.getPid());
        this.formatStringForm(form, "username", item.getUsername());
        this.formatStringForm(form, "name", item.getName());
        this.formatStringForm(form, "password", item.getPassword());
        this.formatStringForm(form, "url", item.getUrl());
        this.formatStringForm(form, "phone", item.getPhone());
        this.formatStringForm(form, "content", item.getContent());
        this.formatStringForm(form, "roles", item.getRoles());
        this.formatStringForm(form, "system_name", item.getSystemName());
        this.formatIntegerForm(form, "state", item.getState());
        this.formatIntegerForm(form, "sex", item.getSex());
        this.formatStringForm(form, "img_url", item.getImgUrl());
return form;
}
	

}
