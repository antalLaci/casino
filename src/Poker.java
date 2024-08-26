import java.util.*;

public class Poker extends KartyaJatek implements Jatek{

    private Jatekos jatekos;
    private String[] jatekosKartyai = new String[2];
    private String[] botKartyai = new String[2];
    private String[] kozosKartyak = new String[5];
    private String akcio;
    private byte kor = 3;
    private boolean mutat = false;
    private boolean[] jatekosEredmeny = new boolean[10];
    private boolean[] botEredmeny = new boolean[10];
    private String[] vegeredmenyek = new String[]
            {"magas lappal","párral","dupla párral","drillel","sorral","flössel","fullal","pókerrel","színsorral","royal flössel"};
    private int jatekosVegeredmeny = 0;
    private int botVegeredmeny = 0;
    private int zseton;
    private int kozosPenz;
    private int emelesErteke;
    private boolean dobta = false;

    private void osztas() {
        super.pakliFeltölt();
        super.ujraKeveres();
        for (int i = 0; i < 2; i++) {
            jatekosKartyai[i] = super.kartyaHuzas();
            botKartyai[i] = super.kartyaHuzas();
        }
        for (int i = 0; i < 5; i++) {
            kozosKartyak[i] = super.kartyaHuzas();
        }
        System.out.println("Közös lapok: ");
        for (int i = 0; i < 3; i++) {
            System.out.print(kozosKartyak[i] + " ");
        }
        System.out.println();
        System.out.println("A kártyáid: ");
        for (String lap : jatekosKartyai) {
            System.out.print(lap + " ");
        }

    }

    private void kartyakKiir() {
        System.out.println("Közös lapok: ");
        for (int i = 0; i < kor; i++) {
            System.out.print(kozosKartyak[i] + " ");
        }
        System.out.println();

        if(mutat) {
            System.out.println("A kártyáid:             │ Az ellefél kártyái");
            for (String lap : jatekosKartyai) {
                System.out.print(lap + " ");
            }
            System.out.print("                  │ ");
            for (String lap : botKartyai) {
                System.out.print(lap + " ");
            }
        }
        else {
            System.out.println("A kártyáid: ");
            for (String lap : jatekosKartyai) {
                System.out.print(lap + " ");
            }
        }
    }

    private void ertekel(String[] kartyak, boolean[] eredmeny) {
        //eredmeny nullazas
        for(boolean bool : eredmeny) {
            bool = false;
        }
        int sorKezdErt = 0;
        char szamPar = ' ';

        String[] osszKartya = new String[7];

        //please don't judge me
        osszKartya[0] = kartyak[0]; osszKartya[1] = kartyak[1]; osszKartya[2] = kozosKartyak[0]; osszKartya[3] = kozosKartyak[1]; osszKartya[4] = kozosKartyak[2]; osszKartya[5] = kozosKartyak[3]; osszKartya[6] = kozosKartyak[4];

        ArrayList<Integer> ertekek = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            switch(osszKartya[i].charAt(1)) {
                case 'J' : ertekek.add(11); break;
                case 'Q' : ertekek.add(12); break;
                case 'K' : ertekek.add(13); break;
                case '⒑' : ertekek.add(10); break;
                case 'A' : ertekek.add(14); break;
                default: ertekek.add(Character.getNumericValue(osszKartya[i].charAt(1)));
            }
        }


        //pár
        for(int i = 0; i < kartyak.length; i++) {
            for(int j = 0; j < kozosKartyak.length; j++) {
                if (kartyak[i].charAt(1) == kozosKartyak[j].charAt(1) || kartyak[0].charAt(1) == kartyak[1].charAt(1)) {
                    eredmeny[1] = true;
                 //   System.out.println("Párod van");
                    szamPar = kartyak[i].charAt(1);
                }
            }
        }

        //dupla pár
        if(eredmeny[1]) {
            for(int i = 0; i < kartyak.length; i++) {
                for(int j = 0; j < kozosKartyak.length; j++) {
                    if (kartyak[i].charAt(1) == kozosKartyak[j].charAt(1) && kartyak[i].charAt(1) != szamPar) {
                        eredmeny[2] = true;
                    //    System.out.println("Dupla párod van");
                    }
                }
            }
        }

        //drill
        for(int i = 0; i < kartyak.length; i++) {
            for(int j = 0; j < kozosKartyak.length; j++) {
                if (kartyak[i].charAt(1) == kozosKartyak[j].charAt(1)) {
                    for(int k = j+1; k < kozosKartyak.length; k++) {
                        if(kartyak[i].charAt(1) == kozosKartyak[j].charAt(1) && kartyak[i].charAt(1) == kozosKartyak[k].charAt(1)) {
                            eredmeny[3] = true;
                        //    System.out.println("Drilled van");
                        }
                    }
                }
                if(kartyak[0].charAt(1) == kartyak[1].charAt(1) && kartyak[0].charAt(1) == kozosKartyak[j].charAt(1)){
                    eredmeny[3] = true;
                 //   System.out.println("Drilled van");
                }
            }
        }

