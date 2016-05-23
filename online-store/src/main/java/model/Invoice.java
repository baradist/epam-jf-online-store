package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * Created by Oleg Grigorjev on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class Invoice {
    private int id;
    private String number;
    private Instant date;
    private Contractor supplier;
    private Store store;
    private double sum;
    private Instant deleted;
    private Person manager;
}
