package modele;

import controleur.Interaction;
import controleur.InteractionOnline;

import java.util.ArrayList;
import java.util.Random;

public class Magicienne extends Personnage{

    public Magicienne() {super("Magicien", 3, Caracteristiques.MAGICIENNE);}

    @Override
    public void utiliserPouvoir(Server server, boolean estEnLigne) {
        PlateauDeJeu plateau = this.getPlateau();
        int nbJoueur = plateau.getNombreJoueurs();
        int joueurNbChoisi;
        Joueur joueurChoisi;

        InteractionOnline.messageToOne(server,estEnLigne, "Voulez-vous échanger toutes vos cartes avec celles d’un autre joueur ?", this.getJoueur().getNom());

        if (Interaction.lireOuiOuNon()) {
            for (int i = 0; i < nbJoueur; i++) {
                System.out.println((i + 1) + ": " + plateau.getJoueur(i).getNom() + " | " + plateau.getJoueur(i).nbQuartiersDansMain() + " cartes");
            }

            InteractionOnline.messageToOne(server,estEnLigne, "Avec qui souhaitez-vous échanger vos cartes ?", this.getJoueur().getNom());

            joueurNbChoisi = InteractionOnline.lireUnEntier(server,estEnLigne,this.getJoueur().getNom(),"VOTRE CHOIX:",1, plateau.getNombreJoueurs() + 1);

            joueurChoisi = plateau.getJoueur(joueurNbChoisi - 1);

            if (joueurChoisi.getMonPersonnage() instanceof Magicienne) {
                InteractionOnline.messageToOne(server,estEnLigne, "Vous ne pouvez pas échanger les cartes avec vous-même.", this.getJoueur().getNom());

            } else {
                ArrayList<Quartier> mainJoueur = joueurChoisi.getMain();
                ArrayList<Quartier> mainMagicien = this.getJoueur().getMain();
                ArrayList<Quartier> temp = new ArrayList<>(mainJoueur);

                mainJoueur.clear();
                mainJoueur.addAll(mainMagicien);
                mainMagicien.clear();
                mainMagicien.addAll(temp);

                InteractionOnline.targetedMessage(server,estEnLigne, "Vous avez échangé vos cartes effectué.", "La magicienne viens d'echanger ses cartes", this.getJoueur().getNom());

            }
        } else if (this.getJoueur().nbQuartiersDansMain() != 0) {
            InteractionOnline.messageToOne(server,estEnLigne, "Combien de cartes souhaitez-vous remplacer ?", this.getJoueur().getNom());

            int nb = InteractionOnline.lireUnEntier(server,estEnLigne,this.getJoueur().getNom(),"VOTRE CHOIX:",0, this.getJoueur().nbQuartiersDansMain());

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

                    InteractionOnline.targetedMessage(server,estEnLigne, "Vous avez échangé vos cartes effectué.", "La magicienne viens d'echanger ses cartes", this.getJoueur().getNom());

                } else {
                    ArrayList<Quartier> mainMagicien = new ArrayList<Quartier>(this.getJoueur().getMain());

                    InteractionOnline.messageToOne(server,estEnLigne, "Voici vos cartes", this.getJoueur().getNom());

                    for (int i = 0; i < mainMagicien.size(); i++) {
                        Quartier quartier = mainMagicien.get(i);
                        System.out.println((i + 1) + ": " + quartier.getNom());
                    }

                    for (int i = 0; i < nb; i++) {
                        int choixCarte;
                        do {
                            InteractionOnline.messageToOne(server,estEnLigne, "Choisissez une carte à retirer (1-" + mainMagicien.size() + ") : ", this.getJoueur().getNom());

                            choixCarte = InteractionOnline.lireUnEntier(server,estEnLigne,this.getJoueur().getNom(), "VOTRE CHOIX:",1, mainMagicien.size() + 1);
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

                    InteractionOnline.targetedMessage(server,estEnLigne, "Vous avez échangé vos cartes effectué.", "La magicienne viens d'echanger ses cartes", this.getJoueur().getNom());

                }
            }
        }
    }
    @Override
    public void utiliserPouvoirAvatar() {
            Random rand = new Random();
            PlateauDeJeu plateau = this.getPlateau();
            int nbJoueur = plateau.getNombreJoueurs();
            boolean echangeCartes = rand.nextBoolean();

            if (echangeCartes) {
                Joueur joueurChoisi;
                int joueurNbChoisi;
                do {
                    joueurNbChoisi = rand.nextInt(nbJoueur);
                    joueurChoisi = plateau.getJoueur(joueurNbChoisi);
                } while (joueurChoisi == this.getJoueur() || joueurChoisi.getMonPersonnage() instanceof Magicienne);

                ArrayList<Quartier> mainJoueur = joueurChoisi.getMain();
                ArrayList<Quartier> mainMagicien = this.getJoueur().getMain();
                ArrayList<Quartier> temp = new ArrayList<>(mainJoueur);

                mainJoueur.clear();
                mainJoueur.addAll(mainMagicien);
                mainMagicien.clear();
                mainMagicien.addAll(temp);
                System.out.println(this.getJoueur() + " a échangé des cartes avec le joueur " + (joueurNbChoisi + 1));

            } else {
                int nbCartesAMelanger = 1 + rand.nextInt(this.getJoueur().nbQuartiersDansMain());
                ArrayList<Quartier> mainMagicien = this.getJoueur().getMain();
                for (int i = 0; i < nbCartesAMelanger; i++) {
                    int indexCarte = rand.nextInt(mainMagicien.size());
                    Quartier retireCarte = mainMagicien.remove(indexCarte);
                    this.getPlateau().getPioche().ajouter(retireCarte);
                    System.out.println(this.getJoueur() + " a retiré une carte de sa main.");
                }

                for (int i = 0; i < nbCartesAMelanger; i++) {
                    Quartier quartierAjoute = this.getPlateau().getPioche().piocher();
                    mainMagicien.add(quartierAjoute);
                    System.out.println(this.getJoueur() + " a ajouté une nouvelle carte à sa main.");
                }

                System.out.println(this.getJoueur() + " a terminé l'échange de " + nbCartesAMelanger + " cartes.");
            }
        }

    }

