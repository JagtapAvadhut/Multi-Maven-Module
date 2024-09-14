package com.b;


import com.b.controller.SwaggerVersionConverter;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.io.*;

public class SwaggerConverter {
    public static void main(String[] args) throws IOException {
  swaggerV2ToV3Convert();
    }

    private static void swaggerV2ToV3Convert() {
        try {

            String swagger3 = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\output1.yaml";
            Yaml yaml = new Yaml();
            Map<Object, Object> swaggerYaml = yaml.load(new FileInputStream(new File(swagger3)));

            Map<Object, Object> swagger2Yaml = SwaggerVersionConverter.converterV2ToV3(swaggerYaml);

            String outPut = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\output2.yaml";
            FileWriter fileWriter = new FileWriter(new File(outPut));
            fileWriter.write(new Yaml().dump(swagger2Yaml));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
