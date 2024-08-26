import java.util.InputMismatchException;
import java.util.Scanner;

public class Roulette implements Jatek {

    private Jatekos jatekos;
    
    private boolean eredmenyParos;
    private boolean eredmenySzin;
    private boolean tippParos;
    private boolean tippSzin;
    private int porgetettSzam;
    private int zseton;
    private int tetParos;
    private int tetSzin;
    private int tetSzam;
    private int tetPontosan;
    private int tippPontosan;
    private String akcio;
    
    

    public void felhasznaloBeker(Jatekos jatekos){
        this.jatekos = jatekos;
        this.zseton = jatekos.getZseton();
    }

    
    private int porget() {
    	porgetettSzam = (int) (Math.random() * (36 - 0 + 1) +0);
    	 return porgetettSzam;	
    }
    
    private void eldont(int porgetett) {
    	
    	if (porgetett % 2 == 0) {
    		eredmenyParos = true;
    	} else { 
    		eredmenyParos = false;
    	}
    	
    	if ((porgetett > 0 && porgetett < 11) || (porgetett > 18 && porgetett < 29)) {
    		if (porgetett % 2 == 0) {
        		eredmenySzin = false;
        	} else { 
        		eredmenySzin = true;
        	}
    	} else {
    		if (porgetett % 2 == 0) {
        		eredmenySzin = true;
        	} else { 
        		eredmenySzin = false;
        	}
    	}
    	
    }
    
    
    private void jatek() {
    	
    	Scanner beakcio = new Scanner(System.in);
		Scanner tetBe = new Scanner(System.in);
		System.out.println("════════════════════════════════════════");
    	System.out.println("Párosra vagy páratlanra szeretnél rakni?");
    	System.out.println("Páros 1     Páratlan 2");
		boolean hibasInput = false;
    	do {
    		hibasInput = true;
			akcio = beakcio.nextLine();

			if (akcio.equals("1") || akcio.equals("páros")) {
				tippParos = true;
				hibasInput = false;
			} else if (akcio.equals("2") || akcio.equals("páratlan")) {
				tippParos = false;
				hibasInput = false;
			}
			else {
				System.out.println(new NemMegfeleloInputException().getMessage());
				hibasInput = true;
			}
		} while(hibasInput);


		do {
			do {
				hibasInput = false;

				try {
					System.out.println("Írd be a tétet:  ");
					tetParos = tetBe.nextInt();

				} catch (InputMismatchException e) {
					hibasInput = true;
					System.out.println("Nem számértéket írtál be!");
					tetBe.next();
				}

			} while (hibasInput);
			if (tetParos <= zseton) {

			} else {
				System.out.println("Nem tehetsz nagyobb tetet mint amennyi zsetonod van!");
			}
		} while(tetParos >= zseton);
		zseton -= tetParos;
		System.out.println("────────────────────────────────────");
    	System.out.println("Most pirosra vagy feketére szeretnél rakni?");
    	System.out.println("Piros 1     Fekete 2");

		do {
			hibasInput = true;
			akcio = beakcio.nextLine();

			if (akcio.equals("1") || akcio.equals("piros")) {
				tippSzin = true;
				hibasInput = false;
			} else if (akcio.equals("2") || akcio.equals("fekete")) {
				tippSzin = false;
				hibasInput = false;
			}
			else {
				System.out.println(new NemMegfeleloInputException().getMessage());
				hibasInput = true;
			}
		} while(hibasInput);


		do {
			do {
				hibasInput = false;

				try {
					System.out.println("Írd be a tétet:  ");
					tetSzin = tetBe.nextInt();

				} catch (InputMismatchException e) {
					hibasInput = true;
					System.out.println("Nem számértéket írtál be!");
					tetBe.next();
				}

			} while (hibasInput);
			if (tetSzin <= zseton) {

			} else {
				System.out.println("Nem tehetsz nagyobb tetet mint amennyi zsetonod van!");
			}
		} while(tetSzin >= zseton);
		zseton -= tetSzin;
		System.out.println("────────────────────────────────────");
		System.out.println("Melyik számra szeretnél pontosan tenni?");
		do {
			do {
				hibasInput = false;

				try {
					tippPontosan = tetBe.nextInt();

				} catch (InputMismatchException e) {
					hibasInput = true;
					System.out.println("Nem számértéket írtál be!");
					tetBe.next();
				}

			} while (hibasInput);
			if (tippPontosan > 36) {
				System.out.println("36 a legnagyobb szám amit választhatsz.");
			}
		} while(tippPontosan > 36);

		do {
			do {
				hibasInput = false;

				try {
					System.out.println("Írd be a tétet:  (Ha nem szeretnél számra tenni írj be nullát!)");
					tetPontosan = tetBe.nextInt();

				} catch (InputMismatchException e) {
					hibasInput = true;
					System.out.println("Nem számértéket írtál be!");
					tetBe.next();
				}

			} while (hibasInput);
			if (tetPontosan <= zseton) {

			} else {
				System.out.println("Nem tehetsz nagyobb tetet mint amennyi zsetonod van!");
			}
		} while(tetPontosan >= zseton);
		zseton -= tetPontosan;
    	porget();


		System.out.println("────────────────────────────────────");
    	System.out.println("A pörgetett szám: ");

    	if(porgetettSzam == 0) {
			System.out.println("A pörgetett szám 0 lett.");
		} else {
			eldont(porgetettSzam);
			System.out.println(porgetettSzam + (eredmenySzin ? " piros" : " fekete") + (eredmenyParos ? " páros" : " páratlan"));
			if (tippParos == eredmenyParos) {
				zseton += 2*tetParos;
				System.out.println();
				System.out.println("Gratulálunk! A szám tényleg "  + (tippParos? " páros volt." : " páratlan volt."));
				System.out.println("A nyeremenyed: " + 2*tetParos + " zseton, zsetonjaid: " + zseton);
			}

			if (tippSzin == eredmenySzin) {
				zseton += 2*tetSzin;
				System.out.println();
				System.out.println("Gratulálunk! A szám tényleg "  + (tippSzin? " piros volt." : " fekete volt."));
				System.out.println("A nyeremenyed: " + 2*tetSzin + " zseton, zsetonjaid: " + zseton);
			}
    	}
    	


		if (tippPontosan == porgetettSzam) {
			zseton += 35*tetPontosan;
			System.out.println();
			System.out.println("Gratulálunk! A szám tényleg "  + porgetettSzam +" volt.");
			System.out.println("A nyeremenyed: " + 35*tetPontosan + " zseton, zsetonjaid: " + zseton);
		}
		System.out.println();
    	System.out.println("Új kört szeretnél vagy kiszállsz?");
    	System.out.println("Új kör 1       Kiszáll 2");


		hibasInput = false;
		do {
			hibasInput = false;
			try {
				akcio = beakcio.nextLine();
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
					System.out.println("A Roulette lényege, hogy megpróbáld megtippelni a pörgetett szám színét, paritását, vagy esetleg a pontos számot.");
					System.out.println("Minden körben tehetsz egy párosra/páratlanra, pirosra/feketére vagy pontos számra egy összeget.");
					System.out.println("Ha nem szeretnél tippelni, tegyél 0 zsetont.");
					System.out.println("A páros vagy páratlan és a piros vagy fekete dupla zsetont ad, a pontos szám pedig 35-szöröst");
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
