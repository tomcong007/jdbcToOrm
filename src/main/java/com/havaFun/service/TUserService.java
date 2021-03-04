package com.havaFun.service;
import com.havaFun.covert.TUserCovert;
import com.havaFun.model.TUser;
public class TUserService extends PojoBaseService<TUser>{
    private static TUserService service;
    private static TUserCovert covert = new TUserCovert();
	private final static String table = "t_user";
	private TUserService(){
         super(table, covert);
    }
	public static TUserService getInstance(){
	   if(service==null)service = new TUserService();
	   return service;
	}

	

}
