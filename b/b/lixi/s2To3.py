import yaml
import copy
import re

def convert_openapi_to_swagger(openapi):
    swagger = {
        "swagger": "2.0",
        "info": openapi.get("info", {}),
        "host": openapi.get("servers", [{}])[0].get("url", "").replace("https://", "").replace("http://", ""),
        "basePath": openapi.get("servers", [{}])[0].get("url", "").split("/", 1)[1] if "/" in openapi.get("servers", [{}])[0].get("url", "") else "",
        "schemes": ["https" if "https" in openapi.get("servers", [{}])[0].get("url", "") else "http"],
        "paths": copy.deepcopy(openapi.get("paths", {})),
        "definitions": {}
    }

    # Function to process components and convert OpenAPI 3.x schemas to Swagger 2.x definitions
    def process_components(components):
        for schema_name, schema in components.get("schemas", {}).items():
            swagger["definitions"][schema_name] = schema
        return swagger

    # Convert OpenAPI components (schemas) to Swagger definitions
    swagger = process_components(openapi.get("components", {}))

    # Convert `$ref` paths to Swagger's `#/definitions/` format
    def update_refs(obj):
        if isinstance(obj, dict):
            for key, value in obj.items():
                if key == "$ref" and isinstance(value, str):
                    # Convert reference paths from components/schemas to definitions
                    obj[key] = re.sub(r"#/components/schemas/", "#/definitions/", value)
                else:
                    update_refs(value)
        elif isinstance(obj, list):
            for item in obj:
                update_refs(item)

    # Update `$ref` paths in the paths and definitions
    update_refs(swagger["paths"])
    update_refs(swagger["definitions"])

    # Convert requestBody to parameters for Swagger 2.0 format
    for path, methods in swagger["paths"].items():
        for method, details in methods.items():
            if "requestBody" in details:
                content = details["requestBody"].get("content", {})
                if "application/json" in content:
                    details["parameters"] = [{
                        "name": "body",
                        "in": "body",
                        "required": details.get("requestBody", {}).get("required", False),
                        "schema": content["application/json"].get("schema", {})
                    }]
                del details["requestBody"]

    return swagger

def main():
    input_file = 'D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3.yaml'
    output_file = 'D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\output1.yaml'

    try:
        with open(input_file, 'r') as f:
            openapi_3 = yaml.safe_load(f)

        swagger_2 = convert_openapi_to_swagger(openapi_3)

        with open(output_file, 'w') as f:
            yaml.dump(swagger_2, f, sort_keys=False)

        print(f'Conversion complete. Swagger 2.0 YAML saved to {output_file}')
    except FileNotFoundError as e:
        print(f"Error: {e}")
    except yaml.YAMLError as e:
        print(f"YAML Error: {e}")
    except Exception as e:
        print(f"Unexpected Error: {e}")

if __name__ == "__main__":
    main()
