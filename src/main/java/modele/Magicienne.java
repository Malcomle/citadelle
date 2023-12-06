package modele;

import controleur.Interaction;

import java.util.ArrayList;

public class Magicienne extends Personnage{

    public Magicienne() {super("Magicien", 3, Caracteristiques.ASSASSIN);}

    @Override
    public void utiliserPouvoir() {
        PlateauDeJeu plateau = this.getPlateau();
        int nbJoueur = plateau.getNombreJoueurs();
        int joueurNbChoisi;
        Joueur joueurChoisi;

        System.out.println("Voulez-vous échanger toutes vos cartes avec celles d’un autre joueur ?");
        if (Interaction.lireOuiOuNon()) {
            for (int i = 0; i < nbJoueur; i++) {
                System.out.println((i + 1) + ": " + plateau.getJoueur(i).getNom() + " | " + plateau.getJoueur(i).nbQuartiersDansMain() + " cartes");
            }

            System.out.println("Avec qui souhaitez-vous échanger vos cartes ?");
            joueurNbChoisi = Interaction.lireUnEntier(1, plateau.getNombreJoueurs() + 1);

            joueurChoisi = plateau.getJoueur(joueurNbChoisi - 1);

            if (joueurChoisi.getMonPersonnage() instanceof Magicienne) {
                System.out.println("Vous ne pouvez pas échanger les cartes avec vous-même.");
            } else {
                ArrayList<Quartier> mainJoueur = joueurChoisi.getMain();
                ArrayList<Quartier> mainMagicien = this.getJoueur().getMain();
                ArrayList<Quartier> temp = new ArrayList<>(mainJoueur);

                mainJoueur.clear();
                mainJoueur.addAll(mainMagicien);
                mainMagicien.clear();
                mainMagicien.addAll(temp);

                System.out.println("Échange de cartes effectué.");
            }
        } else if (this.getJoueur().nbQuartiersDansMain() != 0) {

            System.out.println("Combien de cartes souhaitez-vous remplacer ?");
            int nb = Interaction.lireUnEntier(0, this.getJoueur().nbQuartiersDansMain());

            if (nb != 0) {
                if (nb == this.getJoueur().nbQuartiersDansMain()) {
                    ArrayList<Quartier> mainMagicien = this.getJoueur().getMain();

                    for (int i = 0; i < nb; i++) {
                        Quartier quartierRetire = mainMagicien.remove(0);
                        this.getPlateau().getPioche().ajouter(quartierRetire);
                    }

                    for (int i = 0; i < nb; i++) {
                        Quartier quartierAjoute = this.getPlateau().getPioche().piocher();
                        this.getJoueur().ajouterQuartierDansMain(quartierAjoute);
                    }

                    System.out.println("Échange de cartes effectué.");
                } else {
                    ArrayList<Quartier> mainMagicien = new ArrayList<Quartier>(this.getJoueur().getMain());

                    System.out.println("Voici les cartes de votre main :");
                    for (int i = 0; i < mainMagicien.size(); i++) {
                        Quartier quartier = mainMagicien.get(i);
                        System.out.println((i + 1) + ": " + quartier.getNom());
                    }

                    for (int i = 0; i < nb; i++) {
                        int choixCarte;
                        do {
                            System.out.print("Choisissez une carte à retirer (1-" + mainMagicien.size() + ") : ");
                            choixCarte = Interaction.lireUnEntier(1, mainMagicien.size() + 1);
                        } while (choixCarte < 1 || choixCarte > mainMagicien.size());

                        Quartier carteRetiree = mainMagicien.remove(choixCarte - 1);
                        this.getPlateau().getPioche().ajouter(carteRetiree);
                    }

                    for (int i = 0; i < nb; i++) {
                        Quartier quartierAjoute = this.getPlateau().getPioche().piocher();
                        mainMagicien.add(quartierAjoute);
                    }

                    this.getJoueur().getMain().clear();

                    this.getJoueur().getMain().addAll(mainMagicien);

                    System.out.println("Échange de cartes effectué.");
                }
            }
        }
    }
}
