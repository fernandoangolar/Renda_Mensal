package ao.com.techAngolar.dto;

import ao.com.techAngolar.entity.Category;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {

    @NotBlank(message = "O tipo de transação deve ser especificado.")
    @Pattern(regexp = "ENTRADA|SAIDA", message = "O tipo de transação deve ser 'ENTRADA' ou 'SAIDA'.")
    private String type;

    @NotNull(message = "O valor da transação é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor da transação não pode ser negativo.")
    private Double valor;

    @Size(max = 200, message = "A descrição da categoria não pode ter mais de 200 caracteres.")
    private String description;

    @NotNull(message = "A data da transação é obrigatória.")
    @PastOrPresent(message = "A data da transação não pode ser futura.")
    private LocalDate date;

    @NotNull(message = "A categoria da transação é obrigatória.")
    private Category category;
}
