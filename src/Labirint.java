import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Labirint {
    private int[][] matriceLabirint; // Matricea labirintului
    private int latime; // Lățimea labirintului
    private int inaltime; // Înălțimea labirintului
    private final int dimensiuneCelula = 80; // Dimensiunea fiecărei celule în pixeli (pentru desenare)

    public Labirint(String filePath) {
        incarcaLabirint(filePath);
    }

    public void incarcaLabirint(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String[] dimensiuni = br.readLine().split(" ");
            inaltime = Integer.parseInt(dimensiuni[0]);
            latime = Integer.parseInt(dimensiuni[1]);

            matriceLabirint = new int[inaltime][latime];

            for (int i = 0; i < inaltime; i++) {
                String linie = br.readLine();
                String[] valori = linie.split(" ");
                for (int j = 0; j < latime; j++) {
                    matriceLabirint[i][j] = Integer.parseInt(valori[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afiseazaLabirint(Graphics g) {
        for (int i = 0; i < inaltime; i++) {
            for (int j = 0; j < latime; j++) {
                if (matriceLabirint[i][j] == 0) {
                    g.setColor(Color.BLACK); // Zid
                } else {
                    g.setColor(Color.WHITE); // Drum
                }
                g.fillRect(j * dimensiuneCelula, i * dimensiuneCelula, dimensiuneCelula, dimensiuneCelula);
                g.setColor(Color.GRAY); // Linii de grilă
                g.drawRect(j * dimensiuneCelula, i * dimensiuneCelula, dimensiuneCelula, dimensiuneCelula);
            }
        }
    }

    public int getLatime() {
        return latime;
    }

    public int getInaltime() {
        return inaltime;
    }

    public int[][] getMatriceLabirint() {
        return matriceLabirint;
    }

    public boolean esteDrum(int x, int y) {
        if (x < 0 || x >= latime || y < 0 || y >= inaltime) {
            return false;
        }
        return matriceLabirint[y][x] == 1;
    }

    public int getDimensiuneCelula() {
        return dimensiuneCelula;
    }

}
