public class Jatekos {

    private String nev;
    private int zseton;

    public Jatekos(String nev) {
        this.nev = nev;
        this.zseton = 5000;
    }

    public Jatekos(String nev, int zseton) {
        this.nev = nev;
        this.zseton = zseton;
    }


    public Jatekos() {

    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getZseton() {
        return zseton;
    }

    public void setZseton(int zseton) {
        this.zseton = zseton;
    }
}
