package com.havaFun.service;
import com.havaFun.covert.AreaBidCovert;
import com.havaFun.model.AreaBid;
public class AreaBidService extends PojoBaseService<AreaBid>{
    private static AreaBidService service;
    private static AreaBidCovert covert = new AreaBidCovert();
	private final static String table = "area_bid";
	private AreaBidService(){
         super(table, covert);
    }
	public static AreaBidService getInstance(){
	   if(service==null)service = new AreaBidService();
	   return service;
	}

	

}
