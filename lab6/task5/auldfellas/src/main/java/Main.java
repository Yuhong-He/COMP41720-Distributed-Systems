import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.Quoter;
import service.message.RegisterMessage;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Quoter.class), "auldfellas");
        ActorSelection selection = system.actorSelection("akka.tcp://default@" + System.getenv("BROKER") + ":2550/user/broker");
        selection.tell(new RegisterMessage(ref), ref);
    }
}
