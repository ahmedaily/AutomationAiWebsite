import os
import csv
import zipfile

# Path to the folder containing the zip files
folder_path = os.path.join(os.path.dirname(os.path.abspath(__file__)) + '\\images_jpg\\')

# Loop through all the files in the folder
for file_name in os.listdir(folder_path):
    # Check if the file is a zip file
    if file_name.endswith('.zip'):
        # Open the zip file
        with zipfile.ZipFile(folder_path + file_name, 'r') as zip_file:
            # Get a list of all the file names in the zip file
            file_names = zip_file.namelist()

            # Loop through all the files in the zip file
            for file_name in file_names:
                # Check if the file is an image file (you can change this condition to match your file extension)
                if file_name.endswith('.jpg') or file_name.endswith('.jpeg') or file_name.endswith('.png'):
                    # Extract the file to a folder
                    zip_file.extract(file_name, folder_path + 'extracted_images/')


# Set the folder path to loop through
folder_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), "images_jpg/extracted_images")
print(folder_path)

# Set the output CSV file path and open it in write mode
output_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), "jpg_images_paths.csv")
print(output_path)

output_file = open(output_path, "w", newline='', encoding="utf-8")

# Create a CSV writer object
csv_writer = csv.writer(output_file)

# Set the list of PNG image extensions
image_extensions = (".jpg",)

# Create an empty set to store the unique image filenames
unique_images = set()

# Loop through the folder and write the paths of unique PNG images to the CSV file
for foldername, subfolders, filenames in os.walk(folder_path):
    for filename in filenames:
        if filename.lower().endswith(image_extensions):
            if filename not in unique_images:
                unique_images.add(filename)
                image_path = os.path.join(foldername, filename)
                # Replace backslashes with forward slashes in the image_path variable
                image_path = image_path.replace("\\", "/")
                csv_writer.writerow([image_path])

# Close the CSV file
output_file.close()

