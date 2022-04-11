package ma.enset.etudiantmvc;

import ma.enset.etudiantmvc.entities.Etudiant;
import ma.enset.etudiantmvc.repositories.EtudiantRepository;
import ma.enset.etudiantmvc.resources.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class EtudiantMvcApplication implements CommandLineRunner {
    @Autowired
    EtudiantRepository etudiantRepository;
    public static void main(String[] args) {

        SpringApplication.run(EtudiantMvcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        etudiantRepository.save(
//                new Etudiant(null,"akram","kssiba", "a.k@g.com",new Date(), Genre.MASCULIN,false));

        etudiantRepository.findAll().forEach(p->{
            System.out.println(p.getNom());
        });
    }
}
