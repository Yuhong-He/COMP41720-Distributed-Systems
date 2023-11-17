import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.core.ClientInfo;
import service.core.Quotation;
import service.message.QuotationMessage;
import service.message.RegisterMessage;
import service.message.TimeoutMessage;

public class BrokerTests {
    static ActorSystem system;
    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }
    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system); system = null;
    }

    @Test
    public void registerQuoterTest() {
        final Props props = Props.create(Broker.class);
        final ActorRef subject = system.actorOf(props);
        final TestKit probe = new TestKit(system);
        subject.tell(new RegisterMessage(subject), probe.getRef());
    }

    @Test
    public void clientToBrokerTest() {
        final Props props = Props.create(Broker.class);
        final ActorRef subject = system.actorOf(props);
        final TestKit probe = new TestKit(system);
        subject.tell(new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false), probe.getRef());
    }

    @Test
    public void quoterToBrokerTest() {
        final Props props = Props.create(Broker.class);
        final ActorRef subject = system.actorOf(props);
        final TestKit probe = new TestKit(system);
        subject.tell(new QuotationMessage(123L, new Quotation("Google", "ahjvkrsla", 1000000)), probe.getRef());
    }

    @Test
    public void timeoutTest() {
        final Props props = Props.create(Broker.class);
        final ActorRef subject = system.actorOf(props);
        final TestKit probe = new TestKit(system);
        subject.tell(new TimeoutMessage(123L), probe.getRef());
    }
}
