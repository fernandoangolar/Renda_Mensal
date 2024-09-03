package ao.com.techAngolar.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "economic_goal")
public class EconomicGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "target_value")
    private Double targetValue;
    private String period;
    private String status;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;
}
