package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * Created by 1 on 07.05.2016.
 */

@Data
@AllArgsConstructor
public class Invoice {
    private int id;
    private String number;
    private Instant dateTime;
    // TODO: ...
}
