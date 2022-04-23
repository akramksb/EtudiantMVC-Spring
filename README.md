# JEE_EtudiantMVC
Activité Pratique Spring MVC, Spring Data JPA, Spring Security  

####class Etudiant
```java
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Etudiant {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    @Temporal( TemporalType.DATE )
    private Date date;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private boolean enRegle;
}
```