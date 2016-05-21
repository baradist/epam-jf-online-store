package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */

@Data
@AllArgsConstructor
public class OrderDto {
    @Min(0)
    private int id;
    @Max(45)
    private String number;
    private Instant date;
    @NotNull
    private int customer;
    @Max(10)
    private String state;
    private double sum;
    private Instant deleted;

    public OrderDto(String number, Instant date, int customer, String state, double sum, Instant deleted) {
        this.number = number;
        this.date = date;
        this.customer = customer;
        this.state = state;
        this.sum = sum;
        this.deleted = deleted;
    }
}
