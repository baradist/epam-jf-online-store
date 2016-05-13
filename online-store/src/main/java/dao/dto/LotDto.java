package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by 1 on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class LotDto {
    @Min(0)
    private int id;
    @NotNull
    private int store;
    @NotNull
    private int good;
    @NotNull
    private int invoice;
    @NotNull
    private int invoiceItem;
    @NotNull
    private double quantity;
    @NotNull
    private double quantityRest;
    @NotNull
    private int supplier;
    @NotNull
    private double priceSup;
    @NotNull
    private double priceSal;
}
