# JEE_EtudiantMVC
Activité Pratique Spring MVC, Spring Data JPA, Spring Security  

Dans cette activité pratique créer une application Web basée sur Spring MVC, Spring Data JPA et Spring Security qui permet de gérer des étudiants.

#### Dependences
Pour les dependences on est besoin de Spring Data Jpa, Spring Web, Spring Security, Thymeleaf, MySQL Driver, Lombok.

#### Class Etudiant
On crée la classe persistante Etudiant, chaque étudiant est défini par:  
- Son id
- Son nom
- Son prénom
- Son email
- Sa date naissance
- Son genre : MASCULIN ou FEMININ
- Un attribut qui indique si il est en règle ou non  

Pour valider qu'un champs ne doit pas être vide, on ajoute l'annotation `@NotEmpty`  

```java
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Etudiant {
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String nom;
    @NotEmpty
    private String prenom;
    private String email;
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( pattern = "yyyy-mm-dd" )
    private Date date;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private boolean enRegle;
}
```

#### Etudiant Repository
```java
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {
    Page<Etudiant> findByNomContains(String kw, Pageable pageable);
}
```
  
#### Etudiant Controller

