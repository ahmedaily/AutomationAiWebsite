import os
import csv

# Set the folder path to loop through
folder_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), "images")

# Set the output CSV file path and open it in write mode
output_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), "images_paths.csv")

output_file = open(output_path, "w", newline='', encoding="utf-8")

# Create a CSV writer object
csv_writer = csv.writer(output_file)

# Set the list of PNG image extensions
image_extensions = (".png",)

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
