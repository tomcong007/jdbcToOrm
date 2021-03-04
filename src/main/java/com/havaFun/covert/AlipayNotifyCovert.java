package com.havaFun.covert;
import com.havaFun.model.AlipayNotify;
import com.havaFun.meta.DataRow;
public class AlipayNotifyCovert  extends BaseCovert<AlipayNotify>{
public  AlipayNotify format(DataRow form){
if(form==null)return null;
AlipayNotify item = new AlipayNotify();
        item.setId(this.parseIntegerItem(form,"id"));
        item.setSign(this.parseStringItem(form, "sign"));
        item.setBuyerId(this.parseStringItem(form, "buyer_id"));
        item.setOutTradeNo(this.parseStringItem(form, "out_trade_no"));
        item.setTotalAmount(this.parseIntegerItem(form,"total_amount"));
        item.setTradeStatus(this.parseStringItem(form, "trade_status"));
        item.setTradeNo(this.parseStringItem(form, "trade_no"));
        item.setReceiptAmount(this.parseIntegerItem(form,"receipt_amount"));
        item.setBuyerPayAmount(this.parseIntegerItem(form,"buyer_pay_amount"));
        item.setSignType(this.parseStringItem(form, "sign_type"));
        item.setCreateDate(this.parseStringItem(form, "create_date"));
return item;
}
public  DataRow parse(AlipayNotify item){
if(item==null)return null;
DataRow form = new DataRow();
        this.formatIntegerForm(form, "id", item.getId());
        this.formatStringForm(form, "sign", item.getSign());
        this.formatStringForm(form, "buyer_id", item.getBuyerId());
        this.formatStringForm(form, "out_trade_no", item.getOutTradeNo());
        this.formatIntegerForm(form, "total_amount", item.getTotalAmount());
        this.formatStringForm(form, "trade_status", item.getTradeStatus());
        this.formatStringForm(form, "trade_no", item.getTradeNo());
        this.formatIntegerForm(form, "receipt_amount", item.getReceiptAmount());
        this.formatIntegerForm(form, "buyer_pay_amount", item.getBuyerPayAmount());
        this.formatStringForm(form, "sign_type", item.getSignType());
        this.formatStringForm(form, "create_date", item.getCreateDate());
return form;
}
	

}
