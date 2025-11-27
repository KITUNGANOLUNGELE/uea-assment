package cd.ac.uea.assessment.legacy;

import org.springframework.stereotype.Service;

@Service
public class PaieService {
    private PaieRepository paieRepository;
    public PaieService(PaieRepository pr){
        this.paieRepository = pr;
    }
    public Paie add(Paie p){
        return paieRepository.save(p)
    }
}