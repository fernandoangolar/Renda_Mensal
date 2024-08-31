package ao.com.techAngolar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {

    @NotBlank(message = "O nome da categoria nÃo pode ser vazio ou nulo.")
    @Size(min = 3, max = 50, message = "O nome da cetegoria deve ter entre 3 e 50 caracteres.")
    private String name;

    @Size(max = 200, message = "A descrição da categoria não pode ter mais de 200 caracteres.")
    private String description;

    private String status;
}
