package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// @SpringBootTest sẽ khởi động toàn bộ ứng dụng Spring (bao gồm kết nối DB thật)
// để kiểm tra logic có hoạt động đúng khi chạm vào Postgres hay không.
@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void saveAndFindEmployee_shouldWorkCorrectly() {
        // 1. Tạo 1 nhân viên mới
        Employee employee = new Employee();
        employee.setName("Nguyen Van A");
        employee.setEmail("a.nguyen@example.com");

        // 2. Lưu vào database (Postgres thật, do GitLab CI dựng lên)
        Employee saved = employeeRepository.save(employee);

        // 3. Kiểm tra: sau khi lưu phải có id, và tìm lại được đúng dữ liệu
        assertNotNull(saved.getId());

        Employee found = employeeRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Nguyen Van A", found.getName());
        assertEquals("a.nguyen@example.com", found.getEmail());
    }
}
