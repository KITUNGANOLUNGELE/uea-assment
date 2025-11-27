package cd.ac.uea.assessment.catalog;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    //Ajout du champ quantite pour verifier le stock
    private  int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public int getQuantity(){
        return this.quantity;
    }
    public  void setQuantity(int q){
        this.quantity = q;
    }
}
