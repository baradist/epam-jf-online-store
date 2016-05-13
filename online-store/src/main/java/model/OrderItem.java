package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */

@Data
@AllArgsConstructor
public class OrderItem {
    private int id;
    private Order order;
    private Good good;
    private double quantity;
    private double price;
}
