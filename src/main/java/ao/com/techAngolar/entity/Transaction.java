package ao.com.techAngolar.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private Double valor;
    private String description;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
