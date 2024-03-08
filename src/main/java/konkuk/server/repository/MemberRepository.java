package konkuk.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import konkuk.server.domain.Email;
import konkuk.server.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }



    public List<Member> findLoginId(Long id) {
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }
    
    
    // 특정 ID 를 통해 특정 Member 를 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }


    // 모든 Member 를 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    
    // 특정 Member 를 통해 EmailList 를 조회
    public List<Email> findEmailList(Member member) {
        return member.getEmailList();
    }



}
