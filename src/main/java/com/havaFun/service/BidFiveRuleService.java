package com.havaFun.service;
import com.havaFun.covert.BidFiveRuleCovert;
import com.havaFun.model.BidFiveRule;
public class BidFiveRuleService extends PojoBaseService<BidFiveRule>{
    private static BidFiveRuleService service;
    private static BidFiveRuleCovert covert = new BidFiveRuleCovert();
	private final static String table = "bid_five_rule";
	private BidFiveRuleService(){
         super(table, covert);
    }
	public static BidFiveRuleService getInstance(){
	   if(service==null)service = new BidFiveRuleService();
	   return service;
	}

	

}
