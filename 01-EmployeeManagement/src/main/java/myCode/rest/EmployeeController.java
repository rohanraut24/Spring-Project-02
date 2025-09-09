package myCode.rest;

import lombok.RequiredArgsConstructor;
import myCode.dto.EmployeeDto;
import myCode.model.Employee;
import myCode.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmp(){
        return employeeService.getAllEmp();
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getAllEmp(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> save(@RequestBody Employee emp){
        return employeeService.save(emp);
    }
}
