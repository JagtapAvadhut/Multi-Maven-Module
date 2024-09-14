package com.b.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Python {

    public static void main(String[] args) {
        try {
            // Command to run the Python script
//            ProcessBuilder processBuilder = new ProcessBuilder("python", "D:\\Avadhoot\\Maven-Module\\b\\b\\lixi\\demo.py");
            ProcessBuilder processBuilder = new ProcessBuilder("python", "D:\\Avadhoot\\Maven-Module\\b\\b\\lixi\\demo.py", "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3.yaml", "D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\output.yaml");

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
}
