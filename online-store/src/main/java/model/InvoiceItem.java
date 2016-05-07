package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 1 on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class InvoiceItem {
    private int id;
    private Invoice invoice;
    private Good quantity;
    private double price;
}
