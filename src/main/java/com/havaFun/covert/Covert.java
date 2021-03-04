package com.havaFun.covert;

import com.havaFun.meta.DataRow;

public interface Covert<T> {
	public  T format(DataRow form);
	public  DataRow parse(T item);
	
}
