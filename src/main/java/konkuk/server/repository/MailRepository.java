package konkuk.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MailRepository {

    @PersistenceContext
    private EntityManager em;

}
