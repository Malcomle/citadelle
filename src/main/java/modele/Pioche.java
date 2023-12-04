package modele;

import java.util.ArrayList;
import java.util.Random;

public class Pioche {
    private ArrayList<Quartier> liste;

    public Pioche() {
        this.liste = new ArrayList<>();
    }

    public Quartier piocher() {
        if (!liste.isEmpty()) {
            return liste.remove(liste.size() - 1);
        } else {
            return null;
        }
    }

    public void ajouter(Quartier nouveau) {
        liste.add(0, nouveau);
    }

    public int nombreElements() {
        return liste.size();
    }

    public void melanger() {
        Random generateur = new Random();
        for (int i = 0; i < liste.size(); i++) {
            int j = generateur.nextInt(liste.size());
            Quartier temp = liste.get(i);
            liste.set(i, liste.get(j));
            liste.set(j, temp);
        }
    }
}
