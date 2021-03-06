package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Oleg Grigorjev on 06.05.2016.
 */

@Data
@AllArgsConstructor
public class Good {
    private int id;
    private String name;
    private Producer producer;
    private String description;
}
