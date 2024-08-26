
import java.util.*;


public class TippMix implements Jatek {
    private Jatekos jatekos;
    	
    private String[] csapatok = new String[] {
    		"Real Madrid", "FC Barcelona", "Bayern München" , "Atletico Madrid", "Ferencvárosi TC", "PSG", "Radnicki Beograd",
    		"Nigeria national football team", "Al Amari" , "UTA Arad"
    };
    
    private String[][] meccsek = new String[5][3];
    
    private int[] tetek = new int[5];
    private String[] tippek = new String[5];
    private int zseton;
    private int osszNyeremeny = 0;
    private String akcio;

    private ArrayList<String> klubbok = new ArrayList<String>();
    
    public void klubFeltolt(){
        for (int i = 0; i < csapatok.length; i++){
            klubbok.add(csapatok[i]);
        }
    }
    
    public void ujraKeveres(){
        for(int i = 0; i < klubbok.size(); i++) {
            int csere = (int) (Math.random() * klubbok.size());
            Collections.swap(klubbok, i, csere);
        }
    }
    
    public String valaszt() {
        String valasztottcsapat = this.klubbok.get(0);
        this.klubbok.remove(0);
        return valasztottcsapat;

    }
    
    public void felhasznaloBeker(Jatekos jatekos){
        this.jatekos = jatekos;
        this.zseton = jatekos.getZseton();
    }
    
    private void merkozesosszeall() {
    	for(int i = 0; i < 5; i++) {
    		for(int j = 0; j < 2; j++) {
    			meccsek[i][j] = valaszt();
    		}
    	}
    }
    
	private void jatek() {
        klubFeltolt();
        ujraKeveres();
        merkozesosszeall();
        osszNyeremeny = 0;


        Scanner betipp = new Scanner(System.in);
        Scanner betet = new Scanner(System.in);
        Scanner beAkcio = new Scanner(System.in);
        for(int i = 0; i < 5; i++) {
            System.out.println("────────────────────────────────────");
            System.out.println((i+1) + ". meccs ");
            System.out.println(meccsek[i][0] + " - " + meccsek[i][1]);
            System.out.println("Mi lesz a tipp? (1 - Hazai; 2 - Vendég; 3 - Döntetlen)");

            boolean hibasInput = false;
            do {

                try {
                    tippek[i] = betipp.nextLine();
                    if (tippek[i].toLowerCase().equals("hazai") || tippek[i].equals("1")) {
                        hibasInput = false;
                    } else if (tippek[i].toLowerCase().equals("vendég") || tippek[i].equals("2")) {
                        hibasInput = false;
                    } else if (tippek[i].toLowerCase().equals("döntetlen") || tippek[i].equals("3")) {
                        hibasInput = false;
                    } else {
                        hibasInput = true;
                        throw new NemMegfeleloInputException();

                    }
                } catch(NemMegfeleloInputException e) {

                    System.out.println(e.getMessage());
                }
            } while(hibasInput);

            hibasInput = false;
            do {
                do {
                    hibasInput = false;

                    try {
                        System.out.println("Kérlek írd be a tétet:       |" + zseton + " zsetonod van");
                        tetek[i] = betet.nextInt();

                    } catch (InputMismatchException e) {
                        hibasInput = true;
                        System.out.println("Nem számértéket írtál be!");
                        betet.next();
                    }

                } while(hibasInput);
                if (tetek[i] <= zseton) {
                } else {
                    System.out.println("Nem tehetsz nagyobb tétet mint amennyi zsetonod van!");
                }

            } while(tetek[i] > zseton);
            zseton -= tetek[i];
            int random = ((int)(Math.random() * (3 - 1 + 1) + 1));
            meccsek[i][2] = String.valueOf(random);


        }

        for(int i = 0;i < 5;i++) {
            if(meccsek[i][2].equals(tippek[i])) {
                osszNyeremeny += tetek[i]*2;
            }
        }
        System.out.println("Eredmenyed:");
        for(int i = 0;i < 5;i++) {
            if(meccsek[i][2].equals(tippek[i])) {
                System.out.print("✔ ");
            }
            else {
                System.out.print("❌ ");
            }
        }
        System.out.println();
        System.out.println("A nyereményed: " + osszNyeremeny);
        zseton += osszNyeremeny;
        System.out.println("Ennyi zsetonod van: " + this.zseton);


        System.out.println("Szeretnél új kört vagy kilépsz? (1 - Új; 2 - Kiszállok)");

        boolean hibasInput = false;
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
    
   
    //A játékindít lesz a mainben meghívva, ez a fő játék
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
                    System.out.println("A Tippmix lényege, hogy megtippeld a meccsek eredményét.");
                    System.out.println("Az eredmény lehet hazai győzelem, a vendég csapat győzelme, illetve döntetlen..");
                    System.out.println("Ha nem szeretnél fogadni meccsre tehetsz 0 tétet.");
                    System.out.println("Minden helyes tippért a téted kétszeresét kapod meg.");
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
