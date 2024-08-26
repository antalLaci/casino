import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BlackJack extends KartyaJatek implements Jatek {

    private Jatekos jatekos;
    private int tet;
    private String akcio;
    private int zseton;
    private String huzottLap;
    private String[] jatekosKartyai = new String[11];
    private int jatekosKez;
    private String[] osztoKartyai = new String[10];
    private int osztoKez = 0;
    private boolean besokall = false;
    private boolean bj = false;


    public void felhasznaloBeker(Jatekos jatekos) {
        this.jatekos = jatekos;
        this.zseton = jatekos.getZseton();
    }

    private void osztas() {
        super.pakliFeltölt();
        super.ujraKeveres();
        jatekosKez = 0; osztoKez = 0;
        osztoKartyai[osztoKez] = super.kartyaHuzas(); osztoKez++;
        osztoKartyai[osztoKez] = super.kartyaHuzas(); osztoKez++;
        System.out.println("════════════════════════════════════");
        System.out.println("Az oszto lapjai:");
        System.out.println("    \uD83C\uDCA0       " + osztoKartyai[1]);
        System.out.println("────────────────────────────────────");
        jatekosKartyai[jatekosKez] = super.kartyaHuzas(); jatekosKez++;
        jatekosKartyai[jatekosKez] = super.kartyaHuzas(); jatekosKez++;
        System.out.println("A lapjaid: \n    " + jatekosKartyai[0] + "      " + jatekosKartyai[1]);
    }

    private void huzas() {
        System.out.println("════════════════════════════════════");
        System.out.println("Az oszto lapjai:");
        System.out.println("    \uD83C\uDCA0       " + osztoKartyai[1]);
        System.out.println("────────────────────────────────────");

        jatekosKartyai[jatekosKez] = super.kartyaHuzas(); jatekosKez++;
        System.out.println("A lapjaid: ");
        for (int i = 0; i < jatekosKez; i++) {
            System.out.print("    " + jatekosKartyai[i] + "   ");
        }
        System.out.println();
    }



    private int ertekOsszegzo(String[] kartyak, int kez) {
        int ertek = 0;
        for (int i = 0; i < kez; i++) {
            switch(kartyak[i].charAt(1)) {
                case 'J' : ertek += 10; break;
                case 'Q' : ertek += 10; break;
                case 'K' : ertek += 10; break;
                case '⒑' : ertek += 10; break;
                case 'A' : if((ertek+11) > 21) { ertek += 1; }
                else { ertek += 11; }
                    break;
                default: ertek += Character.getNumericValue(kartyak[i].charAt(1));

            }
        }
        return ertek;
    }

    private void ertekKiir() {
        System.out.println("Az osztó kártyáinak értéke: "  + ertekOsszegzo(osztoKartyai, osztoKez) +
                ", a te kártyáid értéke: " + ertekOsszegzo(jatekosKartyai, jatekosKez)) ;
    }

    private void jatek() {
        Scanner beTet = new Scanner(System.in);
        System.out.println("――――――――――――――――――――――――――――――――――――――");
        boolean hibasInput = false;
        do {
            do {
                hibasInput = false;

                try {
                    System.out.println("Kérlek írd be a tétet: ");
                    tet = beTet.nextInt();

                } catch (InputMismatchException e) {
                    hibasInput = true;
                    System.out.println("Nem számértéket írtál be!");
                    beTet.next();
                }

            } while(hibasInput);
            if (tet <= zseton) {
                osztas();

            } else {
                System.out.println("Nem tehetsz nagyobb tétet mint amennyi zsetonod van!");
            }

        } while(tet > zseton);
        zseton -= tet;

        //Játékos akciói
        Scanner beAkcio = new Scanner(System.in);
        bj = false;
        besokall = false;
        hibasInput = false;
        do {
            System.out.println("────────────────────────────────────");
            System.out.println("Megállsz vagy húzol?");
            System.out.println("┌―――――――――┓      ┌―――――――┐");
            System.out.println("│ Megállok (1)     │      │ Húzok (2)   │");
            System.out.println("└―――――――――┘      └―――――――┘");
            akcio = beAkcio.nextLine();
            akcio.toLowerCase();
            if (ertekOsszegzo(jatekosKartyai, jatekosKez) < 21 && (akcio.toLowerCase().equals("húzok") || akcio.equals("2"))) {
                hibasInput = false;
                huzas();
            } else if(akcio.toLowerCase().equals("megállok") || akcio.equals("1")) {
                hibasInput = false;
            }
            else {
                hibasInput = true;
            }
            if (hibasInput) {
                System.out.println(new NemMegfeleloInputException().getMessage());
            }
            if(ertekOsszegzo(jatekosKartyai, jatekosKez) > 21){
                akcio = "Megállok";
            }
        } while (!akcio.toLowerCase().equals("Megállok") && !akcio.equals("1"));

        //Játékos körének vége
        if (ertekOsszegzo(jatekosKartyai, jatekosKez) < 21) {
            System.out.println("Megálltál");
        } else if (ertekOsszegzo(jatekosKartyai, jatekosKez) == 21) {
            System.out.println("21-ed van");
        } else {
            System.out.println();
            System.out.println("Besokalltál");
            besokall = true;
        }

        //Osztó lapjainak felfedése
        System.out.println("────────────────────────────────────");
        System.out.println("Az oszto lapjai:");
        System.out.print("    " + osztoKartyai[0] +"       " + osztoKartyai[1]);
        while (ertekOsszegzo(osztoKartyai, osztoKez) < 17) {
            osztoKartyai[osztoKez] = super.kartyaHuzas();
            System.out.print("       " + osztoKartyai[osztoKez]);
            osztoKez++;
        }
        System.out.println("\n────────────────────────────────────");

        //Játékos lapjainak kiírása
        System.out.println("A lapjaid: ");
        for (int i = 0; i < jatekosKez; i++) {
            System.out.print("    " + jatekosKartyai[i] + "   ");
        }
        System.out.println();
        System.out.println("════════════════════════════════════");


        //Értékelés
        if(ertekOsszegzo(jatekosKartyai, 2) == 21) {
            if (ertekOsszegzo(osztoKartyai, 2) == 21) {
                System.out.println("Döntetlen mindkettőtöknek blackjackje lett! Az összes zsetonod: " + zseton);
                zseton += tet;
                ertekKiir();
            }
            else {
                System.out.println("Blackjacked lett!");
                zseton += (tet*2) + (tet/2);
                System.out.println("A nyereményed: " + ((tet*2) + (tet/2)) +" zseton. Az összes zsetonod: " + zseton);
                ertekKiir();
                bj = true;
            }
        }
        if((ertekOsszegzo(osztoKartyai, osztoKez) <= 21) && !besokall && !bj) {
            if (ertekOsszegzo(osztoKartyai, osztoKez) < ertekOsszegzo(jatekosKartyai, jatekosKez)) {
                System.out.println("Nyertél!");
                zseton += tet*2;
                System.out.println("A nyereményed: " + tet*2 +" zseton. Az összes zsetonod: " + zseton);
                ertekKiir();

            }
            else if(ertekOsszegzo(osztoKartyai, osztoKez) == ertekOsszegzo(jatekosKartyai, jatekosKez)) {
                System.out.println("Döntetlen! Az összes zsetonod: " + zseton);
                zseton += tet;
                System.out.println("Visszakaptad a téted! Az összes zsetonod: " + zseton);
                ertekKiir();

            }
            else {
                System.out.println("Vesztettél!  Az összes zsetonod: " + zseton);
                ertekKiir();
            }
        }
        else if(ertekOsszegzo(osztoKartyai, osztoKez) > 21 && !besokall) {
            System.out.println("Az osztó besokallt, nyertél!");
            zseton += tet*2;
            System.out.println("A nyereményed: " + tet*2 +" zseton. Az összes zsetonod: " + zseton);
            ertekKiir();

        }
        else if(besokall) {
            System.out.println("Besokalltál, vesztettél!  Az összes zsetonod: " + zseton);
            ertekKiir();
        }
        System.out.println("Új kör vagy kiszállsz?");
        System.out.println("┌―――――┓      ┌―――――――――┐");
        System.out.println("│ Új (1)  │      │ Kiszállok (2)   │");
        System.out.println("└―――――┘      └―――――――――┘");
        hibasInput = false;
        do {
            hibasInput = false;
            try {
                akcio = beAkcio.nextLine();
                if (akcio.equals("új") || akcio.equals("1")) {
                    jatek();
                } else if (akcio.equals("kiszallok") || akcio.equals("2")) {
                    felhasznaloVisszaad();

                } else {
                    throw new NemMegfeleloInputException();
                }
            } catch(NemMegfeleloInputException e) {
                hibasInput = true;
                System.out.println(e.getMessage());
            }
        } while(hibasInput);

    }

    public void jatekIndit(Jatekos jatekos) {
        felhasznaloBeker(jatekos);
        Scanner beAkcio = new Scanner(System.in);
        System.out.println("┌―――――――┓      ┌―――――――┐");
        System.out.println("│ Játék (1)  │      │ Leírás (2) │");
        System.out.println("└―――――――┘      └―――――――┘");
        boolean hibasInput = false;
        do {
            hibasInput = false;
            try {
                akcio = beAkcio.nextLine();
                if (akcio.toLowerCase().equals("játék") || akcio.equals("1")) {
                    jatek();
                } else if (akcio.toLowerCase().equals("leírás") || akcio.equals("2")) {
                    System.out.println("A BlackJack lényege, hogy a kártyáid értéke közelebb legyen a 21-hez mint az osztóé.");
                    System.out.println("Ha két kártyából pontosan 21-ed van az a BlackJack és abban az esetben a téted két és félszeresét kapod vissza.");
                    System.out.println("21 túllépése esetén beskollasz és vesztesz.");
                    System.out.println("Minden körben húzhatsz vagy megállhatsz.");
                    System.out.println("Nyomj meg egy gombot a folytatáshoz!");
                    beAkcio.nextLine();
                    jatek();

                } else {
                    throw new NemMegfeleloInputException();
                }
            } catch(NemMegfeleloInputException e) {
                hibasInput = true;
                System.out.println(e.getMessage());
            }
        } while(hibasInput);
    }


    public void felhasznaloVisszaad() {
        jatekos.setZseton(this.zseton);
        KaszinoIndit back = new KaszinoIndit();
        back.foMenu(jatekos);
    }
}
