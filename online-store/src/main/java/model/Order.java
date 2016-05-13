package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */

@Data
@AllArgsConstructor
public class Order {
    private int id;
    private String number;
    private Instant date;
    private Person customer;
    private State state;
    private Instant deleted;

    public enum State {
        NEW, DONE
    }
}
