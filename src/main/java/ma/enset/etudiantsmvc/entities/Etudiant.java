package ma.enset.etudiantsmvc.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.etudiantsmvc.resources.Genre;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Etudiant {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
//    @Temporal(TemporalType.DATE)
    @Temporal( TemporalType.DATE )
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private boolean enRegle;
}

