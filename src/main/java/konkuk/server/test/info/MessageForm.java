package konkuk.server.test.info;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageForm {

    private String id;
    private String from;
    private String subject;
    private LocalDateTime receivedTime;


    public MessageForm(String id, String from, String subject, LocalDateTime receivedTime) {
        this.id = id;
        this.from = from;
        this.subject = subject;
        this.receivedTime = receivedTime;
    }
}
