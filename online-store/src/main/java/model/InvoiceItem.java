package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Oleg Grigorjev on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class InvoiceItem {
    private int id;
    private Invoice invoice;
    private Good good;
    private double quantity;
    private double price;
}
