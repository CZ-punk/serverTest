package konkuk.server.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import konkuk.server.domain.Email;
import konkuk.server.domain.Mail;
import konkuk.server.domain.Member;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DummyDataInitalizer implements ApplicationRunner {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

//        for (int i = 1; i <= 10; i++) {
//
//            Member member = new Member("member_" + i, "ID_" + i, "PW_" + i);
//            Email email = new Email("Email_" + i, "Email_Address_" + i);
//            email.addMember(member);
//            Mail mail = new Mail("Sender_" + i, "Sender_Address_" + i, member.getName(), email.getEmailAddress(), "Title_" + i, "Content_" + i);
//
//            member.addEmail(email);
//            email.addMail(mail);
//
//            em.persist(member);
//        }
        Member member = new Member("최현", "ch", "19");


        Email email = new Email("Main_Google", "cnhhw0408@gmail.com", member);
        Email email2 = new Email("Main_Naver", "cnhhw0408@naver.com", member);

        Mail mail = new Mail("최현민", "chm1007@naver.com", email.getName(), email.getEmailAddress(), "제목", "내용(추후 multi-form 으로 수정할 예정이다.");
        Mail mail2 =  new Mail("최지현", "cjh1126@naver.com", email.getName(), email.getEmailAddress(), "제목2", "두번 째 메일 잘 들어갔냐?");
        Mail mail3 =  new Mail("스팸", "spam@spam.com", email2.getName(), email2.getEmailAddress(), "스팸", "시벨롬" );
        Mail mail4 =  new Mail("스팸2", "spam2@spam.com", email2.getName(), email2.getEmailAddress(), "스팸2", "시벨롬2" );


        member.addEmail(email);
        member.addEmail(email2);
        email.addMail(mail);
        email.addMail(mail2);
        email2.addMail(mail3);
        email2.addMail(mail4);


        em.persist(member);

        // Member, Email, Mail Entity about Dummy Data 10
    }
}
