package com.havaFun;
import com.havaFun.listener.ApplicationListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
@SpringBootApplication
@ServletComponentScan(basePackageClasses = ApplicationListener.class)
public class FunApplication {
    public static void main(String[] args){
        SpringApplication.run(FunApplication.class, args);
    }
}