package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 1 on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class InvoiceItemDto {
    private int id;
    private int invoice;
    private int good;
    private double quantity;
    private double price;
}