        //sor
        for(int i = 0; i < ertekek.size(); i++) {
            if(ertekek.contains(ertekek.get(i)+1) && ertekek.contains(ertekek.get(i)+2)&& ertekek.contains(ertekek.get(i)+31)&& ertekek.contains(ertekek.get(i)+4)) {
                eredmeny[4] = true;
                sorKezdErt = i;
            //    System.out.println("Sorod van");
            }
        }


        //flush
        int pikkSzamlalo = 0, korSzamlalo = 0, karoSzamlalo = 0, treffSzamlalo = 0;
        for (int i = 0; i < 7; i++) {
            switch (osszKartya[i].charAt(0)) {
                case '♠' : pikkSzamlalo++; break;
                case '♥' : korSzamlalo++; break;
                case '♦' : karoSzamlalo++; break;
                case '♣' : treffSzamlalo++; break;
            }
        }
        if(pikkSzamlalo == 5 || korSzamlalo == 5 || karoSzamlalo == 5 || treffSzamlalo == 5) {
            eredmeny[5] = true;
        //    System.out.println("Színsorod van");
        }

        //full
        if(eredmeny[2] && eredmeny[3]) {
            eredmeny[6] = true;
         //   System.out.println("Fullod van");
        }


        //póker
        for(int i = 0; i < osszKartya.length; i++) {

            if(Collections.frequency(ertekek, ertekek.get(i)) == 4) {
                eredmeny[7] = true;
            }
        }

        //színsor
        if(eredmeny[4] && eredmeny[5]) {
            eredmeny[8]=true;
        }


