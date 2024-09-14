package com.b;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;

import java.io.File;
import java.io.IOException;

public class OpenAPIWriter {
    public static void saveAsYaml(OpenAPI openAPI, String outputPath) throws IOException {
        ObjectMapper mapper = Yaml.mapper();
        mapper.writeValue(new File(outputPath), openAPI);
    }

    public static void saveAsJson(OpenAPI openAPI, String outputPath) throws IOException {
        ObjectMapper mapper = Json.mapper();
        mapper.writeValue(new File(outputPath), openAPI);
    }
}


