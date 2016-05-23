package dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created by Oleg Grigorjev on 06.05.2016.
 */

@Data
@AllArgsConstructor
public class GoodDto {
    @Min(0)
    private int id;
    @Size(min = 2, max = 255)
    private String name;
    private int producer;
    @Size(min = 0, max = 512)
    private String description;

    public GoodDto(String name, int producer, String description) {
        this.name = name;
        this.producer = producer;
        this.description = description;
    }
}
