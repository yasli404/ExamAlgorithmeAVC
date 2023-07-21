import java.util.ArrayList;
import java.util.HashMap;

// Classe représentant un livre
class Livre {
    private String titre;
    private String auteur;
    private String ISBN;
    private boolean estEmprunte;

    public Livre(String titre, String auteur, String ISBN) {
        this.titre = titre;
        this.auteur = auteur;
        this.ISBN = ISBN;
        this.estEmprunte = false;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean estEmprunte() {
        return estEmprunte;
    }

    public void setEmprunte(boolean emprunte) {
        estEmprunte = emprunte;
    }
}

// Classe représentant un magazine
class Magazine {
    private String titre;
    private String numero;
    private String datePublication;
    private boolean estEmprunte;

    public Magazine(String titre, String numero, String datePublication) {
        this.titre = titre;
        this.numero = numero;
        this.datePublication = datePublication;
        this.estEmprunte = false;
    }

    public String getTitre() {
        return titre;
    }

    public String getNumero() {
        return numero;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public boolean estEmprunte() {
        return estEmprunte;
    }

    public void setEmprunte(boolean emprunte) {
        estEmprunte = emprunte;
    }
}

// Classe représentant la bibliothèque
class Bibliotheque {
    private ArrayList<Livre> livres;
    private ArrayList<Magazine> magazines;
    private HashMap<String, ArrayList<Livre>> livresEmpruntes;
    private HashMap<String, ArrayList<Magazine>> magazinesEmpruntes;

    public Bibliotheque() {
        livres = new ArrayList<>();
        magazines = new ArrayList<>();
        livresEmpruntes = new HashMap<>();
        magazinesEmpruntes = new HashMap<>();
    }

    // Ajout d'un nouveau livre à la bibliothèque
    public void ajouterLivre(String titre, String auteur, String ISBN) {
        Livre livre = new Livre(titre, auteur, ISBN);
        livres.add(livre);
    }

    // Ajout d'un nouveau magazine à la bibliothèque
    public void ajouterMagazine(String titre, String numero, String datePublication) {
        Magazine magazine = new Magazine(titre, numero, datePublication);
        magazines.add(magazine);
    }

    // Emprunter un livre
    public void emprunterLivre(String utilisateur, Livre livre) {
        if (livre.estEmprunte()) {
            System.out.println("Ce livre est déjà emprunté.");
            return;
        }

        livre.setEmprunte(true);

        if (!livresEmpruntes.containsKey(utilisateur)) {
            livresEmpruntes.put(utilisateur, new ArrayList<>());
        }
        livresEmpruntes.get(utilisateur).add(livre);
    }

    // Emprunter un magazine
    public void emprunterMagazine(String utilisateur, Magazine magazine) {
        if (magazine.estEmprunte()) {
            System.out.println("Ce magazine est déjà emprunté.");
            return;
        }

        magazine.setEmprunte(true);

        if (!magazinesEmpruntes.containsKey(utilisateur)) {
            magazinesEmpruntes.put(utilisateur, new ArrayList<>());
        }
        magazinesEmpruntes.get(utilisateur).add(magazine);
    }

    // Rendre un livre
    public void rendreLivre(String utilisateur, Livre livre) {
        if (!livre.estEmprunte()) {
            System.out.println("Ce livre n'est pas emprunté.");
            return;
        }

        livre.setEmprunte(false);

        if (livresEmpruntes.containsKey(utilisateur)) {
            livresEmpruntes.get(utilisateur).remove(livre);
        }
    }

    // Rendre un magazine
    public void rendreMagazine(String utilisateur, Magazine magazine) {
        if (!magazine.estEmprunte()) {
            System.out.println("Ce magazine n'est pas emprunté.");
            return;
        }

        magazine.setEmprunte(false);

        if (magazinesEmpruntes.containsKey(utilisateur)) {
            magazinesEmpruntes.get(utilisateur).remove(magazine);
        }
    }

    // Chercher un livre par son titre ou son auteur
    public ArrayList<Livre> chercherLivres(String recherche) {
        ArrayList<Livre> resultats = new ArrayList<>();
        for (Livre livre : livres) {
            if (livre.getTitre().toLowerCase().contains(recherche.toLowerCase())
                    || livre.getAuteur().toLowerCase().contains(recherche.toLowerCase())) {
                resultats.add(livre);
            }
        }
        return resultats;
    }

    // Chercher un magazine par son titre
    public ArrayList<Magazine> chercherMagazines(String recherche) {
        ArrayList<Magazine> resultats = new ArrayList<>();
        for (Magazine magazine : magazines) {
            if (magazine.getTitre().toLowerCase().contains(recherche.toLowerCase())) {
                resultats.add(magazine);
            }
        }
        return resultats;
    }

    // Rechercher les emprunts (livres et magazines) d'un utilisateur
    public ArrayList<Object> chercherEmpruntsUtilisateur(String utilisateur) {
        ArrayList<Object> resultats = new ArrayList<>();
        if (livresEmpruntes.containsKey(utilisateur)) {
            resultats.addAll(livresEmpruntes.get(utilisateur));
        }
        if (magazinesEmpruntes.containsKey(utilisateur)) {
            resultats.addAll(magazinesEmpruntes.get(utilisateur));
        }
        return resultats;
    }
}

public class Main {
    public static void main(String[] args) {
        // Création de la bibliothèque
        Bibliotheque bibliotheque = new Bibliotheque();

        // Ajout de livres
        bibliotheque.ajouterLivre("1984", "George Orwell", "9780451524935");
        bibliotheque.ajouterLivre("Le Petit Prince", "Antoine de Saint-Exupéry", "9782070612758");
        bibliotheque.ajouterLivre("Don Quichotte", "Miguel de Cervantes", "9782080705149");

        // Ajout de magazines 
        bibliotheque.ajouterMagazine("Science Aujourd'hui", "Numéro 123", "2023-07-21");
        bibliotheque.ajouterMagazine("Mode Hebdo", "Numéro 456", "2023-07-19");

        // Emprunt d'un livre et d'un magazine 
        Livre livreEmprunte = bibliotheque.chercherLivres("1984").get(0);
        bibliotheque.emprunterLivre("Utilisateur1", livreEmprunte);

        Magazine magazineEmprunte = bibliotheque.chercherMagazines("Science Aujourd'hui").get(0);
        bibliotheque.emprunterMagazine("Utilisateur1", magazineEmprunte);

        // Recherche des emprunts de l'utilisateur
        ArrayList<Object> resultatsEmprunts = bibliotheque.chercherEmpruntsUtilisateur("Utilisateur1");
        for (Object emprunt : resultatsEmprunts) {
            if (emprunt instanceof Livre) {
                Livre livre = (Livre) emprunt;
                System.out.println("Livre emprunté par Utilisateur1: " + livre.getTitre() + " (Auteur: " + livre.getAuteur() + ")");
            } else if (emprunt instanceof Magazine) {
                Magazine magazine = (Magazine) emprunt;
                System.out.println("Magazine emprunté par Utilisateur1: " + magazine.getTitre() + " (Numéro: " + magazine.getNumero() + ", Date de publication: " + magazine.getDatePublication() + ")");
            }
        }
    }
}
