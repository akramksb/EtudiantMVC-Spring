package ma.enset.etudiantmvc.repositories;

import ma.enset.etudiantmvc.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
}
