package com.a.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class XsdToJsonService {

    public void convertXsdToJson(String xsdFilePath, String outputJsonPath) throws IOException {
        // Read the content of the XSD file into a String
        Path xsdPath = Paths.get(xsdFilePath);
        String xsdContent = Files.readString(xsdPath);

        // Convert XSD (XML) to JSON
        XmlMapper xmlMapper = new XmlMapper();
        Object xmlObject = xmlMapper.readTree(xsdContent);

        // Convert to JSON using ObjectMapper
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonContent = jsonMapper.writeValueAsString(xmlObject);

        // Save JSON to the specified file
        Files.writeString(Paths.get(outputJsonPath), jsonContent);

        System.out.println("XSD file converted to JSON successfully.");
    }
}
