package com.a;

import com.a.service.XsdToJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AApplication {
    //public class AApplication implements CommandLineRunner {
    @Autowired
    private XsdToJsonService xsdToJsonService;

    public static void main(String[] args) {
        SpringApplication.run(AApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        try {
//            xsdToJsonService.convertXsdToJson("C:\\Users\\AvadhutJagtap\\Downloads\\a\\a\\demo\\a.xsd", "C:\\Users\\AvadhutJagtap\\Downloads\\a\\a\\src\\main\\resources\\output.json");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}