        //royal flösh
        if(eredmeny[8]) {
            if(ertekek.contains(10) && ertekek.contains(11) && ertekek.contains(12) && ertekek.contains(13) && ertekek.contains(14)) {
                eredmeny[9] = true;
            }
        }

    }

    public void felhasznaloBeker(Jatekos jatekos){
        this.jatekos = jatekos;
        this.zseton = jatekos.getZseton();
    }

    private void jatek() {
        Scanner beAkcio = new Scanner(System.in);
        Scanner beTet = new Scanner(System.in);
        kozosPenz = 0;
        mutat = false;
        dobta = false;
        kor = 3;
        jatekosVegeredmeny = 0;
        botVegeredmeny = 0;
        System.out.println("════════════════════════════════════════");
        osztas();
        boolean hibasInput = false;
        for(int i = 0; i < 2;i++) {
            System.out.println();
            System.out.println("────────────────────────────────────");
            System.out.println("Emelsz vagy checkelsz? Pot: " + kozosPenz + " Zsetonjaid: " + zseton);
            System.out.println("┌―――――――――┓      ┌―――――――┐");
            System.out.println("│ Emelek (1)       │      │ Check (2)   │");
            System.out.println("└―――――――――┘      └―――――――┘");
            do {
                akcio = beAkcio.nextLine();
                if (akcio.equals("1") || akcio.toLowerCase().equals("emelek") || akcio.toLowerCase().equals("emelés")) {
                    hibasInput = false;
                    do {
                        do {
                            hibasInput = false;

                            try {
                                System.out.println("Írd be mennyivel szeretnél emelni: ");
                                emelesErteke = beTet.nextInt();

                            } catch (InputMismatchException e) {
                                hibasInput = true;
                                System.out.println("Nem számértéket írtál be!");
                                beTet.next();
                            }

                        } while(hibasInput);
                        if (emelesErteke <= zseton) {


                        } else {
                            System.out.println("Nem emelhetsz többel mint amennyi zsetonod van!");
                        }

                    } while(emelesErteke > zseton);
                    zseton -= emelesErteke;
                    ertekel(botKartyai, botEredmeny);
                    if (botEredmeny[4] || botEredmeny[2] || ((int) ((Math.random() * (2 - 0)) + 0)) == 1) {
                        kozosPenz += emelesErteke;
                        zseton -= emelesErteke;
                        System.out.println("Az ellenfel tartotta ez emelest!");
                        kor++;
                    } else {
                        System.out.println("Az ellenfel dobta a lapjait");
                        zseton += kozosPenz;
                        dobta = true;
                        break;
                    }

                } else if (akcio.equals("2") || akcio.toLowerCase().equals("checkelek") || akcio.toLowerCase().equals("checkelés")){
                    hibasInput = false;

                    if (botEredmeny[1] || botEredmeny[2] || ((int) ((Math.random() * (2 - 0)) + 0)) == 1) {
                        emelesErteke = (int)(Math.random() * ((zseton/2)-1) +1 );
                        System.out.println("Az ellenfél emel " + emelesErteke + " zsetonnal.");

                        System.out.println("Tartod vagy dobod?      Zsetonjaid: " + zseton);
                        System.out.println("┌―――――――――┓      ┌―――――――┐");
                        System.out.println("│ Tartom (1)       │      │ Dobom (2)   │");
                        System.out.println("└―――――――――┘      └―――――――┘");

                        hibasInput = false;
                        do {
                            hibasInput = false;
                            try {
                                akcio = beAkcio.nextLine();
                                if (akcio.toLowerCase().equals("tartom") || akcio.equals("1")) {
                                    if(emelesErteke > zseton) {
                                        System.out.println("Sajnos nincs elég zsetonod!");
                                    }
                                    else {
                                        System.out.println("Tartottad az emelést.");
                                        zseton -= emelesErteke;
                                    }

                                } else if (akcio.toLowerCase().equals("dobom") || akcio.equals("2")) {
                                    System.out.println("Dobtad a lapjaidat!");
                                    dobta = true;
                                    break;

                                } else {
                                    throw new NemMegfeleloInputException();
                                }
                            } catch(NemMegfeleloInputException e) {
                                hibasInput = true;
                                System.out.println(e.getMessage());
                            }
                        } while(hibasInput);


                    } else {
                        System.out.println("Az ellenfél is checkelt.");
                    }



                    kor++;
                }
                else {
                    hibasInput = true;
                    System.out.println(new NemMegfeleloInputException().getMessage());
                }
            } while(hibasInput);
            if(i == 1) {
                mutat = true;
            }
            if(dobta) {
                break;
            }

            kartyakKiir();



        }
        if (dobta) {
            kor = 5;
            mutat = true;
            kartyakKiir();
        }

        System.out.println();

        ertekel(jatekosKartyai, jatekosEredmeny);
        ertekel(botKartyai, botEredmeny);
        jatekosVegeredmeny = 0;
        botVegeredmeny = 0;
        for (int i = 0; i < 10; i++) {
            if(jatekosEredmeny[i]) {
                if(i > jatekosVegeredmeny) {
                    jatekosVegeredmeny = i;
                    jatekosEredmeny[i]= false;
                }
            }
            if(botEredmeny[i]) {
                if(i > botVegeredmeny) {
                    botVegeredmeny = i;
                    jatekosEredmeny[i]= false;
                }
            }
        }
        if (jatekosVegeredmeny > botVegeredmeny) {
            System.out.println("Te győztél " + vegeredmenyek[jatekosVegeredmeny]);
            zseton += kozosPenz;
        }
        if (jatekosVegeredmeny < botVegeredmeny) {
            System.out.println("Az ellenfél győzött " + vegeredmenyek[botVegeredmeny]);
        }
        if (jatekosVegeredmeny == botVegeredmeny) {
            int magaslapJatekos = 0;
            int magaslapBot = 0;
            for(int i = 0; i<jatekosKartyai.length; i++) {
                if(Character.getNumericValue(jatekosKartyai[i].charAt(1)) > magaslapJatekos) {
                    magaslapJatekos = Character.getNumericValue(jatekosKartyai[i].charAt(1));
                }
                if(Character.getNumericValue(botKartyai[i].charAt(1)) > magaslapBot) {
                    magaslapBot = Character.getNumericValue(botKartyai[i].charAt(1));
                }
            }
            if(magaslapJatekos > magaslapBot) {
                System.out.println("Te győztél magaslappal!");
                zseton += kozosPenz;
            }
            else if (magaslapJatekos < magaslapBot) {
                System.out.println("Az ellenfél győzött magaslappal!");
            }
            else {
                zseton += kozosPenz/2;
            }
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
                if (akcio.toLowerCase().equals("új") || akcio.equals("1")) {
                    jatek();
                } else if (akcio.toLowerCase().equals("kiszallok") || akcio.equals("2")) {
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

    public void jatekIndit(Jatekos jatekos){
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
                    System.out.println("A Póker lényege, hogy te vidd el a kasszában lévő közös pénzt.");
                    System.out.println("Mindenki két lapot kap és a közös lapokhoz kikerül három, ekkor lehet emelni vagy tovább menni, és még két körben két kártya kerül felfedésre.");
                    System.out.println("Minden kártya után emelhetsz vagy chackelhetsz, ha az ellenfél emel, dobhatod vagy tarthatod.");
                    System.out.println("Kombináció erősségek:");
                    System.out.println("Magas lap > Pár > Dupla pár > Drill > Sor > Flös > Full > Póker > Szín sor > Royal flös");
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
    public void felhasznaloVisszaad(){
        jatekos.setZseton(this.zseton);
        KaszinoIndit back = new KaszinoIndit();
        back.foMenu(jatekos);
    }
}
