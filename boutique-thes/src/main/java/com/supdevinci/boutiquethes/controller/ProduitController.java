package com.supdevinci.boutiquethes.controller;

import com.supdevinci.boutiquethes.model.Produit;
import com.supdevinci.boutiquethes.service.ProduitService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ProduitController {

    private final ProduitService produitService;
    private static final int TAILLE_PAGE = 10;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    // GET / : affiche la liste des produits avec recherche, filtre, tri et pagination
    @GetMapping("/")
    public String afficherListeProduits(
            @RequestParam(value = "recherche", required = false, defaultValue = "") String recherche,
            @RequestParam(value = "typeThe", required = false, defaultValue = "") String typeThe,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "nom") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            Model model) {

        Page<Produit> produits = produitService.rechercherProduits(
                recherche, typeThe, page, TAILLE_PAGE, sortBy, sortDir);

        model.addAttribute("produits", produits.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", produits.getTotalPages());
        model.addAttribute("totalElements", produits.getTotalElements());
        model.addAttribute("recherche", recherche);
        model.addAttribute("typeTheFiltre", typeThe);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("typesThe", produitService.getTypesThe());
        
        // Statistiques
        model.addAttribute("totalReferences", produitService.compterTotalReferences());
        model.addAttribute("produitsEnStock", produitService.compterProduitsEnStock());
        model.addAttribute("prixMoyen", produitService.calculerPrixMoyen());

        return "index";
    }

    // GET /nouveau : affiche le formulaire d'ajout
    @GetMapping("/nouveau")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("typesThe", produitService.getTypesThe());
        model.addAttribute("origines", produitService.getOrigines());
        model.addAttribute("mode", "ajout");
        return "formulaire-produit";
    }

    // POST /enregistrer : enregistre un nouveau produit
    @PostMapping("/enregistrer")
    public String enregistrerProduit(@Valid @ModelAttribute("produit") Produit produit,
                                     BindingResult bindingResult,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("typesThe", produitService.getTypesThe());
            model.addAttribute("origines", produitService.getOrigines());
            model.addAttribute("mode", "ajout");
            return "formulaire-produit";
        }

        produitService.sauvegarderProduit(produit);
        redirectAttributes.addFlashAttribute("messageSucces", "Le thé a été ajouté avec succès !");
        return "redirect:/";
    }

    // GET /modifier/{id} : affiche le formulaire de modification
    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable("id") Long id, 
                                                  Model model,
                                                  RedirectAttributes redirectAttributes) {
        Optional<Produit> produitOptional = produitService.trouverProduitParId(id);
        
        if (produitOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("messageErreur", "Produit non trouvé !");
            return "redirect:/";
        }

        model.addAttribute("produit", produitOptional.get());
        model.addAttribute("typesThe", produitService.getTypesThe());
        model.addAttribute("origines", produitService.getOrigines());
        model.addAttribute("mode", "modification");
        return "formulaire-produit";
    }

    // POST /modifier/{id} : met à jour le produit
    @PostMapping("/modifier/{id}")
    public String modifierProduit(@PathVariable("id") Long id,
                                  @Valid @ModelAttribute("produit") Produit produit,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("typesThe", produitService.getTypesThe());
            model.addAttribute("origines", produitService.getOrigines());
            model.addAttribute("mode", "modification");
            return "formulaire-produit";
        }

        produit.setId(id);
        produitService.sauvegarderProduit(produit);
        redirectAttributes.addFlashAttribute("messageSucces", "Le thé a été modifié avec succès !");
        return "redirect:/";
    }

    // GET /supprimer/{id} : supprime un produit
    @GetMapping("/supprimer/{id}")
    public String supprimerProduit(@PathVariable("id") Long id, 
                                    RedirectAttributes redirectAttributes) {
        if (!produitService.produitExiste(id)) {
            redirectAttributes.addFlashAttribute("messageErreur", "Produit non trouvé !");
            return "redirect:/";
        }

        produitService.supprimerProduit(id);
        redirectAttributes.addFlashAttribute("messageSucces", "Le thé a été supprimé avec succès !");
        return "redirect:/";
    }

    // GET /exporter-csv : exporte la liste en CSV
    @GetMapping("/exporter-csv")
    public void exporterCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"produits_thes.csv\"");
        response.setCharacterEncoding("UTF-8");
        
        // Ajout du BOM pour Excel
        response.getOutputStream().write(0xEF);
        response.getOutputStream().write(0xBB);
        response.getOutputStream().write(0xBF);
        
        produitService.exporterCSV(response.getWriter());
    }
}
