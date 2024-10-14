package com.maithili.TechnicalAssignment.service;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maithili.TechnicalAssignment.entity.Student;
import com.maithili.TechnicalAssignment.repository.StudentRepository;


@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean hasCsvFormat(MultipartFile file) {
        return "text/csv".equals(file.getContentType());
    }

    @Override
    public void processAndSaveData(MultipartFile file) {
        try {
            List<Student> students = csvToStudents(file.getInputStream());
            studentRepository.saveAll(students); // Create
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> readAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void updateStudentsFromCSV(MultipartFile file) {
        try {
            List<Student> students = csvToStudents(file.getInputStream());
            for (Student csvStudent : students) {
                Student studentFromDb = studentRepository.findById(csvStudent.getRollN()).orElse(null);
                if (studentFromDb != null) {
                	studentFromDb.setName(csvStudent.getName());
                	studentFromDb.setSchoolName(csvStudent.getSchoolName());
                    studentRepository.save(studentFromDb); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudentsFromCSV(MultipartFile file) {
        try {
            List<Student> students = csvToStudents(file.getInputStream());
            for (Student csvStudnet : students) {
                studentRepository.deleteById(csvStudnet.getRollN());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Student> csvToStudents(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            
            String firstLine = fileReader.readLine();
            if (firstLine != null && firstLine.startsWith("\uFEFF")) {
                firstLine = firstLine.substring(1); 
            }

            CSVParser csvParser = CSVParser.parse(firstLine + "\n" + fileReader.lines().reduce("", (acc, line) -> acc + "\n" + line),
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<Student> students = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                Student student = new Student(Long.parseLong(csvRecord.get("RollN")), csvRecord.get("Name"), csvRecord.get("SchoolName"));
                students.add(student);
            }

            return students;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public void exportStudentsToCSVFile(String filePath) {
        List<Student> students = studentRepository.findAll();

        try (FileWriter fileWriter = new FileWriter(filePath, true); 
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("RollN", "Name", "SchoolName"))) {

            for (Student student : students) {
                csvPrinter.printRecord(student.getRollN(), student.getName(), student.getSchoolName());
            }

            csvPrinter.flush();
            System.out.println("Data successfully written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
