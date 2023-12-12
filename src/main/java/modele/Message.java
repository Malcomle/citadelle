package modele;

public class Message {
    private String type;
    private String contenu;
    private String destinataire;

    private String clientName;

    public Message(String type, String contenu, String destinataire) {
        this.type = type;
        this.contenu = contenu;
        this.destinataire = destinataire;
    }
    public Message(String type, String contenu) {
        this.type = type;
        this.contenu = contenu;
        this.destinataire = null;
    }

    public String getContenu(){
        return this.contenu;
    }
    public String getType(){
        return this.type;
    }
    public String getDestinataire(){
        return this.destinataire;
    }
}


