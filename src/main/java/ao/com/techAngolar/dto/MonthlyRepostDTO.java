package ao.com.techAngolar.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MonthlyRepostDTO {

    private int year;
    private int month;
    private Double entrada;
    private Double saida;
}
