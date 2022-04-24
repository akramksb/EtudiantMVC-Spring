package ma.enset.etudiantsmvc.sec.repositories;

import ma.enset.etudiantsmvc.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
