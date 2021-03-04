package com.havaFun.model;
public class AlipayNotify extends FormatModel{
	private Integer id;
	private String sign;
	private String buyerId;
	private String outTradeNo;
	private Integer totalAmount;
	private String tradeStatus;
	private String tradeNo;
	private Integer receiptAmount;
	private Integer buyerPayAmount;
	private String signType;
	private String createDate;
	public void setId(Integer  id){
	    this.id = id;
	}
	public  Integer getId(){
	    return id;
	}
	public void setSign(String  sign){
	    this.sign = sign;
	}
	public  String getSign(){
	    return sign;
	}
	public void setBuyerId(String  buyerId){
	    this.buyerId = buyerId;
	}
	public  String getBuyerId(){
	    return buyerId;
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
	public void setTradeStatus(String  tradeStatus){
	    this.tradeStatus = tradeStatus;
	}
	public  String getTradeStatus(){
	    return tradeStatus;
	}
	public void setTradeNo(String  tradeNo){
	    this.tradeNo = tradeNo;
	}
	public  String getTradeNo(){
	    return tradeNo;
	}
	public void setReceiptAmount(Integer  receiptAmount){
	    this.receiptAmount = receiptAmount;
	}
	public  Integer getReceiptAmount(){
	    return receiptAmount;
	}
	public void setBuyerPayAmount(Integer  buyerPayAmount){
	    this.buyerPayAmount = buyerPayAmount;
	}
	public  Integer getBuyerPayAmount(){
	    return buyerPayAmount;
	}
	public void setSignType(String  signType){
	    this.signType = signType;
	}
	public  String getSignType(){
	    return signType;
	}
	public void setCreateDate(String  createDate){
	    this.createDate = createDate;
	}
	public  String getCreateDate(){
	    return createDate;
	}

	
      
}
