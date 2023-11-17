import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;
import service.core.ClientInfo;
import service.message.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Broker extends AbstractActor {

    static List<ActorRef> quoterActorRefs = new ArrayList<>();
    static Map<Long, OfferMessage> cache = new HashMap<>();
    static Map<Long, ActorRef> clientActorRef = new HashMap<>();

    @Override
    public Receive createReceive() {
        return new ReceiveBuilder()
            // register quoter
            .match(RegisterMessage.class,
                msg -> {
                    System.out.println("Add Actor Ref: " + msg.getActorRef());
                    quoterActorRefs.add(msg.getActorRef());
                })
            // client to broker
            .match(ClientInfo.class,
                msg -> {
                    long token = UUID.randomUUID().hashCode();
                    cache.put(token, new OfferMessage(msg, new LinkedList<>()));
                    System.out.println("Receive ClientInfo: " + msg.toString());
                    for (ActorRef ref : quoterActorRefs) {
                        ref.tell(new ClientMessage(token, msg), getSelf());
                    }
                    clientActorRef.put(token, getSender());
                    getContext()
                            .system()
                            .scheduler()
                            .scheduleOnce(
                                    Duration.create(2, TimeUnit.SECONDS),
                                    getSelf(),
                                    new TimeoutMessage(token),
                                    getContext().dispatcher(),
                                    null
                            );
                })
            // quoter to broker
            .match(QuotationMessage.class,
                msg -> {
                    System.out.println("Receive QuotationMessage: " + msg);
                    OfferMessage offerMessage = cache.get(msg.getToken());
                    if (offerMessage != null) {
                        offerMessage.getQuotations().add(msg.getQuotation());
                    }
                })
            // timeout
            .match(TimeoutMessage.class,
                msg -> {
                    System.out.println(msg);
                    long token = msg.getToken();
                    OfferMessage offerMessage = cache.get(token);
                    ActorRef ref = clientActorRef.get(token);
                    if (offerMessage != null && ref != null) {
                        ref.tell(offerMessage, getSelf());
                    }
                })
            .build();
    }
}
