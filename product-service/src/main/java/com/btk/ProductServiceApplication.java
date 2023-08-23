package com.btk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.File;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ProductServiceApplication {
    public static void main(String[] args) {
        String logFilePath = "D:\\Btk-Ecommerce\\product-service\\logs\\app.log";

        File logFile = new File(logFilePath);
        if (logFile.exists()) {
            logFile.delete();
        }else {
            System.out.println("Log Dosyası Silinemedi.");
        }

        SpringApplication.run(ProductServiceApplication.class);
    }
}