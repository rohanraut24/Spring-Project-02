package myCode.service;

import lombok.RequiredArgsConstructor;
import myCode.dto.EmployeeDto;
import myCode.exception.ResourceNotFound;
import myCode.model.Employee;
import myCode.repository.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepo employeeRepo;
    private final ModelMapper modelMapper;

    private EmployeeDto mapToDto(Employee emp) {
        return EmployeeDto.builder()
                .id(emp.getId())
                .name(emp.getName())
                .email(emp.getEmail())
                .build();
    }
    private List<EmployeeDto> mapToDto(List<Employee> all) {
        return all.stream()
                .map(emp->EmployeeDto.builder()
                    .id(emp.getId())
                    .name(emp.getName())
                    .email(emp.getEmail())
                    .build())
                .toList();
    }
    public Employee mapToEntity(EmployeeDto employeeDto){
        return Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .email(employeeDto.getEmail())
                .build();
    }
    public ResponseEntity<List<EmployeeDto>> getAllEmp(){
        return ResponseEntity.ok(mapToDto(employeeRepo.findAll()));
    }

    public ResponseEntity<EmployeeDto> getEmployeeById(Integer id){
        Employee employee = employeeRepo.findById(id).orElseThrow(()->new RuntimeException("Employee not found Ronnie"));
        return ResponseEntity.ok(modelMapper.map(employee,EmployeeDto.class));
    }

    public ResponseEntity<EmployeeDto> save(Employee employee){
        Employee emp = employeeRepo.save(employee);
//      return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(emp));   //This is also valid
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(emp,EmployeeDto.class));
    }

    public ResponseEntity<EmployeeDto> update(Integer id,Employee employee){
        Employee emp = employeeRepo.findById(id).orElseThrow(()->new RuntimeException("Employee not exist with id "+id));
        emp.setEmail(employee.getEmail());
        emp.setName(employee.getName());
        Employee updatedEmployee = employeeRepo.save(emp);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(modelMapper.map(updatedEmployee,EmployeeDto.class));
    }

}
