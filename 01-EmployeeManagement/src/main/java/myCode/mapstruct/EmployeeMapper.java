package myCode.mapstruct;


import myCode.dto.EmployeeDto;
import myCode.dto.createdDto.EmployeeCreatedDto;
import myCode.dto.updatedDto.EmployeeUpdatedDto;
import myCode.model.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    Employee toEntity(EmployeeCreatedDto employeeCreatedDto);

    // PUT → overwrite everything (including nulls)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void overwriteFromDto(EmployeeUpdatedDto dto, @MappingTarget Employee entity);

    // PATCH → ignore nulls (partial update)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(EmployeeUpdatedDto empDto , @MappingTarget Employee emp);
}
