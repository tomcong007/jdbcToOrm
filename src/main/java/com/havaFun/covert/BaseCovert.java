package com.havaFun.covert;
import com.havaFun.meta.DataRow;
import com.havaFun.model.FunDate;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public abstract  class BaseCovert<T> implements Covert<T> {
	public  List<T> formatList(List<DataRow> list){
		if(list==null||list.size()==0)return null;
		List<T> dataList = new ArrayList<T>();
		for(DataRow form:list){
			dataList.add(format(form));
		}
		return dataList;
	}
	public void fromatIntegerForm(DataRow form, String key, Integer value) {
		// TODO Auto-generated method stub
		if(value!=null)form.set(key, value);
	}

	public Long parseLongItem(DataRow form,String key){
		if(form.containsKey(key))return form.getLong(key);
		return null;
	}
	public int parseIntItem(DataRow form,String key){
		return form.getInt(key);
	}
	public Integer parseIntegerItem(DataRow form,String key){
		if(form.containsKey(key))return form.getInt(key);
		return null;
	}

	public double parseDoubleItem(DataRow form,String key){
		return form.getDouble(key);
	}
	public String parseStringItem(DataRow form,String key){
		return form.getString(key);
	}

	public Date parseDateItem(DataRow form,String key){
		if(!form.containsKey(key))return null;
		Timestamp t = (Timestamp) form.get(key);
		if(t==null)return null;
		return new Date(t.getTime());
	}
	public FunDate parseErpDateItem(DataRow form, String key){
		if(!form.containsKey(key))return null;
		Timestamp t = (Timestamp) form.get(key);
		if(t==null)return null;
		return new FunDate(t.getTime());
	}
	public void formatDateForm(DataRow form,String key,Date value){
		if(value!=null){
			form.set(key, value);
		}
	}


	public boolean isNotEmpty(DataRow form,String key){
		String value = form.getString(key);
		return value!=null&&!value.trim().equals("");
	}

	public boolean isNotEmpty(String key){
		return key!=null&&!key.trim().equals("");
	}

	public void formatLongForm(DataRow form,String key,Long value){
		if(value!=null)form.set(key,value);
	}

	public void formatObjectForm(DataRow form,String key,Object value){
		if(value!=null)form.set(key,value);
	}

	public void formatDoubleForm(DataRow form,String key,Double value){
		if(value!=null)form.set(key,value);
	}

	public void formatStringForm(DataRow form,String key,String value){
		if(value!=null&&!value.trim().equals("")&&key!=null&&!key.trim().equals(""))
		form.set(key,value);
	}
	public void  formatIntForm(DataRow form,String key,Integer value){
		if(value!=null&&value!=0)form.set(key,value);
	}
	public void  formatIntegerForm(DataRow form,String key,Integer value){
		if(value!=null)form.set(key,value);
	}

	public void formatTimestampForm(DataRow form,String key,String value,String patten){
		if(value!=null&&!value.trim().equals("")&&key!=null&&!key.trim().equals(""))form.set(key, formatToDate(value, patten));
	}

	private Date formatToDate(String dateStr, String patten) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patten);

		try {
			if (dateStr!=null&&!dateStr.trim().equals("")) {
				return dateFormat.parse(dateStr);
			}
		} catch (ParseException e) {
			e.fillInStackTrace();
		}

		return null;
	}


	public void formatTimestampForm(DataRow form,String key,String value){
		if(value!=null&&!value.trim().equals("")&&key!=null&&!key.trim().equals(""))form.set(key, formatToDate(value, "yyyy-MM-dd HH:mm"));
	}

	public DataRow formatMap(DataRow form,Map<String,String> prox){
		if(form==null||form.size()==0||prox==null||prox.size()==0)return null;
		DataRow result = new DataRow();
		for(Iterator<String> it =prox.keySet().iterator();it.hasNext();){
			String key = it.next().trim();
			if(form.containsKey(key)){
				result.set(prox.get(key), form.get(key));
			}
		}
		return result;
	}
	public List<DataRow> formatMapList(List<DataRow> list,Map<String,String> prox){
		if(list==null||list.size()==0||prox==null||prox.size()==0)return null;
		List<DataRow> dataList = new ArrayList<DataRow>();
		for(DataRow form:list){
			dataList.add(formatMap(form,prox));
		}
		return dataList;
	}

	public void print(T item){
		Field[] fs =item.getClass().getDeclaredFields();
		if(fs.length>0){
			for(Field f:fs){
				String type = f.getType().getSimpleName();
				if(!type.equals("List")&&!type.equals("String[]")){
					if(!"serialVersionUID".equals(f.getName()))System.err.println("proxy.put(\""+f.getName()+"\",\""+f.getName()+"\");");
				}

			}
		}
	}

}
