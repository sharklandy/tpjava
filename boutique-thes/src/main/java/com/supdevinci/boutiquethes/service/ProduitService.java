package com.supdevinci.boutiquethes.service;

import com.supdevinci.boutiquethes.model.Produit;
import com.supdevinci.boutiquethes.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("null")
public class ProduitService {

    private final ProduitRepository produitRepository;

    // Liste des types de thé disponibles
    public static final List<String> TYPES_THE = Arrays.asList(
            "Vert", "Noir", "Oolong", "Blanc", "Pu-erh"
    );

    // Liste des origines disponibles
    public static final List<String> ORIGINES = Arrays.asList(
            "Chine", "Japon", "Inde", "Sri Lanka", "Taiwan"
    );

    @Autowired
    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    // Récupérer tous les produits
    public List<Produit> recupererTousLesProduits() {
        return produitRepository.findAll();
    }

    // Récupérer tous les produits avec tri
    public List<Produit> recupererTousLesProduits(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();
        return produitRepository.findAll(sort);
    }

    // Récupérer les produits avec pagination
    public Page<Produit> recupererProduitsPagines(int page, int taille) {
        Pageable pageable = PageRequest.of(page, taille);
        return produitRepository.findAll(pageable);
    }

    // Récupérer les produits avec pagination et tri
    public Page<Produit> recupererProduitsPagines(int page, int taille, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, taille, sort);
        return produitRepository.findAll(pageable);
    }

    // Rechercher les produits avec filtres et pagination
    public Page<Produit> rechercherProduits(String nom, String typeThe, int page, int taille, 
                                             String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, taille, sort);
        return produitRepository.rechercherProduits(nom, typeThe, pageable);
    }

    // Sauvegarder un produit
    public Produit sauvegarderProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    // Trouver un produit par ID
    public Optional<Produit> trouverProduitParId(Long id) {
        return produitRepository.findById(id);
    }

    // Supprimer un produit
    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);
    }

    // Vérifier si un produit existe
    public boolean produitExiste(Long id) {
        return produitRepository.existsById(id);
    }

    // Récupérer les types de thé
    public List<String> getTypesThe() {
        return TYPES_THE;
    }

    // Récupérer les origines
    public List<String> getOrigines() {
        return ORIGINES;
    }

    // Compter le nombre total de références
    public long compterTotalReferences() {
        return produitRepository.count();
    }

    // Compter les produits en stock (quantité > 0)
    public long compterProduitsEnStock() {
        return produitRepository.findAll().stream()
                .filter(p -> p.getQuantiteStock() != null && p.getQuantiteStock() > 0)
                .count();
    }

    // Calculer le prix moyen
    public Double calculerPrixMoyen() {
        List<Produit> produits = produitRepository.findAll();
        if (produits.isEmpty()) {
            return 0.0;
        }
        return produits.stream()
                .filter(p -> p.getPrix() != null)
                .mapToDouble(Produit::getPrix)
                .average()
                .orElse(0.0);
    }

    // Exporter les produits en CSV
    public void exporterCSV(PrintWriter writer) {
        List<Produit> produits = produitRepository.findAll();
        
        // En-tête CSV
        writer.println("ID,Nom,Type de Thé,Origine,Prix,Quantité Stock,Description,Date Réception");
        
        // Données
        for (Produit produit : produits) {
            writer.println(String.format("%d,\"%s\",\"%s\",\"%s\",%.2f,%d,\"%s\",\"%s\"",
                    produit.getId(),
                    escapeCSV(produit.getNom()),
                    escapeCSV(produit.getTypeThe()),
                    escapeCSV(produit.getOrigine()),
                    produit.getPrix(),
                    produit.getQuantiteStock(),
                    escapeCSV(produit.getDescription() != null ? produit.getDescription() : ""),
                    produit.getDateReception() != null ? produit.getDateReception().toString() : ""
            ));
        }
    }

    // Échapper les caractères spéciaux pour CSV
    private String escapeCSV(String value) {
        if (value == null) return "";
        return value.replace("\"", "\"\"");
    }

    // Compter le nombre total de produits
    public long compterProduits() {
        return produitRepository.count();
    }
}
