package myCode.service;
import myCode.dto.EmployeeDto;
import myCode.dto.createdDto.EmployeeCreatedDto;
import myCode.dto.updatedDto.EmployeeUpdatedDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    public ResponseEntity<List<EmployeeDto>> getAllEmp();

    public ResponseEntity<EmployeeDto> getEmployeeById(Integer id);

    public EmployeeDto save(EmployeeCreatedDto employeeCreatedDto);

    public ResponseEntity<EmployeeDto> update(Integer id, EmployeeUpdatedDto employeeUpdatedDto);

    ResponseEntity<EmployeeDto> putMethod(Integer id, EmployeeUpdatedDto employeeUpdatedDto);

    ResponseEntity<?> deleteById(Integer id);
}