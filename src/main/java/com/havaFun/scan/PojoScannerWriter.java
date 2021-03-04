package com.havaFun.scan;
import com.havaFun.handler.DatasourceOperateHandler;
import com.havaFun.meta.DataRow;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import javax.validation.constraints.NotNull;
import java.util.*;
@Component
public class PojoScannerWriter {
    private static Logger logger = Logger.getLogger(PojoScannerWriter.class);
    @Autowired
    DatasourceOperateHandler handler;
    @Value("${scan.workspace.location}")
    String workspaceLocation;
    @Value("${scan.ftl.location}")
    String ftlLocation;
    public  Template getTemplate(String name) {
        try {
            // 通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            // 设定去哪里读取相应的ftl模板文件
            cfg.setDirectoryForTemplateLoading(new File(ftlLocation));
            // 在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(name);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 生成pojo层
     * @param tpl(生成的java文件名,必须为.java,属于本地Workspace非web容器里com.service里的相对路径)
     */
    public  void createPojo(PojoTpl tpl){
        String fileName=String.format("%s\\model\\%s.java",workspaceLocation,tpl.getName());
        File f = new File(fileName);
        if(f.exists())f.delete();
        FileWriter out = null;
        Map<String,Object> root = new HashMap<String,Object>(2);
        root.put("pojoName", tpl.getName());
        root.put("propertyList",tpl.getPropertyList());
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            validateParentPath(fileName);
            out = new FileWriter(new File(fileName));
            Template temp = getTemplate("pojo.ftl");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 生成数据库结果集hashmap到pojo层的转化映射covert层
     * @param tpl(生成的java文件名,必须为.java,属于本地Workspace非web容器里com.service里的相对路径)
     */
    public  void createCovert(PojoTpl tpl){
        String fileName=String.format("%s\\covert\\%sCovert.java",workspaceLocation,tpl.getName());
        File f = new File(fileName);
        if(f.exists())f.delete();
        FileWriter out = null;
        Map<String,Object> root = new HashMap<String,Object>(2);
        root.put("pojoName", tpl.getName());
        root.put("propertyList",tpl.getPropertyList());
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            validateParentPath(fileName);
            out = new FileWriter(new File(fileName));
            Template temp = getTemplate("covert.ftl");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public  void createService(String table,PojoTpl tpl) {
        String fileName=String.format("%s\\service\\%sService.java",workspaceLocation,tpl.getName());
        File f = new File(fileName);
        if(f.exists())f.delete();
        FileWriter out = null;
        Map<String,Object> root = new HashMap<String,Object>(4);
        root.put("pojoName", tpl.getName());
        root.put("tableName", table);
        root.put("propertyList",tpl.getPropertyList());
        root.put("ali",0);
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            validateParentPath(fileName);
            out = new FileWriter(new File(fileName));
            Template temp = getTemplate("service.ftl");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createAliService(String table,PojoTpl tpl) {
        String fileName=String.format("%s\\com.haveFun.service\\%sService.java",workspaceLocation,tpl.getName());
        File f = new File(fileName);
        if(f.exists())f.delete();
        FileWriter out = null;
        Map<String,Object> root = new HashMap<String,Object>(4);
        root.put("pojoName", tpl.getName());
        root.put("tableName", table);
        root.put("propertyList",tpl.getPropertyList());
        root.put("ali",1);
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            validateParentPath(fileName);
            out = new FileWriter(new File(fileName));
            Template temp = getTemplate("service.ftl");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据表名和字段映射来获取模版变量的值
     * @param tableName(数据库的表名)
     * @param name 为实体类的名称,比如 User.java name就是 User
     * @return 模板
     */
    public  PojoTpl formatTplFromTable(String tableName,String name){
        PojoTpl tpl = new PojoTpl();
        tpl.setName(name);
        tpl.setTable(tableName);
        tpl.setPropertyList(formatListFromTableName(tableName));
        return tpl;
    }

    /**
     * 如果文件的父路径不存在则自动创建文件夹
     * @param fileName
     */
    private  void validateParentPath(String fileName){
        // TODO Auto-generated method stub
        if(fileName==null||fileName.indexOf("/")==-1)return;
        String parentPath = fileName.substring(0, fileName.lastIndexOf("/"));
        File f = new File(parentPath);
        if(!f.exists()) f.mkdir();
    }


    public  List<Property> formatListFromTableName(String tableName){
        String sql = "select column_name,data_type  from information_schema.columns where TABLE_NAME =?";
        List<DataRow> list = handler.query(sql, new Object[]{tableName.toUpperCase()});
        List<Property> properList = new ArrayList<Property>();
        if(list==null||list.size()==0) {
            throw new RuntimeException(String.format("%s表名不存在!",tableName));
        }
        Set<String> keys = new HashSet<String>();
        for(DataRow data:list){
            String name = data.getString("column_name");
            String type = data.getString("data_type");
            Property property = new Property();
            property.setKey(name);
            if(keys.contains(name))continue;
            if(type.equals("varchar")){
                property.setPrefix("String");
            }else if(type.equals("bigint")){
                property.setPrefix("Long");
            }else if(type.equals("int")||type.equals("tinyint")){
                property.setPrefix("Integer");
            }else if(type.equals("datetime")){
                property.setPrefix("FunDate");
            }else if(type.equals("longtext")){
                property.setPrefix("String");
            }else{
                property.setPrefix("Double");
            }
            properList.add(property);
            keys.add(name);
        }
        return properList;
    }


    public  List<Property> formatListFromTableNameWithCols(String table,List<String> cols){
        String sql = "select column_name,data_type  from information_schema.columns where TABLE_NAME =?";
        List<DataRow> list = handler.query(sql, new Object[]{table.toUpperCase()});
        List<Property> properList = new ArrayList<Property>();
        if(list==null||list.size()==0) {
            throw new RuntimeException(String.format("%s表名不存在!",table));
        }
        Set<String> keys = new HashSet<String>();
        for(DataRow data:list){
            String name = data.getString("column_name");
            if(!cols.contains(name))continue;
            String type = data.getString("data_type");
            Property property = new Property();
            property.setKey(name);
            if(keys.contains(name))continue;
            if(type.equals("varchar")){
                property.setPrefix("String");
            }else if(type.equals("bigint")){
                property.setPrefix("Long");
            }else if(type.equals("int")||type.equals("tinyint")){
                property.setPrefix("Integer");
            }else if(type.equals("datetime")){
                property.setPrefix("ErpDate");
            }else{
                property.setPrefix("Double");
            }
            properList.add(property);
            keys.add(name);
        }
        System.out.println(keys);
        return properList;
    }

    public   void createTableByProps(String table,List<Property> list){
        int c  = handler.queryInt("select count(*)  from information_schema.columns where TABLE_NAME =?",new Object[]{table});
        if(c>0)throw new RuntimeException(String.format("表%s已经存在",table));
        StringBuffer sql = new StringBuffer("CREATE TABLE ");
        sql.append(table);
        sql.append(" (");
        for(int i=0;i<list.size();i++){
            if(i!=0)sql.append(",");
            Property property = list.get(i);
            String prefix = property.getPrefix();
            String key = property.getKey();
            sql.append(key);
            if(prefix.equals("String")){
                sql.append(" varchar(20)");
            }else if(prefix.equals("long")){
                sql.append(" bigint(20)");
            }else if(prefix.equals("int")){
                sql.append(" int(11)");
            }else if(prefix.equals("date")){
                sql.append(" datetime ");
            }
        }
        sql.append(")");
        System.out.println(sql.toString());
        handler.update(sql.toString());
    }

    public   void createTableByProps(String table,JSONObject root){
        createTableByProps(table,parsePropertyFromJson(root));
    }
    public  List<Property> parsePropertyFromJson(JSONObject root){
        if(root==null||root.size()==0)return null;
        List<Property> list = new ArrayList<Property>();
        for(Iterator<String> it = root.keySet().iterator();it.hasNext();){
            String key = it.next();
            Object value = root.get(key);
            Property property = new Property();
            property.setValue(key);
            if(value  instanceof String){
                property.setPrefix("String");
            }else if(value instanceof Integer){
                int v = (Integer) value;
                if(v>100000){
                    property.setPrefix("long");
                }else{
                    property.setPrefix("int");
                }
            }else if(value instanceof Long){
                property.setPrefix("long");
            }else if(value instanceof Double){
                property.setPrefix("double");
            }else if(value instanceof Float){
                property.setPrefix("float");
            }else if(value==null|| "".equals(value.toString().trim())){
                property.setPrefix("String");
            }else{
                //暂时不搞其他复杂的类型
                continue;
            }
            if(property.getPrefix()!=null)list.add(property);
        }
        return list;
    }



    public   void createPojo(String name ,JSONObject root){
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(parsePropertyFromJson(root));
        createPojo(pojoTpl);
    }
    public   void createCovert(String name ,JSONObject root){
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(parsePropertyFromJson(root));
        createCovert(pojoTpl);
    }
    public   void createService(String table,String name ,JSONObject root){
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(parsePropertyFromJson(root));
        createService(table,pojoTpl);
    }

    public  void createORM(String table,String name,List<String> cols){
        if(StringUtils.isEmpty(name)||name.length()<2)return;
        name = String.format("%s%s",name.substring(0, 1).toUpperCase(), name.substring(1));
        List<Property> props =formatListFromTableNameWithCols(table,cols);
        System.out.println(props.size());
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(props);
        createPojo(pojoTpl);
        createCovert(pojoTpl);
        createService(table,pojoTpl);
    }
    public  void createORM(String table,String name){
        if(StringUtils.isEmpty(name)||name.length()<2)return;
        name = String.format("%s%s",name.substring(0, 1).toUpperCase(), name.substring(1));
        List<Property> props =formatListFromTableName(table);
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(props);
        createPojo(pojoTpl);
        createCovert(pojoTpl);
        createService(table,pojoTpl);
    }

    public void createORM(@NotNull String table){
        String pojoName = null;
        if(!table.contains("_")){
            pojoName = formatUpper(table);
        }else{
            String[] arr = table.split("_");
            StringBuffer buffer = new StringBuffer();
            for(String key:arr){
                buffer.append(formatUpper(key));
            }
            pojoName = buffer.toString();
        }
        logger.warn(String.format("正在自动创建表[%s]的实体类[%s.java]",table,pojoName));
        createORM(table,pojoName);
    }

    private String formatUpper(@NotNull String name){
        return String.format("%s%s",name.substring(0, 1).toUpperCase(), name.substring(1));
    }

    /**
     * 只扫描5张表自动创建实体类试试
     */
    public void scanAllTable(){
           String sql  ="SELECT DISTINCT TABLE_NAME from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='meituan' limit 0,5";
           String[] tables = handler.queryStringArray(sql);
           if(tables!=null){
               for(String table:tables)createORM(table);
           }
        createORM("t_user");
    }


}
