package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created by 1 on 06.05.2016.
 */

@Data
@AllArgsConstructor
public class CountryDto {
    @Min(0)
    private int id;
    @Size(min = 2, max = 45)
    private String name;
}
