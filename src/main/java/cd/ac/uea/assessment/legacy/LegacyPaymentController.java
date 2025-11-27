package cd.ac.uea.assessment.legacy;

import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/payments")
public class LegacyPaymentController {


    private final PaieService ps = new PaieService(PaieRepository p);

    /*public LegacyPaymentController(LegacyPaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }*/

    @PostMapping
    //on se permet par ici de ne pas accepter les paiements par les  parametres mais par le body de type Paie
    public String processPayment(@RequestBody Paie p) {
        //le service que nous avons mis au conçu c'est lui qui est utilsé ici
        try {
            ps.save(p);
            return "Payment processed successfully";
        } catch (Exception e) {
            return "Payment failed: " + e.getMessage();
        }
    }
}