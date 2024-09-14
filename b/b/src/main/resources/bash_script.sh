#!/bin/bash

# Define the virtual environment directory and log file paths
venv_dir="$(dirname "$0")/env"
log_file="$(dirname "$0")/script_log.txt"

# Function to log messages with timestamps
log() {
    echo "$(date +"%Y-%m-%d %H:%M:%S"): $1" >> "$log_file"
}

# Check if the virtual environment exists; create it if necessary
if [ ! -d "$venv_dir" ]; then
    log "Virtual environment not found. Creating virtual environment..."
    python3 -m venv "$venv_dir"
    if [ $? -ne 0 ]; then
        log "Error: Failed to create virtual environment."
        exit 1
    fi
fi

# Activate the virtual environment
source venv/Scripts/activate

if [ $? -ne 0 ]; then
    log "Error: Failed to activate virtual environment."
    exit 1
fi

# Upgrade pip to the latest version
log "Upgrading pip..."
python -m pip install --upgrade pip
if [ $? -ne 0 ]; then
    log "Error: Failed to upgrade pip."
    exit 1
fi

# Install required packages from requirements.txt
requirements_file="$(dirname "$0")/requirements.txt"
if [ -f "$requirements_file" ]; then
    log "Installing dependencies from requirements.txt..."
    pip install -r "$requirements_file"
    if [ $? -ne 0 ]; then
        log "Error: Failed to install dependencies."
        exit 1
    fi
else
    log "Warning: No requirements.txt found."
fi

# Process input and output file arguments
input_file=$1
output_file=$2

# Check if input and output files are provided
if [ -z "$input_file" ] || [ -z "$output_file" ]; then
    log "Error: Missing input or output file arguments."
    echo "Usage: $0 <input_file> <output_file>"
    exit 1
fi

# Log the input and output file paths
log "Processing OpenAPI conversion: Input File: $input_file, Output File: $output_file"

# Call the Python script for OpenAPI conversion
python "$(dirname "$0")/convert_openapi.py" "$input_file" "$output_file"
if [ $? -ne 0 ]; then
    log "Error: OpenAPI conversion failed."
    exit 1
fi

# Log the successful completion of the conversion
log "OpenAPI conversion completed successfully."

# Exit the script
exit 0
