package myCode.rest;

import lombok.RequiredArgsConstructor;
import myCode.dto.EmployeeDto;
import myCode.dto.createdDto.EmployeeCreatedDto;
import myCode.dto.updatedDto.EmployeeUpdatedDto;
//import myCode.exception.ResourceNotFound;
import myCode.mapstruct.EmployeeMapper;
import myCode.model.Employee;
import myCode.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import myCode.dto.EmployeeDto;

import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getAllEmp(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDto save(@RequestBody EmployeeCreatedDto employeeCreatedDto){
        return employeeService.save(employeeCreatedDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeUpdatedDto employeeUpdatedDto){
        return employeeService.update(id,employeeUpdatedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> putMethod(@PathVariable Integer id, @RequestBody EmployeeUpdatedDto employeeUpdatedDto){
        return employeeService.putMethod(id,employeeUpdatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmp(@PathVariable Integer id){
        return employeeService.deleteById(id);
    }

}
