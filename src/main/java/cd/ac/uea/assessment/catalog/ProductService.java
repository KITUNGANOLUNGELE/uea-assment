package cd.ac.uea.assessment.catalog;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;
    public PaieService(ProductRepository pr){
        this.productRepository = pr;
    }
    public boolean checkProductQuantity(Long id){
        var p = this.productRepository.findById(id);
        if (!p || p.getQuantity<=0){
            return  false;
        }

        return true;


    }
}