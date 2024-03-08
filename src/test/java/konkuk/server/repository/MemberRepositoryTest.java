package konkuk.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import konkuk.server.domain.Email;
import konkuk.server.domain.Mail;
import konkuk.server.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository userRepository;

    @Test
    @Transactional
    @Rollback()
    public void testUser() throws Exception {

        for (int i = 1; i <= 10; i++) {

            Member member = new Member("member_" + i, "ID_" + i, "PW_" + i);
            Email email = new Email("Email_" + i, "Email_Address_" + i, member);
            Mail mail = new Mail("Sender_" + i, "Sender_Address_" + i, member.getName(), email.getEmailAddress(), "Title_" + i, "Content_" + i);

            member.addEmail(email);
            email.addMail(mail);

            em.persist(member);
        }



    }

}