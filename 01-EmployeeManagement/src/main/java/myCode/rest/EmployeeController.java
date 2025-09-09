package myCode.rest;

import lombok.RequiredArgsConstructor;
import myCode.dto.EmployeeDto;
import myCode.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmp(){
        return employeeService.getAllEmp();
    }
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> getAllEmp(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }

}
