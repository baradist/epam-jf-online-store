package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created by Oleg Grigorjev on 14.05.2016.
 */

@Data
@AllArgsConstructor
public class StoreDto {
    @Min(0)
    private int id;
    @Size(max=255)
    private String name;
    @Size(max=255)
    private String address;
}
