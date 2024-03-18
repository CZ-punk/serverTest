package konkuk.server.test.info;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class MailInfo {
    private final String id;
    private String sender;
    private String title;

    public MailInfo(String id) {
        this.id = id;
    }

    public MailInfo(String id, String sender, String title) {
        this.id = id;
        this.sender = sender;
        this.title = title;
    }
}
