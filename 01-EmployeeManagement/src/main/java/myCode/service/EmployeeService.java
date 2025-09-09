package myCode.service;
import myCode.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    public ResponseEntity<List<EmployeeDto>> getAllEmp();
    public ResponseEntity<EmployeeDto> getEmployeeById(Integer id);
}
