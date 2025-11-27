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
        boolean isValideProduct = productService.checkProductQuantity(productId);

        if(isValideProduct == false){
            throw new Exception("Can not process this Order, Quantity check failed");
        }
        return true;
    }
}
