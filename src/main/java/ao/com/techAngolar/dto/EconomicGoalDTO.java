package ao.com.techAngolar.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EconomicGoalDTO {


    @DecimalMin(value = "1.0", inclusive = false, message = "O valor da Meta Economica tem que positivo")
    private Double targetValue;

    @NotNull(message = "O Período da meta economica é obrigatório")
    @Pattern(regexp = "MENSAL|TRIMESTRAL|ANUAL", message = "O tipo de Meta Economica tem que ser 'MENSAL', 'TRIMESTRAL' ou 'ANUAL'")
    private String period;

    @NotNull(message = "O status da meta economica é obrigatório")
    @Pattern(regexp = "EM PROGRESSO", message = "O status da Meta economica tem que ser EM PROGRESSO")
    private String status;

    @NotNull(message = "A data de inicio é obrigatória")
    private LocalDate startdDate;

    @NotNull(message = "A data final é obrigatória")
    private LocalDate endDate;

    @Size(max = 200, message = "A descrição da categoria não pode ter mais de 200 caracteres.")
    private String description;
}
