package com.example.demo;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    public Button buttonFichier;
    public Label labelChemin;
    public Label labelFichier;
    public Button buttonPlay;
    public Button buttonStop;
    public Button buttonLireTags;
    public Button buttonModifier;
    public Label labelCommentaire;
    public TextField TextAlbum;
    public TextField TextTitre;
    public TextField TextArtiste;
    public TextField TextAnnee;
    public TextField TextCommentaire;
    public TextField TextGenre;
    public TextField TextTrack;
    public Label labelTitre;
    public Label labelAlbum;
    public Label labelArtiste;
    public Label labelAnnee;
    public Label labelGenre;
    public Label labelTrack;
    public Button buttonEnregistrer;
    private Media media;
    private MediaPlayer mediaPlayer;
    private Path path;
    private String str_fichierSelectionner;
    private GestionMp3 gestionMp3;
    private SimpleBooleanProperty isEditable;
    private SimpleStringProperty background;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isEditable = new SimpleBooleanProperty(false);
        background = new SimpleStringProperty("-fx-background-color:#EBDEF0");
        TextTitre.editableProperty().bind(isEditable);
        TextArtiste.editableProperty().bind(isEditable);
        TextAlbum.editableProperty().bind(isEditable);
        TextAnnee.editableProperty().bind(isEditable);
        TextCommentaire.editableProperty().bind(isEditable);
        TextTrack.editableProperty().bind(isEditable);
        TextGenre.editableProperty().bind(isEditable);
        TextTitre.styleProperty().bind(background);
        TextArtiste.styleProperty().bind(background);
        TextAlbum.styleProperty().bind(background);
        TextAnnee.styleProperty().bind(background);
        TextCommentaire.styleProperty().bind(background);
        TextTrack.styleProperty().bind(background);
        TextGenre.styleProperty().bind(background);
        labelChemin.setText("");
        labelFichier.setText("");
        buttonPlay.setDisable(true);
        buttonStop.setDisable(true);
        buttonLireTags.setDisable(true);
        buttonEnregistrer.setDisable(true);
        buttonModifier.setDisable(true);
        buttonFichier.setOnAction(event -> ouvreChoixFichier());
        buttonPlay.setOnAction(event -> play());
        buttonStop.setOnAction(event -> stop());
        buttonLireTags.setOnAction(event -> {
            try {
                lireLesTags();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buttonModifier.setOnAction(event -> Modification());
        buttonEnregistrer.setOnAction(event -> {
            try {
                Enregistrer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void Modification(){
        isEditable.setValue(Boolean.TRUE);
        background.setValue("-fx-background-color:#AED6F1");
        buttonEnregistrer.setDisable(false);
    }

    private void Enregistrer() throws IOException {
        isEditable.setValue(Boolean.FALSE);
        gestionMp3.getTag().setTitre(this.TextTitre.getText());
        gestionMp3.getTag().setArtiste(this.TextArtiste.getText());
        gestionMp3.getTag().setAlbum(this.TextAlbum.getText());
        gestionMp3.getTag().setAnnee(this.TextAnnee.getText());
        gestionMp3.getTag().setCommentaires(this.TextCommentaire.getText());
        gestionMp3.getTag().setTrack(Byte.parseByte(String.valueOf(this.TextTrack.getText())));
        gestionMp3.getTag().setGenre(Byte.parseByte(String.valueOf(this.TextGenre.getText())));
        background.setValue("-fx-background-color:#EC7063");
        gestionMp3.ecritTags();
    }

    private void lireLesTags() throws IOException {
        background.setValue("-fx-background-color:#F9E79F");
        gestionMp3 = new GestionMp3(path);
        gestionMp3.lireTags();
        buttonModifier.setDisable(false);
        TextTitre.setText(gestionMp3.getTag().getTitre());
        TextArtiste.setText(gestionMp3.getTag().getArtiste());
        TextAlbum.setText(gestionMp3.getTag().getAlbum());
        TextAnnee.setText(gestionMp3.getTag().getAnnee());
        TextCommentaire.setText(gestionMp3.getTag().getCommentaires());
        TextTrack.setText(String.valueOf(gestionMp3.getTag().getTrack()));
        TextGenre.setText(String.valueOf(gestionMp3.getTag().getGenre()));
    }

    private void play() {
        str_fichierSelectionner = path.toAbsolutePath().toUri().toString();
        System.out.println(str_fichierSelectionner);
        media = new Media(str_fichierSelectionner);
        mediaPlayer = new MediaPlayer(media);
        buttonPlay.setDisable(true);
        buttonStop.setDisable(false);
        mediaPlayer.play();
    }
    private void stop() {
        mediaPlayer.stop();
        buttonStop.setDisable(false);
        buttonStop.setDisable(true);
        buttonPlay.setDisable(false);
    }

    private void ouvreChoixFichier() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("choisir un fichier");
        chooser.setInitialDirectory(new File("./mp3"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("mp3 fichiers","*.mp3"));
        File fichierSelectionner = chooser.showOpenDialog(null);
        if (fichierSelectionner != null){
            path = fichierSelectionner.toPath();
            labelChemin.setText(path.toAbsolutePath().toString());
            gestionMp3 = new GestionMp3(fichierSelectionner.toPath());
            if (gestionMp3.estProbableFichierMP3()) {
                labelFichier.setText(fichierSelectionner.getName());
                buttonPlay.setDisable(false);
                TextTitre.setText("");
                TextArtiste.setText("");
                TextAlbum.setText("");
                TextAnnee.setText("");
                TextCommentaire.setText("");
                TextTrack.setText("");
                TextGenre.setText("");
                isEditable.setValue(Boolean.FALSE);
                background.setValue("-fx-background-color:#EBDEF0");
                buttonLireTags.setDisable(false);
                buttonModifier.setDisable(true);
                buttonEnregistrer.setDisable(true);

            } else {
                afficherMessage("Ce n'est pas un fichier MP3");
                TextTitre.setText("");
                TextArtiste.setText("");
                TextAlbum.setText("");
                TextAnnee.setText("");
                TextCommentaire.setText("");
                TextTrack.setText("");
                TextGenre.setText("");
                buttonLireTags.setDisable(true);
                buttonModifier.setDisable(true);
                buttonEnregistrer.setDisable(true);
                background.setValue("-fx-background-color:#EBDEF0");
            }
        }
    }

    private void afficherMessage(String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}