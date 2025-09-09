package myCode.rest;

import lombok.RequiredArgsConstructor;
import myCode.dto.EmployeeDto;
import myCode.dto.createdDto.EmployeeCreatedDto;
import myCode.mapstruct.EmployeeMapper;
import myCode.model.Employee;
import myCode.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import myCode.dto.EmployeeDto;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmp(){
        return employeeService.getAllEmp();
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getAllEmp(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDto save(@RequestBody EmployeeCreatedDto employeeCreatedDto){
        return employeeService.save(employeeCreatedDto);
    }
}
