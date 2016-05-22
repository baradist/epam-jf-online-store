package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */

@Data
@AllArgsConstructor
public class SetPriceItem {
    private int id;
    private SetPrice setPrice;
    private Lot lot;
    private double increase;
    private double priceSal;
}
