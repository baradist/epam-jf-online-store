package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * Created by 1 on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class InvoiceDto {
    private int id;
    private String number;
    private Instant date;
    private int supplier;
    private int store;
    private double sum;
    private Instant deleted;
    private int manager;
}
