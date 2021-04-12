# Gra2d

Jest to implementacja popularnej gry Flappy bird w javie z wykorzystaniem swinga i awt.
Przeszkody i obiekt którym sterujemy są wykonane z prostokątów. Każda przeszkoda ma inny kolor który jest losowany przy tworzeniu obiektu.
Tak jak w orginalnej grze dotknięcie przeszkody lub podłoża albo granicy nieba kończy się porażką po której wyświetlany jest uzyskany wynik punktowy.
Zdobycie punktu następuje kiedy przeszkoda opuści opszar mapy.
Dodtkową modyfikacją której nie ma w orginalnej mechanice gry jest to, że przestrzeń między przeszkodami stale się przemieszcza w pionie.

Opis kodu

Klasa Przeszkoda

Zmienne zawierającę pozycję przeszkody jej szybkość  rozmiary oraz wartości liczbowe które określają kolor a tagże 2 obiekty rectangle które są górną i dolną częścią przeskody

    private int szybkosc, pozX, pozY,rozmiarX,rozmiarY,k1,k2,k3;
    private Rectangle ruraup,ruradown;
    boolean kierunek;

Konstruktor klasy ustawia zmienne oraz losuje wartości dla kolorów i wyznacza kierunek przemieszczania się przeszkód

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
    
funkcja rozmieszcza  przeszkodę na skraju planszy z odpowiednim przesunięciem w prawo oraz losuje wysokości górnego elementu przeszkody
    
    public void rozmiesc(int i) {
        Random rand = new Random();
        int szerokosc = 75, wysokosc = 50 + rand.nextInt(300),przerwa = 200;
        this.rozmiarY = wysokosc;
        ruraup =new Rectangle(pozX + rozmiarX + 350 * i, 0,rozmiarX,rozmiarY);
        ruradown = new Rectangle(pozX + rozmiarX + 350 * i, przerwa + rozmiarY,rozmiarX,800-przerwa-rozmiarY-100);
    }
    
 funkcja rysuje przeszkodę w jej aktualnych wspułżędnych i nadając jej kolory które zostały wylosowane przy tworzeniu przeszkody
 
     public void narysuj_przeszkode(Graphics g) {
        g.setColor(new Color(k1,k2,k3));
        g.fillRect(ruraup.x,ruraup.y,ruraup.width,ruraup.height);
        g.fillRect(ruradown.x,ruradown.y,ruradown.width,ruradown.height);
    }
    
funkcja przesuwa przeszkodę w lewo o wartość szybkości tej przeszkody

    public void przesun() {
        ruraup.x -= szybkosc;
        ruradown.x -= szybkosc;
    }

funkcja sprawdza w jakim kierunku ma się przemieszczać wolna przestrzeń między elementami przeszkody i zwiększa bądź zmniejsza ich wysokości odpowiednio przesuwając co daje iluzję przesuwania się przestrzeni między elementami przeszkody

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
    
 Klasa Panel
 
 zmienne potrzebne do funkcjonowania gry: zegar, generator liczb losowych, pozycja postaci, liczba tików, liczba punktów,  arraylista przeszkud i wartość oznaczająca czy już przegraliśmy czy nie 
 
    private Timer timer;
    private Random rand;
    private int jaX = 70,jaY = 250,op=0,tiki=0,pkt;
    private ArrayList<Przeszkoda> przeszkoda;
    boolean przegrana = false;
    
konstruktor ustawiający wartości dla panelu  tworzący obiekty takie jak rand timer czy lista przeszkud dodając je do listy oraz ustawia liczbę punktów na 0 i uruchamia zegar
    
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
   
 funkcja resetuje grę ustawiając na nowo wszystkie parametry
   
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
    
 funkcja sprawdzająca czy skok jest możliwy i jeśli tak to go wykonująca poprzez zmianę wartości op która jest wykorzystywana do zmiany położenia naszej postaci
    
     private void skok() {
        if (op > 0)
        {
            op = 0;
        }

        op -= 10;
    }
    
funkcja rysująca wszystkie obiekty i wywołująca wszystkie funkcje z tym związane (w zależności czy gra aktualnie się toczy czy nie)
    
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
    
 funkcja wykonująca zdarzenia, sprawdza czy możemy czy nie przegraliśmy, obsługuje spadanie naszej postaci, przesuwa przeszkody sprawdza czy dostaliśmy punkt tworzy nowe obiekty kiedy wyjdą one poza mapę
    
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
    
obsługa wciśnięcia spacji wywołuje funcję skok albo reset w zależności od ego czy toczy się gra czy nie
    
     public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !przegrana) skok();
        else if(e.getKeyCode() == KeyEvent.VK_SPACE && przegrana) reset();
    }
    
Klasa Okno

konstruktor klasy ustwaiający odpowiednie parametry dla okna i tworzący obiekt tklasy Panel

     public Okno() {
        panel = new Panel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(10,10,1200,800);
        this.add(panel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle("gra");

    }
    
 Klasa Main
 
 wywołuje obiekt klasy okno
 
    public static void main(String[] args) {
    	// write your code here
        Okno o = new Okno();
    }
