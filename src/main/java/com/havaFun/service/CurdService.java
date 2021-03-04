package com.havaFun.service;
import com.havaFun.meta.Message;
import java.util.List;
public interface CurdService<T> {
    public Message saveItem(T item);
    public Message delItem(long id);
    public T findOne(long id);
    public Message updateItem(T act);
    public List<T> findItemList();
}
