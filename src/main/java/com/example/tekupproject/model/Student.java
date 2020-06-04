package com.example.tekupproject.model;

public class Student {
    private String classe;
    private String niveau;
    private String nom;
    private String prenom;
    private String section;

    public Student() {
    }

    public Student(String classe, String niveau, String nom, String prenom, String section) {
        this.classe = classe;
        this.niveau = niveau;
        this.nom = nom;
        this.prenom = prenom;
        this.section = section;
    }


    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
