package myCode;

import myCode.dto.EmployeeDto;
import myCode.model.Employee;
import myCode.repository.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EmployeeManagementTests {

    @Autowired
    public EmployeeRepo employeeRepo;

	@Test
	void contextLoads() {
        List<Employee> list = employeeRepo.findAll();
        System.out.println(list);

//        Employee emp = new Employee("Ronnie","email@g.com");
//        System.out.println((employeeRepo.save(emp)));
    }

}
