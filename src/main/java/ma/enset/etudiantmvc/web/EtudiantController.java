package ma.enset.etudiantmvc.web;

import ma.enset.etudiantmvc.entities.Etudiant;
import ma.enset.etudiantmvc.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class EtudiantController {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @GetMapping("/index")
    public String Etudiants(Model model)
    {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        model.addAttribute( "listEtudiants", etudiants );
        return "etudiants";
    }

    @GetMapping("/etudiants")
    @ResponseBody
    public List<Etudiant> Etudiants()
    {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        return etudiants;
    }

}
