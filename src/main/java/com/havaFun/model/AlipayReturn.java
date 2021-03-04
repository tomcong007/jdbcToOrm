package com.havaFun.model;
public class AlipayReturn extends FormatModel{
	private Integer id;
	private Integer userId;
	private Integer shopId;
	private Integer type;
	private String outTradeNo;
	private Integer totalAmount;
	private String sign;
	private String tradeNo;
	private String signType;
	private Integer state;
	private String sellerId;
	private Integer day;
	private String returnDate;
	private String createDate;
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
	public void setShopId(Integer  shopId){
	    this.shopId = shopId;
	}
	public  Integer getShopId(){
	    return shopId;
	}
	public void setType(Integer  type){
	    this.type = type;
	}
	public  Integer getType(){
	    return type;
	}
	public void setOutTradeNo(String  outTradeNo){
	    this.outTradeNo = outTradeNo;
	}
	public  String getOutTradeNo(){
	    return outTradeNo;
	}
	public void setTotalAmount(Integer  totalAmount){
	    this.totalAmount = totalAmount;
	}
	public  Integer getTotalAmount(){
	    return totalAmount;
	}
	public void setSign(String  sign){
	    this.sign = sign;
	}
	public  String getSign(){
	    return sign;
	}
	public void setTradeNo(String  tradeNo){
	    this.tradeNo = tradeNo;
	}
	public  String getTradeNo(){
	    return tradeNo;
	}
	public void setSignType(String  signType){
	    this.signType = signType;
	}
	public  String getSignType(){
	    return signType;
	}
	public void setState(Integer  state){
	    this.state = state;
	}
	public  Integer getState(){
	    return state;
	}
	public void setSellerId(String  sellerId){
	    this.sellerId = sellerId;
	}
	public  String getSellerId(){
	    return sellerId;
	}
	public void setDay(Integer  day){
	    this.day = day;
	}
	public  Integer getDay(){
	    return day;
	}
	public void setReturnDate(String  returnDate){
	    this.returnDate = returnDate;
	}
	public  String getReturnDate(){
	    return returnDate;
	}
	public void setCreateDate(String  createDate){
	    this.createDate = createDate;
	}
	public  String getCreateDate(){
	    return createDate;
	}

	
      
}
