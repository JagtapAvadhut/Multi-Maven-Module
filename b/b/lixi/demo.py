import yaml
import re
import sys

def update_refs(obj):
    """
    Recursively update $ref paths in the Swagger document to the correct format.
    """
    if isinstance(obj, dict):
        for key, value in obj.items():
            if key == "$ref" and isinstance(value, str):
                # Update incorrect $ref paths to the correct format
                obj[key] = re.sub(r"#/definitions/", "#/components/schemas/", value)
            else:
                update_refs(value)
    elif isinstance(obj, list):
        for item in obj:
            update_refs(item)

def correct_swagger_refs(swagger):
    """
    Correct $ref paths in the Swagger document.
    """
    # Update $ref paths in 'paths' and 'components/schemas'
    if "paths" in swagger:
        update_refs(swagger["paths"])
    if "components" in swagger:
        update_refs(swagger["components"])
    return swagger

def main():
    """
    Main function to read, process, and output the Swagger YAML file.
    """
    try:
        # Read YAML from standard input
        swagger_3 = yaml.safe_load(sys.stdin)

        # Correct the Swagger 3.0 file's $ref paths
        corrected_swagger_3 = correct_swagger_refs(swagger_3)

        # Output the corrected YAML to standard output
        yaml.dump(corrected_swagger_3, sys.stdout, sort_keys=False)

    except yaml.YAMLError as e:
        print(f"YAML Error: {e}", file=sys.stderr)
        sys.exit(1)
    except Exception as e:
        print(f"Unexpected Error: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == "__main__":
    main()
