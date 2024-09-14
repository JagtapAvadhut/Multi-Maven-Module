package com.b.controller;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class Python {

    public static void main(String[] args) {
//        SwaggerService swaggerService=new SwaggerService();
//        swaggerService.processSwaggerFile();
//        Python.runPythonScript();
//        Python.runSpringCode();
        Python.swagger3toSwagger2();

    }

    public static void swagger3toSwagger2() {
        try {
            // Command to run the Python script
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python",
                    "D:\\Avadhoot\\Maven-Module\\b\\b\\lixi\\demo.py",
                    "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3.yaml",
                    "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\output1.yaml"
            );

            // Start the process
            Process process = processBuilder.start();

            // Read the output of the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String runPythonScript() {
        StringBuilder result = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder("python", "D:\\Avadhoot\\Maven-Module\\b\\b\\lixi\\demo2.py");

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }

        return result.toString();
    }

    public static void runSpringCode() {
        String inputFilePath = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3.yaml";
        String outputFilePath = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3_corrected.yaml";

        try {
            // Load the YAML file
            Yaml yaml = new Yaml();
            Map<Object, Object> swagger = yaml.load(new FileReader(inputFilePath));

            // Correct the $ref paths
            updateRefsInMap(swagger);

            // Save the corrected YAML file
            DumperOptions options = new DumperOptions();
            options.setIndent(4);
            options.setPrettyFlow(true); // This will manage brackets nicely
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // Ensure block-style YAML formatting
            Yaml yamlWriter = new Yaml(options);

            try (FileWriter writer = new FileWriter(new File(outputFilePath))) {
                yamlWriter.dump(swagger, writer);
                System.out.println("Correction complete. Corrected Swagger YAML saved to " + outputFilePath);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void correctSwaggerRefs(Map<String, Object> swagger) {
        // Update $ref paths in the given map
        updateRefs(swagger);
    }

    private static void updateRefs(Object obj) {
        if (obj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if ("$ref".equals(entry.getKey()) && entry.getValue() instanceof String ref) {
                    // Convert incorrect $ref paths to the correct format
                    entry.setValue(ref.replace("#/definitions/", "#/components/schemas/"));
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

    // New method for processing a Map<Object, Object>
    public static Map<Object, Object> updateRefPaths(Map<Object, Object> inputMap) {
        updateRefsInMap(inputMap);
        return inputMap;
    }

    private static void updateRefsInMap(Object obj) {
        if (obj instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) obj;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                if ("$ref".equals(entry.getKey()) && entry.getValue() instanceof String) {
                    String ref = (String) entry.getValue();
                    // Correct $ref paths from Swagger 2.0 to 3.0 format
                    entry.setValue(ref.replace("#/definitions/", "#/components/schemas/"));
                } else {
                    updateRefsInMap(entry.getValue());
                }
            }
        } else if (obj instanceof Iterable) {
            for (Object item : (Iterable<?>) obj) {
                updateRefsInMap(item);
            }
        }
    }
}
