package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Oleg Grigorjev on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class PriceItemDto {
//    @Min(0)
//    private int id;
    private int good;
    private double quantity;
    private double quantityOrdered;
    private double price;
}
