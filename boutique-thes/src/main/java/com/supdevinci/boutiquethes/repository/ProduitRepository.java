package com.supdevinci.boutiquethes.repository;

import com.supdevinci.boutiquethes.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    // Recherche par nom (contient, insensible à la casse)
    List<Produit> findByNomContainingIgnoreCase(String nom);

    // Recherche par type de thé
    List<Produit> findByTypeThe(String typeThe);

    // Recherche par nom ET type de thé
    List<Produit> findByNomContainingIgnoreCaseAndTypeThe(String nom, String typeThe);

    // Recherche paginée par nom
    Page<Produit> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    // Recherche paginée par type de thé
    Page<Produit> findByTypeThe(String typeThe, Pageable pageable);

    // Recherche paginée par nom ET type de thé
    Page<Produit> findByNomContainingIgnoreCaseAndTypeThe(String nom, String typeThe, Pageable pageable);

    // Requête personnalisée pour recherche combinée
    @Query("SELECT p FROM Produit p WHERE " +
           "(:nom IS NULL OR :nom = '' OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
           "(:typeThe IS NULL OR :typeThe = '' OR p.typeThe = :typeThe)")
    Page<Produit> rechercherProduits(@Param("nom") String nom, 
                                      @Param("typeThe") String typeThe, 
                                      Pageable pageable);

    // Récupérer tous les types de thé distincts
    @Query("SELECT DISTINCT p.typeThe FROM Produit p ORDER BY p.typeThe")
    List<String> findAllTypesThe();
}
