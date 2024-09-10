package com.a.service;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "convertXsdToJson")
public class XsdToJsonMojo extends AbstractMojo {


    @Parameter(property = "xsdFilePath", required = true)
    private String xsdFilePath;


    @Parameter(property = "jsonOutputPath", required = true)
    private String jsonOutputPath;

    private XsdToJsonService xsdToJsonService = new XsdToJsonService();

    @Override
    public void execute() throws MojoExecutionException {
        try {

            if (xsdToJsonService == null) {
                xsdToJsonService = new XsdToJsonService();
            }

            // Perform the conversion
            xsdToJsonService.convertXsdToJson(xsdFilePath, jsonOutputPath);

            // Log success message
            getLog().info("Successfully converted XSD to JSON at: " + jsonOutputPath);

        } catch (Exception e) {
            // Log error and throw MojoExecutionException
            getLog().error("Error during XSD to JSON conversion", e);
            throw new MojoExecutionException("Error converting XSD to JSON", e);
        }
    }
}
