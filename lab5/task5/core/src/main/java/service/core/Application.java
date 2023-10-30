package service.core;
import java.util.ArrayList;

public class Application {
    private static int COUNTER = 1000;
    public int id;
    public ClientInfo info;
    public ArrayList<Quotation> quotations;
    public Application(ClientInfo info) {
        this.id = COUNTER++;
        this.info = info;
        this.quotations = new ArrayList<>();
    }

    public Application() {}
}
