package com.myproject.employeepairfinder.services;

import com.myproject.employeepairfinder.models.EmployeeProject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvLoaderService {

    private static final String CSV_FILE_PATH = "src/main/resources/data.csv";
    private static final String CSV_DELIMITER = ",";
    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
            DateTimeFormatter.ofPattern("yyyyMMdd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("yyyyMMdd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd/MMM/yyyy")
    );

    public List<EmployeeProject> loadEmployeeProjectsFromCsv() {
        List<EmployeeProject> employeeProjects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(CSV_DELIMITER);

                int empID = Integer.parseInt(fields[0]);
                int projectID = Integer.parseInt(fields[1]);
                LocalDate dateFrom = parseDate(fields[2]);

                LocalDate dateTo = null;
                if (fields.length > 3 && !fields[3].isEmpty() && !fields[3].equalsIgnoreCase("null")) {
                    dateTo = parseDate(fields[3]);
                }

                EmployeeProject employeeProject = new EmployeeProject(empID, projectID, dateFrom, dateTo);
                employeeProjects.add(employeeProject);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return employeeProjects;
    }

    private LocalDate parseDate(String dateString) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("Unable to parse date: " + dateString, dateString, 0);
    }
}
