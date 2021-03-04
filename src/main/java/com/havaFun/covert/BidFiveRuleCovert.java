package com.havaFun.covert;
import com.havaFun.model.BidFiveRule;
import com.havaFun.meta.DataRow;
public class BidFiveRuleCovert  extends BaseCovert<BidFiveRule>{
public  BidFiveRule format(DataRow form){
if(form==null)return null;
BidFiveRule item = new BidFiveRule();
        item.setId(this.parseIntegerItem(form,"id"));
        item.setUserId(this.parseIntegerItem(form,"user_id"));
        item.setAcctId(this.parseIntegerItem(form,"acct_id"));
        item.setShopName(this.parseStringItem(form, "shop_name"));
        item.setEdit(this.parseIntegerItem(form,"edit"));
        item.setLow(this.parseIntegerItem(form,"low"));
        item.setHigh(this.parseIntegerItem(form,"high"));
        item.setStartPos(this.parseIntegerItem(form,"start_pos"));
        item.setEndPos(this.parseIntegerItem(form,"end_pos"));
        item.setBid(this.parseIntegerItem(form,"bid"));
        item.setStatus(this.parseIntegerItem(form,"status"));
        item.setCreateDateStr(this.parseStringItem(form, "create_date_str"));
return item;
}
public  DataRow parse(BidFiveRule item){
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
        this.formatIntegerForm(form, "bid", item.getBid());
        this.formatIntegerForm(form, "status", item.getStatus());
        this.formatStringForm(form, "create_date_str", item.getCreateDateStr());
return form;
}
	

}
