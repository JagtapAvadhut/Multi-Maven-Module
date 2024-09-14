package com.b.controller;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.Map;

public class PythonIntegration {

    public static void main(String[] args) throws IOException {
        Yaml yaml = new Yaml();
        String inputFilePath = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3.yaml";
        String outputFilePath = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3_corrected.yaml";

        Map<Object, Object> inputMap = loadYamlFile(inputFilePath);
        Map<Object, Object> correctedMap = processYamlWithPython(inputMap);

        if (correctedMap != null) {
            saveYamlFile(correctedMap, outputFilePath);
        }
    }

    // Load YAML file into a Map<Object, Object>
    private static Map<Object, Object> loadYamlFile(String filePath) {
        Yaml yaml = new Yaml();
        try (FileReader reader = new FileReader(filePath)) {
            return yaml.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Process YAML data using the Python script
    private static Map<Object, Object> processYamlWithPython(Map<Object, Object> yamlMap) {
        StringBuilder result = new StringBuilder();
        Yaml yaml = new Yaml();

        try {
            Process process = startPythonProcess();

            writeYamlToProcess(yamlMap, process);

            readProcessOutput(result, process);

            process.waitFor();

            return yaml.load(new StringReader(result.toString()));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Start the Python process
    private static Process startPythonProcess() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "python",
                "D:\\Avadhoot\\Maven-Module\\b\\b\\lixi\\demo.py"
        );
        return processBuilder.start();
    }

    // Write YAML data to the Python script via stdin
    private static void writeYamlToProcess(Map<Object, Object> yamlMap, Process process) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream())) {
            Yaml yaml = new Yaml();
            yaml.dump(yamlMap, writer);
            writer.flush();
        }
    }

    // Read the output (corrected YAML) from the Python script
    private static void readProcessOutput(StringBuilder result, Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
    }

    // Save the corrected YAML data to a file
    private static void saveYamlFile(Map<Object, Object> yamlMap, String filePath) {
        Yaml yaml = new Yaml();
        try (FileWriter writer = new FileWriter(filePath)) {
            yaml.dump(yamlMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
