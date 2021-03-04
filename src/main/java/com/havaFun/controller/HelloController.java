package com.havaFun.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Controller
public class HelloController {
    @Autowired
    DataSource dataSource;
    @RequestMapping("sayHello")
    @ResponseBody
    /**
     * 这里测试获取一个数据库连接对象,并用携程把连接对象资源释放
     */
    public String sayHello(){
        try {
            Connection connection = dataSource.getConnection();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }).start();
            return String.format("sayHello:"+connection.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return String.format("init datesource failed:"+e.getMessage());
        }

    }
}
