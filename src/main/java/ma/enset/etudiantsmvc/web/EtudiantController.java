package ma.enset.etudiantsmvc.web;

import lombok.AllArgsConstructor;
import ma.enset.etudiantsmvc.entities.Etudiant;
import ma.enset.etudiantsmvc.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class EtudiantController {
    EtudiantRepository etudiantRepository;

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping("/index")
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

    @GetMapping("/delete")
    public String delete( Long id, String keyword, int page){
        etudiantRepository.deleteById( id );
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("etudiants")
    @ResponseBody
    public List<Etudiant> listEtudiants(){
        return etudiantRepository.findAll();
    }
}
