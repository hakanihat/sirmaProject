# sirma-project

I have divided the project into 4 packages: controllers, dtos, models, and services.
I don't have a 'repositories' package because I am not using a database to solve the problem.
To solve the problem, I assessed that I don't need separate 'Employee' and 'Project' models 
but rather an intermediate model (similar to an intermediate table in a database) called 'EmployeeProject' 
to avoid unnecessarily complicating the solution.
In the 'dtos' package, I have a model called 'ProjectDurationDTO,' which I use for receiving responses.

SERVICES
CsvLoaderService:
The CsvLoaderService is responsible for loading employee project data from a CSV file.
The CSV file contains information about employees, projects, and their collaboration dates.
The service reads the CSV file, parses the data, and creates a list of EmployeeProject objects.
I have a static list of DataFormatter to enable the program to support various date formats.

EmployeeProjectService and CsvManipulationService:
The idea of these two services is to have CRUD operations in the project,
even though I haven't implemented it for Employees in my case, but for the intermediate model.


EmployeePairService:
The EmployeePairService is where the class where is the algorithm to find the longest collaborations between pairs of employees on the same project.

Algorithm Overview
Data Loading: Load employee project data from a CSV file using CsvLoaderService.
Collaboration Mapping: Iterate over pairs of employee projects to identify collaborations on the same project.
For each pair of employee projects with the same project ID:
Calculate the overlap days between the date ranges of the two projects.
If there is an overlap, create a collaboration key and a ProjectDurationDTO object representing the collaboration.
Store collaborations in a map using the collaboration key.
Longest Collaboration Identification: Identify the longest collaborations by summing the duration days for each collaboration.
Find the collaboration with the maximum total duration.
Return a list of ProjectDurationDTO objects representing the longest collaborations.

Methods
findLongestCollaborations: The main method orchestrating the collaboration finding process.
calculateOverlap: Calculate the overlap days between two date ranges.
generateCollaborationKey: Generate a unique key for identifying collaborations between two employees.

Key Concepts
Collaboration Map: A map is used to group collaborations by a unique key, which represents collaborations between two employees.
Overlap Calculation: The algorithm determines the overlap days between two date ranges.
Longest Collaboration: The algorithm identifies and returns the longest collaborations based on total duration.

