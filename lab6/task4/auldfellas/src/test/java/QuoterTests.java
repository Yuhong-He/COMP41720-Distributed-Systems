import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.Quoter;
import service.core.ClientInfo;
import service.message.ClientMessage;
import service.message.QuotationMessage;

import java.time.Duration;

public class QuoterTests {
    static ActorSystem system;
    @BeforeClass
    public static void setup() {system = ActorSystem.create(); }
    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system); system = null;
    }

    @Test
    public void quoterTest() {
        final Props props = Props.create(Quoter.class);
        final ActorRef subject = system.actorOf(props);
        final TestKit probe = new TestKit(system);
        subject.tell(
                new ClientMessage(1l,
                        new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false)),
                probe.getRef());
        probe.expectMsgClass(Duration.ofSeconds(2), QuotationMessage.class);
    }

}
