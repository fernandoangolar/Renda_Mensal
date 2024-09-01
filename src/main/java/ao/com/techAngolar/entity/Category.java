package ao.com.techAngolar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Transaction> transaction = new ArrayList<>();
}
