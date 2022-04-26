# JEE_EtudiantMVC
Activité Pratique Spring MVC, Spring Data JPA, Spring Security  

Dans cette activité pratique créer une application Web basée sur Spring MVC, Spring Data JPA et Spring Security qui permet de gérer des étudiants.

### Dependences
Pour les dependences on est besoin de Spring Data Jpa, Spring Web, Spring Security, Thymeleaf, MySQL Driver, Lombok.

### Entités
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

### Ropositories
En crée une repository etudiant, et on ajoute une methode qui nous permettra de faire une recherche paginée des étudiants.

```java
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {
    Page<Etudiant> findByNomContains(String kw, Pageable pageable);
}
```
  
### Web
Pour répondre aux requêtes des clients, en ajoute la couche web dans laquel on definit des controlleurs ( classe avec l'annotation `@Contoller` ). 
Dans notre cas on a la classe `EtudiantController` qui contient des methodes qui retourne des vue pour chaque route.

```java
@Controller
@AllArgsConstructor
public class EtudiantController {
    EtudiantRepository etudiantRepository;

    @GetMapping("/user/index")
    public String etudiants(Model model,
                            @RequestParam(name="page",defaultValue = "0") int page,
                            @RequestParam(name = "size",defaultValue = "5") int size,
                            @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Page<Etudiant> pageEtudiants = etudiantRepository.findByNomContains( keyword, PageRequest.of(page,size));
        model.addAttribute( "listEtudiants", pageEtudiants.getContent() );
        model.addAttribute("pages", pageEtudiants.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "etudiants";
    }
```

Pour communiquer avec les vues, on utilise un `Model` que l'on utilise pour passer des paramètres.


### Vues
On génère les pages html à partir des vues avec le moteur de template `thymeleaf`.  
Donc, pour chaque page on va créer une vue qui est une page html avec le namespace suivant ``xmlns:th="http://www.thymeleaf.org"``.  

Pour ne pas répéter la bar de navigation à chaque fois, on peut créer une page `template` qu'on va utiliser dans toutes les vues.  
Pour cela on crée un fichier `template.html` avec le namespace ``xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"`` à l'addition de ``xmlns:th``.  
Et une balise ``section`` avec l'attribut ``layout:fragment``, qui indique un fragment dans lequel on peut injecter du code html à partir des autres vues.  

>template.html
```html
<!DOCTYPE html>
<html lang="en"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
...
<section layout:fragment="content1"></section>
...
</html>
```

>Dans les autre vues on ajoute l'attribut ``layout:decorate`` en specifiant la page à utiliser comme une base.  
Puis on definit le contenu du fragment avec ``layout:fragment``.
```html
<html lang="en"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="template">
...
<div layout:fragment="content1">
    ...
</div>
...
</html>
```



