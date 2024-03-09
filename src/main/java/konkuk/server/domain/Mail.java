package konkuk.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "MAIL")
public class Mail {

    @Id @GeneratedValue
    @Column(name = "MAIL_ID")
    private Long id;

    @Column(name = "SENDER_NAME", length = 20)
    private String senderName;
    @Column(name = "SENDER_ADDRESS", length = 20)
    private String senderAddress;
    @Column(name = "RECEIVER_NAME", length = 20)
    private String receiverName;
    @Column(name = "RECEIVER_ADDRESS", length = 20)
    private String receiverAddress;
    @Column(name = "TITLE", length = 30)
    private String title;
    @Column(name = "CONTENTS", length = 3000)
    private String contents;
    @Column(name = "MEDIA_FILE_URL")
    private String mediaFileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMAIL_ID")
    @JsonBackReference
    private Email email;

    public Mail() {
    }

    public Mail(String senderName, String senderAddress, String receiverName, String receiverAddress, String title, String contents) {
        this.senderName = senderName;
        this.senderAddress = senderAddress;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    private void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    private void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    private void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    private void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    private void setContents(String contents) {
        this.contents = contents;
    }

    public Email getEmail() {
        return email;
    }

    public void addEmail(Email email) {
        this.email = email;
    }

    public String getMediaFileUrl() {
        return mediaFileUrl;
    }

    public void setMediaFileUrl(String mediaFileUrl) {
        this.mediaFileUrl = mediaFileUrl;
    }
}
