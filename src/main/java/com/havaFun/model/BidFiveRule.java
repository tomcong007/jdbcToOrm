package com.havaFun.model;
public class BidFiveRule extends FormatModel{
	private Integer id;
	private Integer userId;
	private Integer acctId;
	private String shopName;
	private Integer edit;
	private Integer low;
	private Integer high;
	private Integer startPos;
	private Integer endPos;
	private Integer bid;
	private Integer status;
	private FunDate createDate;
	private String createDateStr;
	public void setId(Integer  id){
	    this.id = id;
	}
	public  Integer getId(){
	    return id;
	}
	public void setUserId(Integer  userId){
	    this.userId = userId;
	}
	public  Integer getUserId(){
	    return userId;
	}
	public void setAcctId(Integer  acctId){
	    this.acctId = acctId;
	}
	public  Integer getAcctId(){
	    return acctId;
	}
	public void setShopName(String  shopName){
	    this.shopName = shopName;
	}
	public  String getShopName(){
	    return shopName;
	}
	public void setEdit(Integer  edit){
	    this.edit = edit;
	}
	public  Integer getEdit(){
	    return edit;
	}
	public void setLow(Integer  low){
	    this.low = low;
	}
	public  Integer getLow(){
	    return low;
	}
	public void setHigh(Integer  high){
	    this.high = high;
	}
	public  Integer getHigh(){
	    return high;
	}
	public void setStartPos(Integer  startPos){
	    this.startPos = startPos;
	}
	public  Integer getStartPos(){
	    return startPos;
	}
	public void setEndPos(Integer  endPos){
	    this.endPos = endPos;
	}
	public  Integer getEndPos(){
	    return endPos;
	}
	public void setBid(Integer  bid){
	    this.bid = bid;
	}
	public  Integer getBid(){
	    return bid;
	}
	public void setStatus(Integer  status){
	    this.status = status;
	}
	public  Integer getStatus(){
	    return status;
	}
	public void setCreateDate(FunDate  createDate){
	    this.createDate = createDate;
	}
	public  FunDate getCreateDate(){
	    return createDate;
	}
	public void setCreateDateStr(String  createDateStr){
	    this.createDateStr = createDateStr;
	}
	public  String getCreateDateStr(){
	    return createDateStr;
	}

	
      
}
