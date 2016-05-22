package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */

@Data
@AllArgsConstructor
public class SetPriceItemDto {
    @Min(0)
    private int id;
    private int setPrice;
    private int lot;
    private double increase;
    private double priceSal;
}
