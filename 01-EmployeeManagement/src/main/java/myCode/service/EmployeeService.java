package myCode.service;

import lombok.RequiredArgsConstructor;
import myCode.dto.EmployeeDto;
import myCode.model.Employee;
import myCode.repository.EmployeeRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final EmployeeDto employeeDto;

//    public ResponseEntity<List<EmployeeDto>> getAllEmp(){
//        return ResponseEntity.ok(mapToDto(employeeRepo.findAll()));
//    }

//    private List<EmployeeDto> mapToDto(List<Employee> all) {
//        all.builder()
//                .map(employee->employee.Mapper(employee,employeeDto))
//                .Collect(Collections.addAll())
//    }

}
