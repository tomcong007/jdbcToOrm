package com.havaFun.covert;
import com.havaFun.model.BidFourRule;
import com.havaFun.meta.DataRow;
public class BidFourRuleCovert  extends BaseCovert<BidFourRule>{
public  BidFourRule format(DataRow form){
if(form==null)return null;
BidFourRule item = new BidFourRule();
        item.setId(this.parseIntegerItem(form,"id"));
        item.setUserId(this.parseIntegerItem(form,"user_id"));
        item.setAcctId(this.parseIntegerItem(form,"acct_id"));
        item.setShopName(this.parseStringItem(form, "shop_name"));
        item.setEdit(this.parseIntegerItem(form,"edit"));
        item.setLow(this.parseIntegerItem(form,"low"));
        item.setHigh(this.parseIntegerItem(form,"high"));
        item.setStartPos(this.parseIntegerItem(form,"start_pos"));
        item.setEndPos(this.parseIntegerItem(form,"end_pos"));
        item.setBidType(this.parseIntegerItem(form,"bid_type"));
        item.setFlow(this.parseIntegerItem(form,"flow"));
        item.setStatus(this.parseIntegerItem(form,"status"));
return item;
}
public  DataRow parse(BidFourRule item){
if(item==null)return null;
DataRow form = new DataRow();
        this.formatIntegerForm(form, "id", item.getId());
        this.formatIntegerForm(form, "user_id", item.getUserId());
        this.formatIntegerForm(form, "acct_id", item.getAcctId());
        this.formatStringForm(form, "shop_name", item.getShopName());
        this.formatIntegerForm(form, "edit", item.getEdit());
        this.formatIntegerForm(form, "low", item.getLow());
        this.formatIntegerForm(form, "high", item.getHigh());
        this.formatIntegerForm(form, "start_pos", item.getStartPos());
        this.formatIntegerForm(form, "end_pos", item.getEndPos());
        this.formatIntegerForm(form, "bid_type", item.getBidType());
        this.formatIntegerForm(form, "flow", item.getFlow());
        this.formatIntegerForm(form, "status", item.getStatus());
return form;
}
	

}
