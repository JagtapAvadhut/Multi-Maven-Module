//package com.b;
//
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//@Service
//public class OpenApiConversionService {
//
//    private static final String OPENAPI_GENERATOR_JAR = "D:\\Avadhoot\\Maven-Module\\b\\b\\lixi\\openapi-generator-cli.jar"; // Update this path as needed
//
//    public static void main(String[] args) throws IOException {
//        String inputFilePath = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3.yaml";
//        String outputFilePath = "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\output.yaml";
//        convertOpenApi3ToSwagger2(inputFilePath, outputFilePath);
//    }
//
//    public static void convertOpenApi3ToSwagger2(String inputFilePath, String outputFilePath) throws IOException {
//        // Ensure that the file paths are valid
//        File inputFile = new File(inputFilePath);
//        File outputFile = new File(outputFilePath);
//
//        if (!inputFile.exists()) {
//            throw new IOException("Input file does not exist: " + inputFilePath);
//        }
//
//        // Build the process to run the OpenAPI Generator
//        ProcessBuilder processBuilder = new ProcessBuilder(
//                "java", "-jar", OPENAPI_GENERATOR_JAR,
//                "generate", "-i", inputFilePath,
//                "-g", "swagger", "-o", outputFile.getParent()
//        );
//
//        processBuilder.redirectErrorStream(true);
//
//        Process process = processBuilder.start();
//
//        // Capture the output of the process
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//        }
//
//        // Wait for the process to finish and check the exit code
//        try {
//            int exitCode = process.waitFor();
//            if (exitCode != 0) {
//                throw new IOException("Conversion process failed with exit code " + exitCode);
//            } else {
//                System.out.println("Conversion successful! Swagger 2.0 file is located at: " + outputFilePath);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new IOException("Conversion process was interrupted", e);
//        }
//    }
//}
