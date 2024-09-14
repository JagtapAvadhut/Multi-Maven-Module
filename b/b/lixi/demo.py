import yaml

def openapi3_to_swagger2(openapi3_path, swagger2_path):
    with open(openapi3_path, 'r') as f:
        openapi3 = yaml.safe_load(f)

    swagger2 = {
        'swagger': '2.0',
        'info': openapi3.get('info', {}),
        'paths': openapi3.get('paths', {}),
        'definitions': {},  # You'll need to handle OpenAPI 3.0 components manually
        'parameters': {},   # You'll need to handle OpenAPI 3.0 parameters manually
        'responses': {}     # You'll need to handle OpenAPI 3.0 responses manually
    }

    # Convert components to definitions, parameters, and responses if needed

    with open(swagger2_path, 'w') as f:
        yaml.dump(swagger2, f, default_flow_style=False)

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 3:
        print("Usage: python convert_openapi.py <input_file> <output_file>")
    else:
        input_file = sys.argv[1]
        output_file = sys.argv[2]
        openapi3_to_swagger2(input_file, output_file)
