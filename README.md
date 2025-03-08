# Sirma Project

## Project Structure
This project is divided into four main packages:
- **controllers** – Handles incoming requests and responses.
- **dtos** – Contains Data Transfer Objects used for structured responses.
- **models** – Defines the core data structures.
- **services** – Implements business logic and processing.

Notably, there is no **repositories** package since a database is not used. Instead, the solution is designed with an intermediate model called **EmployeeProject**, eliminating the need for separate `Employee` and `Project` models, thus keeping the implementation simple and efficient. The `dtos` package includes `ProjectDurationDTO`, which is used for returning structured responses.

---
## Services
### **CsvLoaderService**
Responsible for loading employee project data from a CSV file. The CSV file contains details about employees, projects, and their collaboration periods. This service:
- Reads and parses the CSV file.
- Creates a list of `EmployeeProject` objects.
- Supports multiple date formats using a static `DataFormatter` list.

### **EmployeeProjectService & CsvManipulationService**
These services provide CRUD operations for managing employee project data. Though the implementation does not include CRUD for employees themselves, it is applied to the intermediate `EmployeeProject` model.

### **EmployeePairService**
This service contains the core algorithm for identifying the **longest collaboration** between pairs of employees who worked on the same project.

---
## Algorithm Overview
### **1. Data Loading**
- Load employee project data from a CSV file using `CsvLoaderService`.

### **2. Collaboration Mapping**
- Iterate over pairs of `EmployeeProject` records to find collaborations on the same project.
- For each pair with the same project ID:
  - Calculate the **overlap days** between their date ranges.
  - If an overlap exists:
    - Create a collaboration key.
    - Generate a `ProjectDurationDTO` object representing the collaboration.
  - Store collaborations in a map using the collaboration key.

### **3. Longest Collaboration Identification**
- Identify the longest collaborations by summing the total duration days for each collaboration.
- Find the collaboration with the maximum total duration.
- Return a list of `ProjectDurationDTO` objects representing the longest collaborations.

---
## Key Methods
### **findLongestCollaborations**
- The main method that orchestrates the process of finding the longest collaboration.

### **calculateOverlap**
- Computes the number of overlapping days between two date ranges.

### **generateCollaborationKey**
- Generates a unique key to identify collaborations between two employees.

---
## Core Concepts
### **Collaboration Map**
A map is used to group collaborations by a unique key representing employee pairs.

### **Overlap Calculation**
The algorithm determines the number of days two employees worked together on the same project.

### **Longest Collaboration Identification**
The algorithm finds and returns the longest collaborations based on total duration.

---
## Summary
This project efficiently processes CSV data to identify long-term employee collaborations on shared projects. By leveraging a structured approach, it avoids unnecessary complexity while delivering an optimal solution for tracking work relationships over time.

