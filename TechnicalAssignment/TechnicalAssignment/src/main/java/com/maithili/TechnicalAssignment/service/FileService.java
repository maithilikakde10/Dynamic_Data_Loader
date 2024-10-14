package com.maithili.TechnicalAssignment.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.maithili.TechnicalAssignment.entity.Student;

public interface FileService {
	
	
    boolean hasCsvFormat(MultipartFile file);
    void processAndSaveData(MultipartFile file);   
                 
	void deleteStudentsFromCSV(MultipartFile file);
	void exportStudentsToCSVFile(String filePath);
	List<Student> readAllStudents();
	void updateStudentsFromCSV(MultipartFile file);
	
}
