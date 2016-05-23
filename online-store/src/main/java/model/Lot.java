package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Oleg Grigorjev on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class Lot {
    private int id;
    private Store store;
    private Good good;
    private Invoice invoice;
    private InvoiceItem invoiceItem;
    private double quantity;
    private double quantityRest;
    private Contractor supplier;
    private double priceSup;
    private double priceSal;
}
