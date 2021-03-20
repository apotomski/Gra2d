package pakiet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Panel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Random rand;
    private int jaX = 70,jaY = 250,op=0,tiki=0,pkt;
    private ArrayList<Przeszkoda> przeszkoda;
    boolean przegrana = false;

    public Panel() {
        rand = new Random();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(20, this);
        przeszkoda = new ArrayList<>();
        przeszkoda.add(new Przeszkoda(0,rand));
        przeszkoda.add(new Przeszkoda(1,rand));
        przeszkoda.add(new Przeszkoda(2,rand));
        przeszkoda.add(new Przeszkoda(3,rand));
        pkt = 0;


        timer.start();
        
    }
    private void reset() {
        przegrana = false;
        przeszkoda.clear();
        jaX = 70;
        jaY = 250;
        przeszkoda.add(new Przeszkoda(0,rand));
        przeszkoda.add(new Przeszkoda(1,rand));
        przeszkoda.add(new Przeszkoda(2,rand));
        przeszkoda.add(new Przeszkoda(3,rand));
        pkt = 0;
    }
    private void skok() {
        if (op > 0)
        {
            op = 0;
        }

        op -= 10;
    }

    public void paint(Graphics g) {

        if(!przegrana) {
            g.setColor(new Color(0, 180, 247));
            g.fillRect(0, 0, 1200, 800);
            for (int i = 0; i < przeszkoda.size(); i++) {
                przeszkoda.get(i).narysuj_przeszkode(g);
            }
            g.setColor(new Color(0, 128, 0));
            g.fillRect(0, 700, 1200, 110);
            g.setColor(new Color(0, 0, 0));
            g.fillRect(jaX, jaY, 20, 20);
        }
        else {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0, 1200, 800);
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", 1, 100));
                g.drawString("Przegrana", 350, 100);
                g.drawString("Wynik", 450, 250);
                g.drawString(String.valueOf(pkt), 550, 400);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", 1, 20));
                g.drawString("Wciśnij spację", 510, 500);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(!przegrana) {
            tiki++;

            if (tiki % 2 == 0 && op < 15) {
                op += 2;
            }
            jaY += op;
            if (jaY + 20 >= 700 || jaY <= 0) przegrana = true;

            for (int i = 0; i < przeszkoda.size(); i++) {
                przeszkoda.get(i).przesun();
                przeszkoda.get(i).ruch_przestrzeni();
            }

            for (int i = 0; i < przeszkoda.size(); i++) {
                if((jaX <= przeszkoda.get(i).getruraup().x + przeszkoda.get(i).getruraup().width) &&
                        (jaX + 20 >= przeszkoda.get(i).getruraup().x) &&
                        ((jaY <= przeszkoda.get(i).getruraup().y + przeszkoda.get(i).getruraup().height) || (jaY + 20 >= przeszkoda.get(i).getruradown().y))) {
                    przegrana = true;
                }
                
                if (przeszkoda.get(i).getruraup().x + przeszkoda.get(i).getruraup().width < 0 && przeszkoda.get(i).getruradown().x + przeszkoda.get(i).getruradown().width < 0) {
                    przeszkoda.remove(przeszkoda.get(i));
                    pkt++;
                    przeszkoda.add(new Przeszkoda(0, rand));
                }
            }
        }




        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !przegrana) skok();
        else if(e.getKeyCode() == KeyEvent.VK_SPACE && przegrana) reset();
    }
}
