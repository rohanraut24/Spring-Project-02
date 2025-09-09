package myCode.service;
import myCode.dto.EmployeeDto;
import myCode.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    public ResponseEntity<List<EmployeeDto>> getAllEmp();
    public ResponseEntity<EmployeeDto> getEmployeeById(Integer id);
    public ResponseEntity<EmployeeDto> save(Employee employee);
}
