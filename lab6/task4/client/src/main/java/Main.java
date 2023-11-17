import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.Client;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorSelection selection = system.actorSelection("akka.tcp://default@127.0.0.1:2550/user/broker");
        ActorRef ref = system.actorOf(Props.create(Client.class, selection), "client");
        ref.tell("execute_client_service", ref);
    }
}
