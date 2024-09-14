package com.b;

import com.a.service.XsdToJsonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BApplication {
String string="D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger.yaml";

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BApplication.class, args);
//        XsdToJsonService xsdToJsonService = new XsdToJsonService();
//        xsdToJsonService.convertXsdToJson("C:\\Users\\AvadhutJagtap\\Downloads\\b\\b\\lixi\\index.xsd", "C:\\Users\\AvadhutJagtap\\Downloads\\b\\b\\src\\main\\resources\\output.json");
    }

}
