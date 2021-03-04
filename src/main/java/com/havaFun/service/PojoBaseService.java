package com.havaFun.service;
import com.havaFun.covert.BaseCovert;
import com.havaFun.except.JdbcException;
import com.havaFun.meta.DataRow;
import com.havaFun.meta.Message;
import com.havaFun.model.FormatModel;
import com.havaFun.transaction.SessionParams;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PojoBaseService<T> extends ProjectBaseService implements CurdService<T> {
    private String table;
    private BaseCovert<T> covert;

    public Message delItem(long id) {
        // TODO Auto-generated method stub
        try {
            this.deleteById(table, id);
            return new Message();
        }catch (JdbcException e){
            e.fillInStackTrace();
            return new Message("系统错误");
        }
    }

    protected PojoBaseService(String table, BaseCovert<T> covert) {
        this.table = table;
        this.covert = covert;
    }

    public int[] queryIntArray(String sql,Object[] args){
        return handler.queryIntArray(sql,args);
    }
    public int queryInt(String sql,Object[] args){
        return handler.queryInt(sql,args);
    }


    public List<T> findItemList() {
        // TODO Auto-generated method stub
        return covert.formatList(handler.query(String.format("select * from %s", table)));
    }
    public int count(){
        return handler.queryInt(String.format("select count(*) from %s where 1=1 ", table));
    }

    public int count(T item){
        if (!(item instanceof FormatModel)) return 0;
        FormatModel model = (FormatModel) item;
        StringBuffer sql = new StringBuffer(String.format("select count(*) from %s where 1=1 ", table));
        List<Object> args = new ArrayList<Object>();
        Field[] fs = item.getClass().getDeclaredFields();
        if (fs == null || fs.length == 0) return 0;
        List<String> fieldNames = new ArrayList<String>();
        for (Field field : fs) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("serialVersionUID")) continue;
            field.setAccessible(true);
            fieldNames.add(fieldName);
            try {

                Object value = field.get(item);
                if (value == null) continue;
                String key = model.getKey(fieldName);
                sql.append(String.format(" and %s=? ", key));
                args.add(value);

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Map<String,String> greaterMap =  model.greaterMap;
        Map<String,String> letterMap = model.letterMap;
        Map<String,String> notEqualsMap = model.notEqualsMap;
        if(greaterMap!=null&&greaterMap.size()>0){
            for(Iterator<String> it = greaterMap.keySet().iterator();it.hasNext();){
                String fieldName = it.next();
                if(!fieldNames.contains(fieldName))continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s >%s ",key,greaterMap.get(fieldName)));
            }
        }
        if(letterMap!=null&&letterMap.size()>0){
            for(Iterator<String> it = letterMap.keySet().iterator();it.hasNext();){
                String fieldName = it.next();
                if(!fieldNames.contains(fieldName))continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s <%s ",key,letterMap.get(fieldName)));
            }
        }
        if(notEqualsMap!=null&&notEqualsMap.size()>0){
            for(Iterator<String> it = notEqualsMap.keySet().iterator();it.hasNext();){
                String fieldName = it.next();
                if(!fieldNames.contains(fieldName))continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s !=%s ",key,notEqualsMap.get(key)));
            }
        }
        return handler.queryInt(sql.toString(), args.toArray());
    }
    public void invokeTasks(List<? extends Callable<String>> tasks){
        if(tasks==null||tasks.size()==0)return;
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(tasks.size());
        try {
            cachedThreadPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            cachedThreadPool.shutdown();
            if(cachedThreadPool.awaitTermination(2*60*1000, TimeUnit.MILLISECONDS)){
                cachedThreadPool.shutdownNow();
            }
        }catch (InterruptedException e){
            cachedThreadPool.shutdownNow();
        }
    }

    public List<T> findAll(T item) {
        if (!(item instanceof FormatModel)) return null;
        FormatModel model = (FormatModel) item;
        StringBuffer sql = new StringBuffer(String.format("select * from %s where 1=1 ", table));
        List<Object> args = new ArrayList<Object>();
        Field[] fs = item.getClass().getDeclaredFields();
        if (fs == null || fs.length == 0) return null;
        for (Field field : fs) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("serialVersionUID")) continue;
            field.setAccessible(true);
            try {

                Object value = field.get(item);
                if (value == null) continue;
                String key = model.getKey(fieldName);
                sql.append(String.format(" and %s=? ", key));
                args.add(value);

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return covert.formatList(handler.query(sql.toString(), args.toArray()));
    }
    public List<T> findAllByWhere(String buffer,Object[] args){
        StringBuffer sql = new StringBuffer(String.format("select * from %s where 1= 1 %s", table,buffer));
        return covert.formatList(handler.query(sql.toString(), args));
    }

    public String[] queryStringArray(String sql,Object[] args){
        return handler.queryStringArray(sql,args);
    }


    public String queryString(String sql,Object[] args){
        return handler.queryString(sql,args);
    }



    public List<T> findItemList(String sql, Object[] args) {
        // TODO Auto-generated method stub
        return covert.formatList(handler.query(sql, args));
    }

    public List<T> findItemList(String sql) {
        // TODO Auto-generated method stub
        return covert.formatList(handler.query(sql));
    }

    public T findItem(T item) {
        List<T> list = findItemList(item);
        if (list == null || list.size() == 0) return null;
        return list.get(0);
    }
    public T findItem(T item,String buffer) {
        List<T> list = findItemList(item,buffer);
        if (list == null || list.size() == 0) return null;
        return list.get(0);
    }
    public SessionParams getSaveItemSession(T item) {
        return handler.getInsertSql(table, covert.parse(item));
    }

    public SessionParams getUpdateItemSession(T item) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("update " + table + " set ");
        int i = 0;
        ArrayList valueList = new ArrayList();
        DataRow form = covert.parse(item);
        Iterator iter = form.keySet().iterator();
        while (iter.hasNext()) {
            ++i;
            String key = (String) iter.next();
            valueList.add(form.get(key));
            if (i < form.size()) {
                sqlBuffer.append(key + "=?,");
            } else {
                sqlBuffer.append(key + "=?");
            }
        }
        sqlBuffer.append(" where  id =?");
        valueList.add(form.getLong("id"));
        return  new SessionParams(sqlBuffer.toString(), valueList.toArray());
    }

    public SessionParams getDelItemSession(int id){
        return new SessionParams("delete from "+table+" where id =?",new Object[]{id});
    }

    public List<T> findItemList(T item) {
        if (!(item instanceof FormatModel)) return null;
        FormatModel model = (FormatModel) item;
        StringBuffer sql = new StringBuffer(String.format("select * from %s where 1=1 ", table));
        List<Object> args = new ArrayList<Object>();
        Field[] fs = item.getClass().getDeclaredFields();
        if (fs == null || fs.length == 0) return null;
        List<String> fieldNames = new ArrayList<String>();
        for (Field field : fs) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("serialVersionUID")) continue;
            field.setAccessible(true);
            fieldNames.add(fieldName);
            try {
                Object value = field.get(item);
                if (value == null) continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s=? ", key));
                args.add(value);

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Map<String,String> greaterMap =  model.greaterMap;
        Map<String,String> letterMap = model.letterMap;
        Map<String,String> notEqualsMap = model.notEqualsMap;
        if(greaterMap!=null&&greaterMap.size()>0){
            for(Iterator<String> it = greaterMap.keySet().iterator();it.hasNext();){
                String fieldName = it.next();
                if(!fieldNames.contains(fieldName))continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s >%s ",key,greaterMap.get(fieldName)));
            }
        }
        if(letterMap!=null&&letterMap.size()>0){
            for(Iterator<String> it = letterMap.keySet().iterator();it.hasNext();){
                String fieldName = it.next();
                if(!fieldNames.contains(fieldName))continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s <%s ",key,letterMap.get(fieldName)));
            }
        }
        if(notEqualsMap!=null&&notEqualsMap.size()>0){
            for(Iterator<String> it = notEqualsMap.keySet().iterator();it.hasNext();){
                String fieldName = it.next();
                if(!fieldNames.contains(fieldName))continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s !=%s ",key,notEqualsMap.get(key)));
            }
        }
        List<DataRow> list = handler.query(sql.toString(), args.toArray());
        return covert.formatList(list);
    }


    public List<T> findItemListByBuffer(String buffer,Object[] params) {
        StringBuffer sql = new StringBuffer(String.format("select * from %s where 1=1  %s", table,buffer));
        List<Object> args = new ArrayList<Object>();
        if(buffer!=null&&!buffer.trim().equals("")){
            sql.append(buffer);
            if(params!=null&&params.length>0){
                for(Object param:params){
                    args.add(param);
                }
            }
        }
        List<DataRow> list = handler.query(sql.toString(), args.toArray());
        return covert.formatList(list);
    }

    public List<T> findItemList(T item,String buffer){
        return findItemList(item,buffer,null);
    }
    public List<T> findItemList(T item,int limit){
        return findItemList(item,null,null,limit);
    }
    public List<T> findItemList(T item,String buffer,Object[] params) {
        if (!(item instanceof FormatModel)) return null;
        FormatModel model = (FormatModel) item;
        StringBuffer sql = new StringBuffer(String.format("select * from %s where 1=1 ", table));
        List<Object> args = new ArrayList<Object>();
        Field[] fs = item.getClass().getDeclaredFields();
        if (fs == null || fs.length == 0) return null;
        List<String> fieldNames = new ArrayList<String>();
        for (Field field : fs) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("serialVersionUID")) continue;
            field.setAccessible(true);
            fieldNames.add(fieldName);
            try {
                Object value = field.get(item);
                if (value == null) continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s=? ", key));
                args.add(value);

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Map<String,String> greaterMap =  model.greaterMap;
        Map<String,String> letterMap = model.letterMap;
        Map<String,String> notEqualsMap = model.notEqualsMap;
        if(greaterMap!=null&&greaterMap.size()>0){
            for(Iterator<String> it = greaterMap.keySet().iterator();it.hasNext();){
                String fieldName = it.next();
                if(!fieldNames.contains(fieldName))continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s >%s ",key,greaterMap.get(fieldName)));
            }
        }
        if(letterMap!=null&&letterMap.size()>0){
            for(Iterator<String> it = letterMap.keySet().iterator();it.hasNext();){
                String fieldName = it.next();
                if(!fieldNames.contains(fieldName))continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s <%s ",key,letterMap.get(fieldName)));
            }
        }
        if(notEqualsMap!=null&&notEqualsMap.size()>0){
            for(Iterator<String> it = notEqualsMap.keySet().iterator();it.hasNext();){
                String fieldName = it.next();
                if(!fieldNames.contains(fieldName))continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s !=%s ",key,notEqualsMap.get(fieldName)));
            }
        }
        if(buffer!=null&&!buffer.trim().equals("")){
            sql.append(buffer);
            if(params!=null&&params.length>0){
                for(Object param:params){
                    args.add(param);
                }
            }
        }
        List<DataRow> list = handler.query(sql.toString(), args.toArray());
        return covert.formatList(list);
    }
    public List<T> findItemList(T item,String buffer,int limit){
        return findItemList(item,buffer,null,limit);
    }
    public List<T> findItemList(T item,String buffer,Object[] params,int limit) {
        if (!(item instanceof FormatModel)) return null;
        FormatModel model = (FormatModel) item;
        StringBuffer sql = new StringBuffer(String.format("select * from %s where 1=1 ", table));
        List<Object> args = new ArrayList<Object>();
        Field[] fs = item.getClass().getDeclaredFields();
        if (fs == null || fs.length == 0) return null;
        for (Field field : fs) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("serialVersionUID")) continue;
            field.setAccessible(true);
            try {

                Object value = field.get(item);
                if (value == null) continue;
                String key = getKey(fieldName);
                sql.append(String.format(" and %s=? ", key));
                args.add(value);

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(buffer!=null&&!buffer.trim().equals("")){
            sql.append(buffer);
            if(params!=null&&params.length>0){
                for(Object param:params){
                    args.add(param);
                }
            }
        }
        if(limit>0){
            sql.append(" limit 0,?");
            args.add(limit);
        }
        List<DataRow> list = handler.query(sql.toString(), args.toArray());
        return covert.formatList(list);
    }


    public List<T> findItemPage(T item, int start, int limit) {
        if (item == null)
            return covert.formatList(handler.query(String.format("select * from %s ", table), null, start, limit));
        if (!(item instanceof FormatModel)) return null;
        FormatModel model = (FormatModel) item;
        StringBuffer sql = new StringBuffer(String.format("select * from %s where 1=1 ", table));
        List<Object> args = new ArrayList<Object>();
        Field[] fs = item.getClass().getDeclaredFields();
        if (fs == null || fs.length == 0) return null;
        for (Field field : fs) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("serialVersionUID")) continue;
            field.setAccessible(true);
            try {

                Object value = field.get(item);
                if (value == null) continue;
                String key = model.getKey(fieldName);
                sql.append(String.format(" and %s=? ", key));
                args.add(value);

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return covert.formatList(handler.query(sql.toString(), args.toArray(), start, limit));

    }

    public Integer sum(T item, String col) {
        if (!(item instanceof FormatModel)) return null;
        FormatModel model = (FormatModel) item;
        StringBuffer sql = new StringBuffer(String.format("select sum(%s) from %s where 1=1 ", col, table));
        List<Object> args = new ArrayList<Object>();
        Field[] fs = item.getClass().getDeclaredFields();
        if (fs == null || fs.length == 0) return null;
        for (Field field : fs) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("serialVersionUID")) continue;
            field.setAccessible(true);
            try {

                Object value = field.get(item);
                if (value == null) continue;
                String key = model.getKey(fieldName);
                sql.append(String.format(" and %s=? ", key));
                args.add(value);

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return handler.queryInt(sql.toString(), args.toArray());
    }

    public Integer findItemCount(T item) {
        if (!(item instanceof FormatModel)) return null;
        FormatModel model = (FormatModel) item;
        StringBuffer sql = new StringBuffer(String.format("select count(*) from %s where 1=1 ", table));
        List<Object> args = new ArrayList<Object>();
        Field[] fs = item.getClass().getDeclaredFields();
        if (fs == null || fs.length == 0) return null;
        for (Field field : fs) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("serialVersionUID")) continue;
            field.setAccessible(true);
            try {

                Object value = field.get(item);
                if (value == null) continue;
                String key = model.getKey(fieldName);
                sql.append(String.format(" and %s=? ", key));
                args.add(value);

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return handler.queryInt(sql.toString(), args.toArray());
    }


    public T findOne(long id) {
        // TODO Auto-generated method stub
        return covert.format(this.findById(table, id));
    }


    public Message saveItem(T item) {
        // TODO Auto-generated method stub
        try {
            this.save(table, covert.parse(item));
            return new Message();
        }catch (JdbcException e){
            e.fillInStackTrace();
            return new Message(e.getMessage());
        }
    }


    public Message updateItem(T item) {
        // TODO Auto-generated method stub
        try {
            this.update(table, covert.parse(item));
            return new Message();
        }catch (JdbcException e){
            e.fillInStackTrace();
            return new Message("系统错误");
        }
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public BaseCovert<T> getCovert() {
        return covert;
    }

    public void setCovert(BaseCovert<T> covert) {
        this.covert = covert;
    }

}
