import java.util.ArrayList;
import java.util.Collections;

public class KartyaJatek{

    private String[] lapok = new String[]{
          "♠2", "♠3","♠4","♠5","♠6","♠7","♠8","♠9","♠⒑","♠J","♠Q","♠K","♠A",
          "♥2", "♥3","♥4","♥5","♥6","♥7","♥8","♥9","♥⒑","♥J","♥Q","♥K","♥A",
          "♦2", "♦3","♦4","♦5","♦6","♦7","♦8","♦9","♦⒑","♦J","♦Q","♦K","♦A",
          "♣2", "♣3","♣4","♣5","♣6","♣7","♣8","♣9","♣⒑","♣J","♣Q","♣K","♣A",
    };

    private ArrayList<String> pakli = new ArrayList<String>();

    public void pakliFeltölt(){
        for (int i = 0; i < lapok.length; i++){
            pakli.add(lapok[i]);
        }
    }

    public void ujraKeveres(){
        for(int i = 0; i < pakli.size(); i++) {
            int csere = (int) (Math.random() * pakli.size());
            Collections.swap(pakli, i, csere);
        }
    }

    public ArrayList getPakli() {
        return this.pakli;
    }

    public String kartyaHuzas() {
        String huzottLap = this.pakli.get(0);
        this.pakli.remove(0);
        return huzottLap;
    }

}
