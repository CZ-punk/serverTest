package konkuk.server.service;

import konkuk.server.domain.Email;
import konkuk.server.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    public Long save(Email email) {
        return emailRepository.save(email);
    }

    // Id 를 통해 Email 엔티티 조회
    public Email findOne(Long id) {
        return emailRepository.findOne(id);
    }

    // 모든 Email 엔티티 조회
    public List<Email> findAll() {
        return emailRepository.findAll();
    }

    // Member ID 를 통해 Member 에 등록된 Email List 를 조회
    public List<Email> findEmailListByMemberId(Long memberId) {
        return emailRepository.findEmailListByMemberId(memberId);
    }



}
