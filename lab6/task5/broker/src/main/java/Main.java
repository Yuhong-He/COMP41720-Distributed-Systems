import akka.actor.ActorSystem;
import akka.actor.Props;
import service.Broker;

public class Main {
    public static void main(String[] args) {
        ActorSystem.create().actorOf(Props.create(Broker.class), "broker");
    }
}
