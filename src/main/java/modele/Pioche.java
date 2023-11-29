package modele;
import java.util.ArrayList;
import java.util.Random;

public class Pioche {
    private ArrayList<Quartier> liste;

    public Pioche() {
        liste = new ArrayList<>();
    }

    public Quartier piocher() {
        if (liste.isEmpty()) {
            return null;
        }
        return liste.remove(0);
    }

    public void ajouter(Quartier nouveau) {
        liste.add(nouveau);
    }

    public int nombreElements() {
        return liste.size();
    }

    public void melanger() {
        Random generateur = new Random();
        for (int i = liste.size(); i > 1; i--) {
            int j = generateur.nextInt(i);
            int k = generateur.nextInt(i);

            Quartier temp = liste.get(j);
            liste.set(j, liste.get(k));
            liste.set(k, temp);
        }
    }
}
