package com.maithili.TechnicalAssignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maithili.TechnicalAssignment.entity.Student;
import com.maithili.TechnicalAssignment.response.ResponseMessage;
import com.maithili.TechnicalAssignment.service.FileService;
@RestController
@RequestMapping("/students")
public class FileController {
    
    @Autowired
    private FileService fileService;

    
    @PostMapping("/insertStudent")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        if (fileService.hasCsvFormat(file)) {
            fileService.processAndSaveData(file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Please upload a CSV file"));
    }

   
    @GetMapping("/getAll")
    public List<Student> getAllUsers() {
        return fileService.readAllStudents();
    }

    

    @PutMapping("/update")
    public ResponseEntity<String> updateStudents(@RequestParam("file") MultipartFile file) {
        if (fileService.hasCsvFormat(file)) {
            fileService.updateStudentsFromCSV(file);
            return ResponseEntity.status(HttpStatus.OK).body("Users updated successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a CSV file!");
    }

  
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudents(@RequestParam("file") MultipartFile file) {
        if (fileService.hasCsvFormat(file)) {
            fileService.deleteStudentsFromCSV(file);
            return ResponseEntity.status(HttpStatus.OK).body("Users deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a CSV file!");
    }
    
    @PostMapping("/exportTo-CSV")
    public ResponseEntity<String> exportToCSVFile(@RequestParam("filePath") String filePath) {
        try {
            fileService.exportStudentsToCSVFile(filePath); 
            return ResponseEntity.status(HttpStatus.OK).body("Data successfully written to " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error writing to file: " + e.getMessage());
        }
    }
    
}
