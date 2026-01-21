package com.supdevinci.boutiquethes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "Le type de thé est obligatoire")
    @Column(name = "type_the", nullable = false)
    private String typeThe;

    @NotBlank(message = "L'origine est obligatoire")
    @Column(nullable = false)
    private String origine;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "5.0", message = "Le prix doit être au minimum de 5€")
    @DecimalMax(value = "100.0", message = "Le prix ne doit pas dépasser 100€")
    @Column(nullable = false)
    private Float prix;

    @NotNull(message = "La quantité en stock est obligatoire")
    @Min(value = 0, message = "La quantité en stock doit être au minimum de 0")
    @Column(name = "quantite_stock", nullable = false)
    private Integer quantiteStock;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    @Column(length = 500)
    private String description;

    @Column(name = "date_reception")
    private LocalDate dateReception;

    // Constructeur par défaut
    public Produit() {
    }

    // Constructeur avec paramètres
    public Produit(String nom, String typeThe, String origine, Float prix, 
                   Integer quantiteStock, String description, LocalDate dateReception) {
        this.nom = nom;
        this.typeThe = typeThe;
        this.origine = origine;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
        this.description = description;
        this.dateReception = dateReception;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeThe() {
        return typeThe;
    }

    public void setTypeThe(String typeThe) {
        this.typeThe = typeThe;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Integer getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(Integer quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", typeThe='" + typeThe + '\'' +
                ", origine='" + origine + '\'' +
                ", prix=" + prix +
                ", quantiteStock=" + quantiteStock +
                ", description='" + description + '\'' +
                ", dateReception=" + dateReception +
                '}';
    }
}
