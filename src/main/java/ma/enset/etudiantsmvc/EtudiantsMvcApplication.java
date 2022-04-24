package ma.enset.etudiantsmvc;

import ma.enset.etudiantsmvc.entities.Etudiant;
import ma.enset.etudiantsmvc.repositories.EtudiantRepository;
import ma.enset.etudiantsmvc.resources.Genre;
import ma.enset.etudiantsmvc.sec.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class EtudiantsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtudiantsMvcApplication.class, args);
    }

//    @Bean
    CommandLineRunner saveUsers(SecurityService securityService){
        return args -> {
            securityService.saveNewUser( "akram", "1234", "1234" );
            securityService.saveNewUser( "tarik", "1234", "1234" );
            securityService.saveNewUser( "zakaria", "1234", "1234" );

            securityService.saveNewRole("USER", "user");
            securityService.saveNewRole("ADMIN", "admin");

            securityService.addRoleToUser("akram", "ADMIN");
            securityService.addRoleToUser("akram", "USER");
            securityService.addRoleToUser("tarik", "USER");
            securityService.addRoleToUser("zakaria", "USER");
        };
    }

    //@Bean
    CommandLineRunner commandLineRunner(EtudiantRepository etudiantRepository){
        return args -> {
            etudiantRepository.save(
                    new Etudiant( null, "Akram", "Kssiba", "email", new Date(), Genre.MASCULIN, true));
            etudiantRepository.save(
                    new Etudiant( null, "Tarik", "Ofkir", "email", new Date(), Genre.MASCULIN, true));
            etudiantRepository.save(
                    new Etudiant( null, "Zakaria", "Hadoumi", "email", new Date(), Genre.MASCULIN, true));

            etudiantRepository.findAll().forEach( etudiant -> {
                System.out.println( etudiant.getId() + " "+ etudiant.getPrenom() );
            } );
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
