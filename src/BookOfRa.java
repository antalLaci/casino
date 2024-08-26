import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class BookOfRa implements Jatek {

    private Jatekos jatekos;
    //J-2es szorzó
    //Q-3as szorzó
    //K-4-as szorzó
    //A-5ös szorzó
    //🤠-es 7-es szorzó
    //📕-10 es szorzó
    private String akcio;
    private int tet; //amit megadsz a játék elején  körönkénti pénz
    private int zseton; // amit befizettél összesen a játékra
    private boolean BonuszPorgetes=false; 
    private String matrix[][] = new String[3][5];
    private String[] szimbolumok = new String[]{
            "J","J","J","J","J","J","J","Q","Q","Q","Q","Q","Q","Q",
            "K","K","K","K","K","K","A","A","A","A","A","A",
            "🤠","🤠","🤠","🤠","📕","📕","📕"

    };
    public void felhasznaloBeker(Jatekos jatekos){
        this.jatekos = jatekos;
        this.zseton=jatekos.getZseton();
    }

    

    public void felhasznaloVisszaad(){
        jatekos.setZseton(this.zseton);
        KaszinoIndit back = new KaszinoIndit();
        back.foMenu(jatekos);
    }
    

    private ArrayList<String> szimbolumLista = new ArrayList<String>();

    //Feltöltjuk a listát a szimbolumokkal
    public void ListaFeltolt(){
        szimbolumLista.clear();
        for (int i = 0; i < szimbolumok.length; i++){
            szimbolumLista.add(szimbolumok[i]);
        }
    }
    
    //Összekeverjük a szimbólumokat
    public void ListaRendezes(){
        for(int i = 0; i < szimbolumLista.size(); i++) {
            int csere = (int) (Math.random() * szimbolumLista.size());
            Collections.swap(szimbolumLista, i, csere);
        }
    }
    
    //Kiíratjuk a táblába
    private String ListaRandom() {
        String huzottSzimbolum = this.szimbolumLista.get(0);
        this.szimbolumLista.remove(0);
        return huzottSzimbolum;

    }
    
    //Ha 3 szimbólum egymás mellett van, jöhet a nyeremény
    private void Egymasmellett(String[][] tarcsa) {
    	
    	int szorzo=0;
    	for(int i=0; i<3; i++) {
    		for(int j=0; j<3; j++) {
    			if(tarcsa[i][j] == tarcsa[i][j+1] && tarcsa[i][j] ==tarcsa[i][j+2]) {
    				switch(tarcsa[i][j]) {
    					case "J" : szorzo= 1; break;
    					case "Q" : szorzo=2; break;
    					case "K" : szorzo=2; break;
    					case "A" : szorzo= 3; break;
    					case "🤠" : szorzo= 7; break;
    					case "📕" : szorzo=10; break;
    				}
    				System.out.println("A nyereményed : "+szorzo*this.tet);
    			}
    		}
    	}
    	if(szorzo == 0) {
            System.out.println("Sajnos ebben a körben nem nyertél.");
        }
    	this.zseton+=this.tet*szorzo;
    	
    }
    
    private void bonusz() {
    	int porgetesekSzama = 10;
        Scanner kovetkezo = new Scanner(System.in);
        System.out.println("Gratulálok, nyert 10 ingyen pörgetést! ");
        int random=new Random().nextInt(szimbolumok.length);
        System.out.println("A szimbólum amit kiválasztott a gép: "+(szimbolumok[random]));
        BonuszPorgetes = false;

    	for(int i=0; i<porgetesekSzama; i++) {
    	    System.out.println( i+1 + ". pörgetés. A pörgetéshez nyomj meg egy gombot!");
            kovetkezo.nextLine();
            ListaFeltolt();
    		ListaRendezes();
    		porget();
            BonuszPorgetes = false;
            bonuszPorgetes(matrix);
            if(BonuszPorgetes) {
                porgetesekSzama+=2;
                System.out.println("Gratulálok kaptál plusz 2 pörgetést!");
            }
            int bonuszSzimbolumSzamlalo = 0;
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 5; k++) {
                    if(matrix[j][k] == szimbolumok[random]) {
                        bonuszSzimbolumSzamlalo++;
                    }
                }
            }
            if(bonuszSzimbolumSzamlalo >= 2) {
                zseton += bonuszSzimbolumSzamlalo * tet;
                System.out.println("Nyertél " + bonuszSzimbolumSzamlalo * tet + " zsetont.");
            }

    	}
    }
    
    
    //Bónusz pörgetés 3 könyv esetén vagy több ,10 db ingyen pörgetés, magasbb szorzók, bárhova dobhat legalább 3 szimbólumot és a végén duplázhatod vagy kivehted
   private void bonuszPorgetes(String[][] tarcsaa) {
	   BonuszPorgetes=false;
	   int x=0;
	   int szamlalo=0;
	   int bonuszSzorzo=0;
	   for(int i=0; i<3; i++) {
		   for(int j=0; j<5; j++) {
			   if(tarcsaa[i][j].equals("📕")) {
				szamlalo++;
			   }
		   }
	   }
	   if(szamlalo>=3) {

		    BonuszPorgetes=true;
	   }
   }
    
    
    
    //Módosíthatunk a tétet, ha túl kevésnek érezzük, vagy túl soknak
    public void tetModositas() {
    	
    	Scanner tetmodosit=new Scanner(System.in);
    	//int modositott=tetmodosit.nextInt();
    	boolean hibasInput = false;
        do {
            do {
                hibasInput = false;

                try {
        
                    System.out.println("Írd be az új tétet:  ");
                    tet = tetmodosit.nextInt();

                } catch (InputMismatchException e) {
                    hibasInput = true;
                    System.out.println("Nem számértéket írtál be!");
                    tetmodosit.next();
                }

            } while(hibasInput);
            if (tet <= zseton) {
                Porgetes();

            } else {
                System.out.println("Nem tehetsz nagyobb tetet mint amennyi zsetonod van!");
            }

        } while(tet >= zseton);
        zseton -= tet;

    }
    
    
    //A szimobulokat kimutatja, pörgetéshez tartozik csak szét kellett szedni az átláthatóság végett
    public void porget() {
    	for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = ListaRandom();
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
    	}
    }
       
    //Pörgetés 
    public void Porgetes() {
    	ListaFeltolt();
    	ListaRendezes();
    	BonuszPorgetes=false;
    	if(this.zseton-this.tet>=0) {
    		this.zseton-=this.tet; 
    		porget();
    	}else {
    		System.out.println("Az ön tétje: "+this.tet +" és "+this.zseton+" zsetonja van, ami sajnos nem elég a pörgetéshez.");
    		System.out.println("Ha szeretné folytatni módosítsa a tétet!");
    		
    	}

    	 bonuszPorgetes(matrix);
         if(BonuszPorgetes==true) {
        	 bonusz();
         }
         Egymasmellett(matrix);
        System.out.println();
        Scanner beakcio = new Scanner(System.in);
       System.out.println("Újra pörget, vagy kiszáll? (1 'ÚJRA' / 2 'TÉT MÓDOSÍT' / 3 'KISZÁLL' )     Zsetonja: "+this.zseton);
       System.out.println("                                                                            Bet :     "+this.tet);
       
       boolean hibasInput=true;
       do {
    	   hibasInput=true;
    	   akcio=beakcio.nextLine();
    	   if(akcio.equals("1") || akcio.toLowerCase().equals("újra")) {
    		   Porgetes();   
    		   hibasInput=false;
    	   }else if(akcio.equals("2") || akcio.toLowerCase().equals("tét módosít")) {
    		   tetModositas();
    		   Porgetes();
    		   hibasInput=false;
    	   }else if(akcio.equals("3") || akcio.toLowerCase().equals("kiszáll")) {
    		   System.out.println("Viszlát,  máskor több szerencsét!");
    		   hibasInput=false;
    		   felhasznaloVisszaad();
    	   }else {
    		   System.out.println(new NemMegfeleloInputException().getMessage());
    	   }
       }while(hibasInput);
       
    }
    
   //A játékindít lesz a mainben meghívva, ez a fő játék
    public void jatekIndit(Jatekos jatekos){
    	felhasznaloBeker(jatekos);
    	Scanner be=new Scanner(System.in);

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

                } else if (akcio.toLowerCase().equals("leírás") || akcio.equals("2")) {
                    System.out.println("A Book of Ra lényege, hogy egymás mellett három azonosos szimbólum legyen.");
                    System.out.println("Minél magasabb a szimbólum annál nagyobb szorzó lesz az eredményre.");
                    System.out.println("Három könyv esetén 10 ingyen bónusz pörgetés jár.");
                    System.out.println("Minden kör végén újra lehet pörgetni, tétet lehet módosítani és ki lehet szállni.");
                    System.out.println("Nyomj meg egy gombot a folytatáshoz!");
                    beAkcio.nextLine();


                } else {
                    throw new NemMegfeleloInputException();
                }
            } catch(NemMegfeleloInputException e) {
                hibasInput = true;
                System.out.println(e.getMessage());
            }
        } while(hibasInput);


        System.out.println("════════════════════════════════════════");
        hibasInput = false;
        do {
            do {
                hibasInput = false;

                try {
                    System.out.println("Kérlek írd be a tétet: ");
                    tet = be.nextInt();

                } catch (InputMismatchException e) {
                    hibasInput = true;
                    System.out.println("Nem számértéket írtál be!");
                    be.next();
                }

            } while(hibasInput);
            if (tet <= zseton) {
            	Porgetes();

            } else {
                System.out.println("Nem tehetsz nagyobb tetet mint amennyi zsetonod van!");
            }

        } while(tet >= zseton);
        zseton -= tet;
    }
}
