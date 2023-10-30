# Introduction

This is the basic implementation of the Life Insurance Quotation Service. This is the base system that you will distribute using a 
range of distribution technologies as part of the COMP30220/41720 module laboratories.

# Running the Program

This is a maven project, complete the following steps:

1.  Download/Clone the project
2.  Use a command shell, and go to the quoco folder
3.  Type: `mvn clean compile exec:java'

# Dockerising the Program

1. Create the docker image: `docker build -it lifeco-basic:latest .`
2. Run the image: `docker run lifeco-basic:latest`

# Further Information

Please read the `QuoCo-SystemDesign.pdf` doc provided in the root folder of the project.