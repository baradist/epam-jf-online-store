package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.Instant;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */

@Data
@AllArgsConstructor
public class SetPriceDto {
    @Min(0)
    private int id;
    private String number;
    private Instant date;
    private int manager;
}
