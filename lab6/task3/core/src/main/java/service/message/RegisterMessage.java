package service.message;

import akka.actor.ActorRef;

public class RegisterMessage {
    ActorRef actorRef;

    public RegisterMessage(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

    public RegisterMessage() {}

    public ActorRef getActorRef() {
        return actorRef;
    }
}
