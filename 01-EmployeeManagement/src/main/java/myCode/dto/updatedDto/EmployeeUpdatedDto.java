package myCode.dto.updatedDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

// Here you can only write field that are mutable and no problem for expose ,
// for current project, I don't take many fields but in production level ,This type of updatedDto is required

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdatedDto {
//  Give Only field that is updatable
    private String email;
}
