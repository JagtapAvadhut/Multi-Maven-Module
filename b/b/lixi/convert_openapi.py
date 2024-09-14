import sys
import os
import subprocess

def convert_openapi3_to_openapi2(input_file_path, output_file_path):
    try:
        # Ensure the paths are absolute
        input_file_path = os.path.abspath(input_file_path)
        output_file_path = os.path.abspath(output_file_path)

        # Run the subprocess to convert OpenAPI 3.0 to OpenAPI 2.0
        result = subprocess.run([
            'openapi-generator-cli', 'convert',
            '-i', input_file_path,
            '-g', 'swagger2',
            '-o', output_file_path
        ], capture_output=True, text=True)

        if result.returncode == 0:
            print(f"Successfully converted '{input_file_path}' to Swagger 2.0 and saved as '{output_file_path}'.")
        else:
            print(f"Conversion failed with exit code {result.returncode}.")
            print(f"Error output: {result.stderr}")

    except subprocess.CalledProcessError as e:
        print(f"Error occurred during conversion: {e}")
    except FileNotFoundError as e:
        print(f"Required tool not found: {e}")
    except Exception as e:
        print(f"An unexpected error occurred: {e}")

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python convert_openapi.py <input_file> <output_file>")
    else:
        input_file = sys.argv[1]
        output_file = sys.argv[2]
        convert_openapi3_to_openapi2(input_file, output_file)
