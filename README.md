# JEE_EtudiantMVC
_Activité Pratique Spring MVC, Spring Data JPA, Spring Security_  

Dans cette activité pratique on va créer une application Web basée sur Spring MVC, Spring Data JPA et Spring Security qui permet de gérer des étudiants.

### Dépendences
Pour les dependences on est besoin de __Spring Data Jpa__, __Spring Web__, __Spring Security__, __Thymeleaf__, __MySQL Driver__ et __Lombok__.

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
Donc, pour chaque page on va créer une vue qui est une page html avec le namespace suivant `xmlns:th="http://www.thymeleaf.org"`.  

Pour ne pas répéter la bar de navigation à chaque fois, on peut créer une page `template` qu'on va utiliser dans toutes les vues.  
Pour cela on crée un fichier `template.html` avec le namespace `xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"` à l'addition de `xmlns:th`.  
Et une balise `section` avec l'attribut `layout:fragment`, qui indique un fragment dans lequel on peut injecter du code html à partir des autres vues.  

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

### Security
Dans la partie sécurité, on crée les entités `AppUser` et `AppRole` avec leur repositories pour les utilisateurs et les rôles.


Pour configurer l'authentification et definir les droit d'accés, on crée une classe `SecurityConfig` qui hérite de `WebSecurityConfigurerAdapter`, avec les annotations : `@Configuration` et `@EnableWebSecurity`  

Puis on redefinit les deux méthodes : [voir l'implementation](./src/main/java/ma/enset/etudiantsmvc/sec/SecurityConfig.java)  
```java
protected void configure(AuthenticationManagerBuilder auth)
protected void configure(HttpSecurity http)
```

Pour l'authentification on utilise `UserDetailsService` pour cela on crée notre propre implementation de cette interface. On doit donc redefinir la méthode `loadUserByUsername` qui permet de récupérer des données relatives à l'utilisateur.

```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private SecurityService securityService;

    public UserDetailsServiceImpl(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = securityService.loadUserByUserName(username);

        Collection<GrantedAuthority> authorities = appUser.getAppRoles()
                .stream().map( role-> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        User user = new User( appUser.getUsername(), appUser.getPassword(), authorities);
        return user;
    }
}
```


Finalement, dans les vues, pour afficher un contenu différent selon le rôle d'utilisateur, on doit premièrement utiliser l'espace de nom : `xmlns:sec="http://www.thymeleaf.org/extras/spring-security"`.  
Par exemple, si on veut afficher un contenu, à un utilisateur avec le rôle admin, on peut faire : 
```html
<div sec:authorize="hasAuthority('ADMIN')">.
```
et si on veut autoriser tous les utilisateur authentifiés on utilise : `sec:authorize="isAuthenticated()"`

### Screen Shots  

__la liste des étudiants en tant qu'administrateur__  
![liste des etudiant as admin](./.screen%20shots/Screenshot%202022-04-30%20230000.png)  


__la liste des étudiants en tant qu'utilisateur__    
![liste des etudiant as admin](./.screen%20shots/Screenshot%202022-04-30%20230054.png)

