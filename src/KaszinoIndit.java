import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class KaszinoIndit {


    public static boolean helytelenJatek = false;

    public static void main (String[] args) {
        belepes();
    }

    public static void belepes() {
        //felhasználó belépés vagy regisztráció
        Scanner be = new Scanner(System.in);
        System.out.println("┌――――――――┓      ┌―――――――――――┐      ┌――――――――┓");
        System.out.println("│ Belépés (1)  │      │ Regisztráció (2)  │      │ Kilépés (3)  │");
        System.out.println("└――――――――┘      └―――――――――――┘      └――――――――┘");
        String akcio;
        boolean nullFelhasznalo = false;
        ArrayList<String> nevek = new ArrayList<String>();
        Jatekos jatekos = new Jatekos();
        String nev = new String();
        boolean helytelenAkcio = true;
        do {
            helytelenAkcio = true;
            akcio = be.nextLine();
            if (akcio.equals("1") || akcio.equals("belépek") || akcio.equals("belépés")) {
                helytelenAkcio = false;
                do {
                    nullFelhasznalo = false;
                    System.out.println("Ird be a felhasznalo neved: ");
                    nev = be.nextLine();
                    try {
                        RandomAccessFile raf = new RandomAccessFile("nevek.txt", "r");
                        raf.seek(0);
                        int db = 0;
                        while (raf.readLine() != null) {
                            db++;
                        }
                        raf.seek(0);
                        for (int i = 0; i < db; i++) {
                            nevek.add(raf.readLine());
                            String[] adatok = nevek.get(i).split(" ");
                            if (nev.toLowerCase().equals(adatok[0].toLowerCase())) {
                                jatekos.setNev(adatok[0]);
                                jatekos.setZseton(Integer.parseInt(adatok[1]));
                            }
                        }
                        if (jatekos.getNev() == null) {
                            nullFelhasznalo = true;
                            System.out.println("Nincs ilyen felhasználó!");
                        }

                        raf.close();
                    } catch (IOException e) {
                        System.out.println("Nem sikerült a file belolvasás!");
                    }
                } while (nullFelhasznalo);

            } else if (akcio.equals("2") || akcio.equals("regisztrálok") || akcio.equals("regisztráció")) {
                boolean foglalt = false;
                boolean szokoz = false;
                helytelenAkcio = false;
                do {
                    foglalt = false;
                    szokoz = false;
                    System.out.println("Írj be egy felhasználó nevet:    (A felhasználónév nem tartalmazhat szóközt!)");
                    nev = be.nextLine();
                    try {
                        File f = new File("nevek.txt");
                        long fileLength = f.length();
                        RandomAccessFile raf = new RandomAccessFile("nevek.txt", "rw");

                        raf.seek(0);
                        int db = 0;

                        while (raf.readLine() != null) {
                            db++;
                        }
                        raf.seek(0);
                        String[] nevekReg = new String[db];
                        raf.seek(0);

                        for (int i = 0; i < db; i++) {
                            nevekReg[i] = raf.readLine();
                            String[] adatok = nevekReg[i].split(" ");
                            if (adatok[0].toLowerCase().equals(nev.toLowerCase())) {
                                foglalt = true;
                            }
                            if(nev.contains(" ") || nev.equals("")) {
                                szokoz = true;
                            }
                        }

                        if (!foglalt && !szokoz) {
                            raf.seek(fileLength);
                            raf.writeBytes(nev + " " + 5000 + "\n");
                            raf.close();
                            jatekos.setNev(nev);
                            jatekos.setZseton(5000);
                        } else if(foglalt){
                            System.out.println("Már foglalt ez a név!");
                        } else if(szokoz) {
                            System.out.println("A felhasználónév nem tartalmazhat szóközt és nem lehet üres!");
                        }

                    } catch (IOException e) {
                        System.out.println("Nem sikerült a file belolvasás!");
                    }
                } while (foglalt || szokoz);


            } else if(akcio.equals("3") || akcio.equals("kilépek") || akcio.equals("kilépés")) {
                helytelenAkcio = false;
                System.exit(0);
            }
            if(helytelenAkcio) {
                System.out.println(new NemMegfeleloInputException().getMessage());
            }
        } while(helytelenAkcio);
        System.out.println("Felhasználó: " + jatekos.getNev() + " Zsetonok: " + jatekos.getZseton());
        System.out.println(jatekos.getZseton() <= 0? "Nulla zsetonod van nem tudsz seholsem tétet tenni!" :"");
        do {
            helytelenJatek = false;
            jatekLista();
            akcio = be.nextLine();
            jatekBeker(akcio, jatekos);
        } while(helytelenJatek);

    }

    public void foMenu(Jatekos jatekos) {
        String akcio;
        boolean helytelenAkcio = true;
        Scanner be = new Scanner(System.in);
        System.out.println("┌―――――――――┐      ┌――――――――――――┓");
        System.out.println("│ Új játék (1)  │      │ Kijelentkezés (2)  │");
        System.out.println("└―――――――――┘      └――――――――――――┘");
        do {
            helytelenAkcio = true;
            akcio = be.nextLine();
            if (akcio.equals("1") || akcio.equals("új") || akcio.equals("új játék")) {
                helytelenAkcio = false;
                jatekLista();
                akcio = be.nextLine();
                jatekBeker(akcio, jatekos);
            } else if (akcio.equals("2") || akcio.equals("kijelentkezek") || akcio.equals("kijelentkezés")) {
                helytelenAkcio = false;
                try {
                    RandomAccessFile raf = new RandomAccessFile("nevek.txt", "rw");
                    raf.seek(0);
                    int db = 0;
                    while (raf.readLine() != null) {
                        db++;
                    }
                    String[] nevek = new String[db];
                    raf.seek(0);
                    for (int i = 0; i < db; i++) {
                        nevek[i] = raf.readLine();

                    }
                    raf.seek(0);
                    for (int i = 0; i < db; i++) {
                        String[] adatok = nevek[i].split(" ");
                        if (jatekos.getNev().equals(adatok[0])) {
                            nevek[i] = jatekos.getNev() + " " + jatekos.getZseton();
                        }
                        raf.writeBytes(nevek[i] + "\n");
                    }
                    raf.close();
                } catch (IOException e) {
                    System.out.println("Nem sikerült a file belolvasás!");
                }
                belepes();
            }
            if(helytelenAkcio) {
                System.out.println(new NemMegfeleloInputException().getMessage());
            }
        }while(helytelenAkcio);
    }

    private static void jatekLista() {

        System.out.println("――――――――――――――――――――――――――――――――――――――");
        System.out.println("Melyik játékkal szeretnél játszani?");
        System.out.println("┌――――――――――――┐      ┌―――――――――――┓");
        System.out.println("│ 📖 Book of Ra (1)   │      │ ♠ Blackjack (2)  │");
        System.out.println("└――――――――――――┘      └―――――――――――┘");
        System.out.println("┌――――――――┓      ┌――――――――――┓    ┌――――――――――┓");
        System.out.println("│ 🃏 Poker (3)  │     │ 🕳 Roulette (4) │    │ ⚽ Tipp Mix (5) │");
        System.out.println("└――――――――┘     └――――――――――┘    └――――――――――┘");
    }

    private static void jatekBeker(String akcio, Jatekos jatekos) {
        switch(akcio.toLowerCase()) {
            case "1":
            case "book of ra" : BookOfRa bookOfRa = new BookOfRa();
                bookOfRa.jatekIndit(jatekos);
                break;
            case "2":
            case "blackjack" : BlackJack blackJack = new BlackJack();
                blackJack.jatekIndit(jatekos);
                break;
            case "3":
            case "poker" : Poker poker = new Poker();
                poker.jatekIndit(jatekos);
                break;
            case "4":
            case "roulette" : Roulette roulette = new Roulette();
                roulette.jatekIndit(jatekos);
                break;
            case "5":
            case "tipp mix" : TippMix tippMix = new TippMix();
                tippMix.jatekIndit(jatekos);
                break;
            default:
                helytelenJatek = true;
                System.out.println("Ismeretlen játék!");
        }
    }
}
