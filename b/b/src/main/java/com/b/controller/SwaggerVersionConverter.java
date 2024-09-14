package com.b.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import java.util.Map;

public class SwaggerVersionConverter {
    public static Map<Object, Object> converterV2ToV3(Map<Object, Object> swaggerYaml) throws JsonProcessingException {
        OpenAPI openAPI = new OpenAPIParser().readContents(Yaml.mapper().writeValueAsString(swaggerYaml), null, null).getOpenAPI();
        return openAPI != null ? Yaml.mapper().readValue(Yaml.mapper().writeValueAsString(openAPI), Map.class) : null;
    }

}
