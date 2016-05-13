package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by 1 on 06.05.2016.
 */

@Data
@AllArgsConstructor
public class ProducerDto {
    @Min(0)
    private int id;
    @Size(min = 2, max = 255)
    private String name;
    @Min(0)
    @NotNull
    private int country;
}
