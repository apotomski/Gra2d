package pakiet;

import java.awt.*;
import java.util.Random;

public class Przeszkoda {
    private int szybkosc, pozX, pozY,rozmiarX,rozmiarY,k1,k2,k3;
    private Rectangle ruraup,ruradown;
    boolean kierunek;

    public Przeszkoda(int i,Random rand) {
        this.szybkosc = 5;
        this.pozX = 1200;
        this.pozY = 0;
        this.rozmiarX = 75;
        this.rozmiarY = 0;
        int pom = rand.nextInt(100) % 2;
        if(pom % 2 == 0) kierunek = false; else kierunek = true;
        k1 = rand.nextInt(250);
        k2 = rand.nextInt(250);
        k3 = rand.nextInt(250);
        this.rozmiesc(i);
    }
    public void rozmiesc(int i) {
        Random rand = new Random();
        int szerokosc = 75, wysokosc = 50 + rand.nextInt(300),przerwa = 200;
        this.rozmiarY = wysokosc;
        ruraup =new Rectangle(pozX + rozmiarX + 350 * i, 0,rozmiarX,rozmiarY);
        ruradown = new Rectangle(pozX + rozmiarX + 350 * i, przerwa + rozmiarY,rozmiarX,800-przerwa-rozmiarY-100);
    }
    public void narysuj_przeszkode(Graphics g) {
        g.setColor(new Color(k1,k2,k3));
        g.fillRect(ruraup.x,ruraup.y,ruraup.width,ruraup.height);
        g.fillRect(ruradown.x,ruradown.y,ruradown.width,ruradown.height);
    }
    public void przesun() {
        ruraup.x -= szybkosc;
        ruradown.x -= szybkosc;
    }
    public void ruch_przestrzeni() {
        if(kierunek == false) {
            ruraup.height -= 1;
            ruradown.y -= 1;
            ruradown.height += 1;
            if(ruraup.height <= 50) kierunek = true;
        } else {
            ruraup.height += 1;
            ruradown.y += 1;
            ruradown.height -= 1;
            if(ruradown.y >= 650) kierunek = false;
        }
    }

    public int getSzybkosc() {
        return szybkosc;
    }

    public Rectangle getruraup() {
        return ruraup;
    }
    public Rectangle getruradown() {
        return ruradown;
    }
    public void setSzybkosc(int szybkosc) {
        this.szybkosc = szybkosc;
    }

    public int getPozX() {
        return pozX;
    }

    public void setPozX(int pozX) {
        this.pozX = pozX;
    }

    public int getPozY() {
        return pozY;
    }

    public void setPozY(int pozY) {
        this.pozY = pozY;
    }

    public int getRozmiarX() {
        return rozmiarX;
    }

    public void setRozmiarX(int rozmiarX) {
        this.rozmiarX = rozmiarX;
    }

    public int getRozmiarY() {
        return rozmiarY;
    }

    public void setRozmiarY(int rozmiarY) {
        this.rozmiarY = rozmiarY;
    }
}
