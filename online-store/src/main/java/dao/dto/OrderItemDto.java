package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */

@Data
@AllArgsConstructor
public class OrderItemDto {
    @Min(0)
    private int id;
    @NotNull
    private int order;
    @NotNull
    private int good;
    @NotNull
    private double quantity;
    private double price;

    public OrderItemDto(int order, int good, double quantity, double price) {
        this.order = order;
        this.good = good;
        this.quantity = quantity;
        this.price = price;
    }
}
