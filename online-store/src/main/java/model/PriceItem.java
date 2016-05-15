package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 1 on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class PriceItem {
//    private int id;
    private Good good;
    private double quantity;
    private double quantityOrdered;
    private double price;
}
