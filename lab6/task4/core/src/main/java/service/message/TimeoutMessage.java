package service.message;

public class TimeoutMessage implements MySerializable {
    private long token;

    public TimeoutMessage(long token) {
        this.token = token;
    }

    public long getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "TimeoutMessage{" +
                "token=" + token +
                '}';
    }
}
