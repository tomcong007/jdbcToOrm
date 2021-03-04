package com.havaFun.service;
import com.havaFun.covert.AlipayReturnCovert;
import com.havaFun.model.AlipayReturn;
public class AlipayReturnService extends PojoBaseService<AlipayReturn>{
    private static AlipayReturnService service;
    private static AlipayReturnCovert covert = new AlipayReturnCovert();
	private final static String table = "alipay_return";
	private AlipayReturnService(){
         super(table, covert);
    }
	public static AlipayReturnService getInstance(){
	   if(service==null)service = new AlipayReturnService();
	   return service;
	}

	

}
