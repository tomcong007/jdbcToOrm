package com.havaFun.covert;
import com.havaFun.model.AlipayReturn;
import com.havaFun.meta.DataRow;
public class AlipayReturnCovert  extends BaseCovert<AlipayReturn>{
public  AlipayReturn format(DataRow form){
if(form==null)return null;
AlipayReturn item = new AlipayReturn();
        item.setId(this.parseIntegerItem(form,"id"));
        item.setUserId(this.parseIntegerItem(form,"user_id"));
        item.setShopId(this.parseIntegerItem(form,"shop_id"));
        item.setType(this.parseIntegerItem(form,"type"));
        item.setOutTradeNo(this.parseStringItem(form, "out_trade_no"));
        item.setTotalAmount(this.parseIntegerItem(form,"total_amount"));
        item.setSign(this.parseStringItem(form, "sign"));
        item.setTradeNo(this.parseStringItem(form, "trade_no"));
        item.setSignType(this.parseStringItem(form, "sign_type"));
        item.setState(this.parseIntegerItem(form,"state"));
        item.setSellerId(this.parseStringItem(form, "seller_id"));
        item.setDay(this.parseIntegerItem(form,"day"));
        item.setReturnDate(this.parseStringItem(form, "return_date"));
        item.setCreateDate(this.parseStringItem(form, "create_date"));
return item;
}
public  DataRow parse(AlipayReturn item){
if(item==null)return null;
DataRow form = new DataRow();
        this.formatIntegerForm(form, "id", item.getId());
        this.formatIntegerForm(form, "user_id", item.getUserId());
        this.formatIntegerForm(form, "shop_id", item.getShopId());
        this.formatIntegerForm(form, "type", item.getType());
        this.formatStringForm(form, "out_trade_no", item.getOutTradeNo());
        this.formatIntegerForm(form, "total_amount", item.getTotalAmount());
        this.formatStringForm(form, "sign", item.getSign());
        this.formatStringForm(form, "trade_no", item.getTradeNo());
        this.formatStringForm(form, "sign_type", item.getSignType());
        this.formatIntegerForm(form, "state", item.getState());
        this.formatStringForm(form, "seller_id", item.getSellerId());
        this.formatIntegerForm(form, "day", item.getDay());
        this.formatStringForm(form, "return_date", item.getReturnDate());
        this.formatStringForm(form, "create_date", item.getCreateDate());
return form;
}
	

}
