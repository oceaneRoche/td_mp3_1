package com.example.demo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class GestionMp3 {
    private TagMp3 tag;
    private Path fileSource;
    private byte[]tab;

    public TagMp3 getTag() {
        return tag;
    }

    public GestionMp3(Path fileSource) {
        this.fileSource = fileSource;
        tab = new byte[128];
        tag = new TagMp3();
    }
    public void lireTags() throws IOException {
        InputStream is = Files.newInputStream(fileSource);
        DataInputStream dis = new DataInputStream(is);
        dis.skipBytes((int) (Files.size(fileSource)-128));
        dis.read(tab);
        dis.close();
        tag.setTitre(new String(tab,3,30));
        tag.setArtiste(new String(tab,33,30));
        tag.setAlbum(new String(tab,63,30));
        tag.setAnnee(new String(tab,93,4));
        tag.setCommentaires(new String(tab,97,28));
        tag.setTrack(tab[126]);
        tag.setGenre(tab[127]);
    }
    public void ecritTags() throws IOException {
        for (int i = 0; i < tab.length; i++) {
            tab[i]=(byte) 0x00;
        }
        for (int i = 0; i < tag.getTitre().length(); i++) {
            tab[3+i]=(byte) tag.getTitre().charAt(i);
        }
        for (int i = 0; i < tag.getArtiste().length(); i++) {
            tab[33+i]=(byte) tag.getArtiste().charAt(i);
        }
        for (int i = 0; i < tag.getAlbum().length(); i++) {
            tab[63+i]=(byte) tag.getAlbum().charAt(i);
        }
        for (int i = 0; i < tag.getAnnee().length(); i++) {
            tab[93+i]=(byte) tag.getAnnee().charAt(i);
        }
        for (int i = 0; i < tag.getCommentaires().length(); i++) {
            tab[97+i]=(byte) tag.getCommentaires().charAt(i);
        }
        for (int i = 0; i < tag.getTrack(); i++) {
            tab[126] = tag.getTrack();
        }
        for (int i = 0; i < tag.getGenre(); i++) {
            tab[127] = tag.getGenre();
        }
        RandomAccessFile randomFile = new RandomAccessFile(fileSource.toAbsolutePath().toString(),"rw");
        long taille = randomFile.length();
        randomFile.seek(taille - 128);
        randomFile.write(tab);
        randomFile.close();
    }
}
