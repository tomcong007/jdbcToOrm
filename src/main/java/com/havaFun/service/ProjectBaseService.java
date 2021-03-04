package com.havaFun.service;
import com.havaFun.except.JdbcException;
import com.havaFun.handler.DatasourceOperateHandler;
import com.havaFun.meta.DataRow;
import com.havaFun.meta.Message;
import com.havaFun.transaction.SessionParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class ProjectBaseService{
    @Autowired
    DatasourceOperateHandler handler;
    public void save(String table, DataRow data) {
        handler.insert(table, data);
    }
    public String getFieldName(String key) {
        if(key==null||key.trim().equals(""))return null;
            String[] words = key.split("_");
            if (words != null && words.length > 0) {
                StringBuffer value = new StringBuffer();
                for (String word : words) {
                    if (value.length() == 0) {
                        value.append(word.substring(0, 1).toLowerCase());
                    } else {
                        value.append(word.substring(0, 1).toUpperCase());
                    }
                    if (word.length() > 1) value.append(word.substring(1).toLowerCase());
                }
                return value.toString();
            }
            return key;
    }

    public String getKey(String fieldName) {
        if(fieldName==null||fieldName.trim().equals(""))return null;
        StringBuffer key = new StringBuffer();
        for (int i = 0; i < fieldName.length(); i++) {
            char c = fieldName.charAt(i);
            if (i == 0) {
                key.append(String.valueOf(c).toLowerCase());
            } else {
                if (Character.isUpperCase(c)) {
                    key.append("_").append(String.valueOf(c).toLowerCase());
                } else {
                    key.append(c);
                }
            }
        }
        return key.toString();
    }



    public void update(String table, DataRow data) {
        long id = data.getLong("id");
        if (id == 0) throw new RuntimeException("主键不存在,无法修改!");
        handler.update(table, data, "id", id);

    }

    public void deleteById(String table, long id) {
        handler.delete(table, "id", id);
    }

    /**
     * 通过主键查询
     * 表的主键就叫id
     *
     * @param table
     * @param id
     * @return
     */
    public DataRow findById(String table, long id) {
        String sql = "select * from " + table + " where id=?";
        return handler.queryMap(sql, new Object[]{id});

    }

    public void update(String sql,Object[] args){
        handler.update(sql, args);
    }

    public List<String> arrayToList(String[] array) {
        if (array == null) return null;
        List<String> list = new ArrayList<String>();
        for (String s : array) {
            list.add(s);
        }
        return list;
    }

    public List<String> queryStringList(String sql, Object[] args) {
        return arrayToList(handler.queryStringArray(sql, args));
    }

    public Message saveSessionParam(List<SessionParams> sessions) {
        if (sessions != null && sessions.size() != 0) {
            String[] sqlArray = new String[sessions.size()];
            List<Object[]> argList = new ArrayList<Object[]>(sessions.size());
            for (int i = 0; i < sessions.size(); i++) {
                sqlArray[i] = sessions.get(i).getSql();
                argList.add(sessions.get(i).getArgs());
            }
            try {
                handler.batchUpdateSql(sqlArray, argList);
                return new Message();
            }catch (JdbcException e){
                e.fillInStackTrace();
                return new Message("系统错误");
            }
        } else {
            return new Message("无效操作");
        }
    }

    public Map<String, String> getParams(String formatStr) {
        if (formatStr.contains("%2")) {
            try {
                formatStr = URLDecoder.decode(formatStr, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String[] arr = formatStr.split("&");
        if (arr == null || arr.length == 0) return null;
        Map<String, String> params = new HashMap<String, String>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            String fstr = arr[i];
            String[] fs = fstr.split("=");
            if (fs == null || fs.length == 0) continue;
            String val = fs.length > 1 ? fs[1] : "";
            params.put(fs[0], val);
        }
        return params;
    }
}
