package konkuk.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import konkuk.server.domain.Email;
import konkuk.server.domain.Member;
import konkuk.server.domain.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }


    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }


    // 특정 ID 를 통해 특정 Member 를 조회
    @Transactional(readOnly = true)
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }


    // 모든 Member 를 조회
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 특정 Member 를 통해 EmailList 를 조회
    @Transactional(readOnly = true)
    public List<Email> findEmailList(Member member) {
        return member.getEmailList();
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmailAndProvider(String email, Provider provider) {
        String query = "select m from Member m where m.email = :email and m.provider = :provider";

        List<Member> findMember = em.createQuery(query, Member.class)
                .setParameter("email", email)
                .setParameter("provider", provider)
                .getResultList();

        if (findMember.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(findMember.getFirst());
    }

}
