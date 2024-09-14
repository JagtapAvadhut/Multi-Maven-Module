package com.b;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenApiConversionService {
    public static void main(String[] args) throws IOException {
        OpenApiConversionService.convertOpenApi3ToSwagger2("D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3.yaml", "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\output.yaml");

    }
    private static final String OPENAPI_GENERATOR_JAR = "D:\\Avadhoot\\Maven-Module\\b\\b\\lixi\\openapi-generator-cli.jar"; // Adjust the path accordingly

    public static void convertOpenApi3ToSwagger2(String inputFilePath, String outputFilePath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java", "-jar", OPENAPI_GENERATOR_JAR,
                "convert", "-i", inputFilePath, "-g", "swagger", "-o", outputFilePath
        );
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Conversion process failed with exit code " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Conversion process was interrupted", e);
        }
    }
}
