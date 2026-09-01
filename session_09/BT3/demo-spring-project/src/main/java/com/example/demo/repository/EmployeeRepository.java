package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// Chỉ cần extends JpaRepository là có sẵn save(), findById(), findAll(), delete()...
// không cần viết thêm code gì cả.
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
