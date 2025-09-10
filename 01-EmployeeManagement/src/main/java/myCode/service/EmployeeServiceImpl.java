package myCode.service;

import lombok.RequiredArgsConstructor;
import myCode.dto.EmployeeDto;
import myCode.dto.createdDto.EmployeeCreatedDto;
import myCode.dto.updatedDto.EmployeeUpdatedDto;
//import myCode.exception.ResourceNotFound;
import myCode.mapstruct.EmployeeMapper;
import myCode.model.Employee;
import myCode.repository.EmployeeRepo;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepo employeeRepo;
    private final ModelMapper modelMapper;
    private final EmployeeMapper employeeMapper;

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

    public EmployeeDto save(EmployeeCreatedDto employeeCreatedDto){
        Employee emp =employeeMapper.toEntity(employeeCreatedDto);
        return employeeMapper.toDto(employeeRepo.save(emp));

    }

    @Override
    public ResponseEntity<EmployeeDto> update(Integer id , EmployeeUpdatedDto employeeUpdatedDto) {
        Employee emp =employeeRepo.findById(id).orElseThrow(()-> new RuntimeException("Employee not found of id "+id));

        employeeMapper.updateFromDto(employeeUpdatedDto,emp);
        employeeRepo.save(emp);
        return ResponseEntity.ok(employeeMapper.toDto(emp));
    }

    @Override
    public ResponseEntity<EmployeeDto> putMethod(Integer id, EmployeeUpdatedDto employeeUpdatedDto) {
        Employee emp = employeeRepo.findById(id).orElseThrow(()->new RuntimeException("Employee not found biro"));
        employeeMapper.overwriteFromDto(employeeUpdatedDto,emp);
        employeeRepo.save(emp);
        return ResponseEntity.ok(employeeMapper.toDto(emp));
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {

        Map<String,Object> hm = new HashMap<>();
        if(!employeeRepo.existsById(id)){
            hm.put("Message","Bro! What are you doing at least see in database,Employee of this Id is not present");
            hm.put("Status",404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hm);
        }
        else{
            employeeRepo.deleteById(id);
            hm.put("Message","Employee is fired");
            hm.put("Status",200);
            return ResponseEntity.ok(hm);

        }
    }

//  Only for Practice
    public ResponseEntity<EmployeeDto> update(Integer id,Employee employee){
        Employee emp = employeeRepo.findById(id).orElseThrow(()->new RuntimeException("Employee not exist with id "+id));
        emp.setEmail(employee.getEmail());
        emp.setName(employee.getName());
        Employee updatedEmployee = employeeRepo.save(emp);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(modelMapper.map(updatedEmployee,EmployeeDto.class));
    }

}
