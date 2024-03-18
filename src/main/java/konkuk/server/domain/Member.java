package konkuk.server.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "MEMBER_NAME", length = 20)
    private String name;
    @Column(name = "LOGIN_ID", length = 20)
    private String loginId;
    @Column(name = "LOGIN_PW", length = 20)
    private String loginPw;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Email> emailList = new ArrayList<>();

    @Column(name = "FIRST_EMAIL")
    private String email;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    public Provider getProvider() {
        return provider;
    }

    public Member() {
    }

    public Member(String name, String loginId, String loginPw) {
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.email = loginId + "@gmail.com";
    }

    public Member(String name, String loginId, String loginPw, Provider provider) {
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.provider = provider;
        this.email = loginId + "@gmail.com";
    }

    public void addEmail(Email email) {
        if (this.emailList.size() >= 5) {
            throw new IllegalStateException("Cannot add more than 5 emails");
        }
        this.emailList.add(email);
        email.addMember(this);
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

    public String getLoginId() {
        return loginId;
    }

    private void setMemberId(String memberId) {
        this.loginId = memberId;
    }

    public String getLoginPw() {
        return loginPw;
    }

    private void setMemberPw(String memberPw) {
        this.loginPw = memberPw;
    }

    public List<Email> getEmailList() {
        return emailList;
    }

    private void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    public String getEmail() {
        return email;
    }

}
