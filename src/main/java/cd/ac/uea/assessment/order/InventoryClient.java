package cd.ac.uea.assessment.order;

import cd.ac.uea.assessment.catalog;

import org.springframework.stereotype.Component;

@Component
public class InventoryClient {
    //instance de la classe ProductService nouvellement créé
    private final ProductService productService = new ProductService(ProductRepository pr);
    public boolean checkStock(Long productId) {

        /*check if product exist :
        *  Il n'existe aucun service produit
        *  Alors commencons par :
        *  - Ajouter le champ quantity dans produit
        *  - ajouter les accesseurs et les mutateurs de ce champ
        *  - creer un repository pour produit
        *  - creer un service pour produit
        *  - Appeler ce service ici
        *  */

        Optional<Product> p = Product.findById(productId);
        //if p does not exist throw new error
        if(!p || p.quantity <= 0){
            throw new Exception("");
        }

        // In a real scenario, this might call another microservice

        //return true if it doens
        return true;
    }
}
