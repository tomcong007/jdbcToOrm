package com.havaFun.covert;
import com.havaFun.model.AreaBid;
import com.havaFun.meta.DataRow;
public class AreaBidCovert  extends BaseCovert<AreaBid>{
public  AreaBid format(DataRow form){
if(form==null)return null;
AreaBid item = new AreaBid();
        item.setId(this.parseIntegerItem(form,"id"));
        item.setBizadCityId(this.parseIntegerItem(form,"bizad_city_id"));
        item.setBizadSecondCityId(this.parseIntegerItem(form,"bizad_second_city_id"));
        item.setBizadThirdCityId(this.parseIntegerItem(form,"bizad_third_city_id"));
        item.setBadPrice(this.parseIntegerItem(form,"bad_price"));
        item.setSuperPrice(this.parseIntegerItem(form,"super_price"));
        item.setAveragePrice(this.parseIntegerItem(form,"average_price"));
        item.setGeneralPrice(this.parseIntegerItem(form,"general_price"));
        item.setPos(this.parseIntegerItem(form,"pos"));
        item.setViewDay(this.parseIntegerItem(form,"view_day"));
return item;
}
public  DataRow parse(AreaBid item){
if(item==null)return null;
DataRow form = new DataRow();
        this.formatIntegerForm(form, "id", item.getId());
        this.formatIntegerForm(form, "bizad_city_id", item.getBizadCityId());
        this.formatIntegerForm(form, "bizad_second_city_id", item.getBizadSecondCityId());
        this.formatIntegerForm(form, "bizad_third_city_id", item.getBizadThirdCityId());
        this.formatIntegerForm(form, "bad_price", item.getBadPrice());
        this.formatIntegerForm(form, "super_price", item.getSuperPrice());
        this.formatIntegerForm(form, "average_price", item.getAveragePrice());
        this.formatIntegerForm(form, "general_price", item.getGeneralPrice());
        this.formatIntegerForm(form, "pos", item.getPos());
        this.formatIntegerForm(form, "view_day", item.getViewDay());
return form;
}
	

}
