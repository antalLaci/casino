public class NemMegfeleloInputException extends Exception {

    public NemMegfeleloInputException() {
        super("Ismeretlen parancs!");
    }
    public NemMegfeleloInputException(String uzenet){
        super(uzenet);
    }

}
