package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 1 on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class Lot {
    private int id;
    private Store store;
    private Good good;
    private Invoice invoice;
    private InvoiceItem item;
    private double quantity;
    private double quantityRest;
    private Contractor supplier;
    private double price_sup;
    private double price_sal;
}
