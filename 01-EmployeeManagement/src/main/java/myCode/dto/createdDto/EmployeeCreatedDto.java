package myCode.dto.createdDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreatedDto {
//    private Integer id;  //Don't give this
    private String name;
    private String email;
}
