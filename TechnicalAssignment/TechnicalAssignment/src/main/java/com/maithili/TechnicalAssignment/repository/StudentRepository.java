package com.maithili.TechnicalAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maithili.TechnicalAssignment.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
