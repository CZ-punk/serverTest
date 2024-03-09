package konkuk.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EMAIL")
public class Email {

    @Id
    @GeneratedValue
    @Column(name = "EMAIL_ID", length = 20)
    private Long id;
    @Column(name = "EMAIL_NAME", length = 20)
    private String name;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private Member member;

    @OneToMany(mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Mail> mailList = new ArrayList<>();

    public Email() {
    }

    public Email(String name, String emailAddress, Member member) {
        this.name = name;
        this.emailAddress = emailAddress;
        addMember(member);
    }



    public String getEmailAddress() {
        return emailAddress;
    }

    public void addMail(Mail mail) {

        mailList.add(mail);
        mail.addEmail(this);

    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Member getMember() {
        return member;
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public List<Mail> getMailList() {
        return mailList;
    }

    private void setMailList(List<Mail> mailList) {
        this.mailList = mailList;
    }
}

