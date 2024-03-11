package com.example.demo;

public class TagMp3 {
    private String titre;
    private String artiste;
    private String album;
    private String annee;
    private String commentaires;
    private byte track;
    private byte genre;
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public byte getTrack() {
        return track;
    }

    public void setTrack(byte track) {
        this.track = track;
    }

    public byte getGenre() {
        return genre;
    }

    public void setGenre(byte genre) {
        this.genre = genre;
    }
}
