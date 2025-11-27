package cd.ac.uea.assessment.legacy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "paiements")
@Data
public class Paie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float amount;

    public Paie (float amount){
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}