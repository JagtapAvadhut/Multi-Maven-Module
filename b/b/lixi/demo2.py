import yaml
import re

# Function to update $ref paths to the correct format
def update_refs(obj):
    if isinstance(obj, dict):
        for key, value in obj.items():
            if key == "$ref" and isinstance(value, str):
                # Convert incorrect $ref paths to the correct format
                obj[key] = re.sub(r"#/definitions/", "#/components/schemas/", value)
            else:
                update_refs(value)
    elif isinstance(obj, list):
        for item in obj:
            update_refs(item)

# Function to process the Swagger file and correct the $ref paths
def correct_swagger_refs(swagger):
    # Update $ref paths in the paths and components/schemas
    update_refs(swagger.get("paths", {}))
    update_refs(swagger.get("components", {}))
    return swagger

def main():
    input_file = 'D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3.yaml'
    output_file = 'D:\\Avadhoot\\Maven-Module\\b\\b\\src\\main\\resources\\swagger3_corrected.yaml'

    try:
        # Read the input YAML file
        with open(input_file, 'r') as f:
            swagger_3 = yaml.safe_load(f)

        # Correct the Swagger 3.0 file's $ref paths
        corrected_swagger_3 = correct_swagger_refs(swagger_3)

        # Save the corrected YAML to the output file
        with open(output_file, 'w') as f:
            yaml.dump(corrected_swagger_3, f, sort_keys=False)

        print(f'Correction complete. Corrected Swagger 3.0 YAML saved to {output_file}')
    except FileNotFoundError as e:
        print(f"Error: {e}")
    except yaml.YAMLError as e:
        print(f"YAML Error: {e}")
    except Exception as e:
        print(f"Unexpected Error: {e}")

if __name__ == "__main__":
    main()
