package com.b.controller;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.logging.Logger;

@Service
public class SwaggerService {

    private static final String INPUT_FILE_PATH = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3.yaml";
    private static final String OUTPUT_FILE_PATH = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3_corrected.yaml";
    private static final Pattern REF_PATTERN = Pattern.compile("#/definitions/");


    private static final Logger LOGGER = Logger.getLogger(SwaggerService.class.getName());

    public void processSwaggerFile() {
        try {
            // Load the YAML file
            Yaml yaml = new Yaml();
            Map<String, Object> swagger = yaml.load(new FileInputStream(INPUT_FILE_PATH));

            // Correct the $ref paths
            correctSwaggerRefs(swagger);

            // Save the corrected YAML file
            DumperOptions options = new DumperOptions();
            options.setIndent(4);
            options.setPrettyFlow(true);
            Yaml yamlWriter = new Yaml(options);

            try (FileWriter writer = new FileWriter(new File(OUTPUT_FILE_PATH))) {
                yamlWriter.dump(swagger, writer);
                LOGGER.info("Correction complete. Corrected Swagger YAML saved to " + OUTPUT_FILE_PATH);
            }
        } catch (IOException e) {
            LOGGER.severe("I/O Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void correctSwaggerRefs(Map<String, Object> swagger) {
        // Update $ref paths in the 'paths' and 'components' sections
        if (swagger.containsKey("paths")) {
            updateRefs(swagger.get("paths"));
        }
        if (swagger.containsKey("components")) {
            updateRefs(swagger.get("components"));
        }
    }

    @SuppressWarnings("unchecked")
    private void updateRefs(Object obj) {
        if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if ("$ref".equals(entry.getKey()) && entry.getValue() instanceof String) {
                    String ref = (String) entry.getValue();
                    // Convert incorrect $ref paths to the correct format
                    String updatedRef = REF_PATTERN.matcher(ref).replaceAll("#/components/schemas/");
                    if (!updatedRef.equals(ref)) {
                        LOGGER.info("Updating $ref path: " + ref + " to " + updatedRef);
                    }
                    entry.setValue(updatedRef);
                } else {
                    updateRefs(entry.getValue());
                }
            }
        } else if (obj instanceof Iterable) {
            for (Object item : (Iterable<?>) obj) {
                updateRefs(item);
            }
        }
    }
}

