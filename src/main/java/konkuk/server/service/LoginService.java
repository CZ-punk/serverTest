package konkuk.server.service;

import konkuk.server.domain.Member;
import konkuk.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String loginPw) {

        Optional<Member> findMember = memberRepository.findByLoginId(loginId);

        return findMember.
                filter(member -> member.getLoginPw().equals(loginPw))
                .orElse(null);
    }
}
