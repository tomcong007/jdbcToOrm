package com.havaFun.service;
import com.havaFun.covert.BidFourRuleCovert;
import com.havaFun.model.BidFourRule;
public class BidFourRuleService extends PojoBaseService<BidFourRule>{
    private static BidFourRuleService service;
    private static BidFourRuleCovert covert = new BidFourRuleCovert();
	private final static String table = "bid_four_rule";
	private BidFourRuleService(){
         super(table, covert);
    }
	public static BidFourRuleService getInstance(){
	   if(service==null)service = new BidFourRuleService();
	   return service;
	}

	

}
