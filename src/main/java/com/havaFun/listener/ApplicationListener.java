package com.havaFun.listener;
import com.havaFun.scan.PojoScannerReader;
import com.havaFun.scan.PojoScannerWriter;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class ApplicationListener implements ServletContextListener {
    @Autowired
    PojoScannerWriter writer;
    @Autowired
    PojoScannerReader reader;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //反正写着玩,搞个自动扫描创建实体类
         writer.scanAllTable();
         //也可以写自定义注解，扫描实体类的注解，来自动建表,待续
        reader.readAnotation();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}