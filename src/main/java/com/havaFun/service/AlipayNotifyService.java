package com.havaFun.service;
import com.havaFun.covert.AlipayNotifyCovert;
import com.havaFun.model.AlipayNotify;
public class AlipayNotifyService extends PojoBaseService<AlipayNotify>{
    private static AlipayNotifyService service;
    private static AlipayNotifyCovert covert = new AlipayNotifyCovert();
	private final static String table = "alipay_notify";
	private AlipayNotifyService(){
         super(table, covert);
    }
	public static AlipayNotifyService getInstance(){
	   if(service==null)service = new AlipayNotifyService();
	   return service;
	}

	

}
