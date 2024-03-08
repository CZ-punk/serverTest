package konkuk.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import konkuk.server.domain.Email;
import konkuk.server.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Email email) {
        em.persist(email);
        return email.getId();
    }

    // ID 를 통해 Email 조회
    public Email findOne(Long id) {
        return em.find(Email.class, id);
    }

    // 모든 Email 조회
    public List<Email> findAll() {
        return em.createQuery("select e from Email e", Email.class)
                .getResultList();
    }

    // Member ID 를 통해 Member 에 등록된 Email List 를 조회
    public List<Email> findEmailListByMemberId(Long memberId) {
        String jpql = "select e from Email e where e.member.id = :memberId";
        return em.createQuery(jpql, Email.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    // Member.getId =  Email.

}

