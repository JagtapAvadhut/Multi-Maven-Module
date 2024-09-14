package com.a.service;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;

@Mojo(name = "convertXsdToJson")
public class XsdToJsonMojo extends AbstractMojo {

    @Parameter(property = "xsdFilePath", required = true)
    private String xsdFilePath;

    @Parameter(property = "outputPath", required = true)
    private String outputPath;

    @Parameter(property = "type", defaultValue = "yaml")
    private String type;

    private final XsdToJsonService xsdToJsonService = new XsdToJsonService();

    @Override
    public void execute() throws MojoExecutionException {
        try {
            if ("json".equalsIgnoreCase(type)) {
                // Perform JSON conversion
                xsdToJsonService.convertXsdToJson(xsdFilePath, outputPath);
                getLog().info("Successfully converted XSD to JSON at: " + outputPath);
            } else if ("yaml".equalsIgnoreCase(type)) {
                // Perform YAML conversion
                String yamlOutputPath = outputPath.replace(".json", ".yaml");
                xsdToJsonService.convertXsdToYaml(xsdFilePath, yamlOutputPath);
                getLog().info("Successfully converted XSD to YAML at: " + yamlOutputPath);
            } else {
                throw new MojoExecutionException("Invalid type specified. Only 'json' or 'yaml' are allowed.");
            }
        } catch (Exception e) {
            getLog().error("Error during XSD to " + type + " conversion", e);
            throw new MojoExecutionException("Error converting XSD to " + type, e);
        }
    }
}
