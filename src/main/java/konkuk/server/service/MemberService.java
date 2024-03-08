package konkuk.server.service;

import konkuk.server.domain.Email;
import konkuk.server.domain.Member;
import konkuk.server.repository.EmailRepository;
import konkuk.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입

        추후 추가 예정
     */

    public Long join(Member member) {
        validateDuplicateLoginId(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 이미 존재하는 Login ID 가 있을 경우 회원가입 못하게 Exception
    private void validateDuplicateLoginId(Member member) {
        List<Member> findMember = memberRepository.findLoginId(member.getId());
        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원ID 입니다.");
        }
    }

    // 특정 ID 를 통해 특정 Member 를 조회
    @Transactional(readOnly = true)
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    //  모든 Member 를 조회
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 특정 Member 를 통해 EmailList 를 출력
    @Transactional(readOnly = true)
    public List<Email> findEmailList(Member member) {
        return memberRepository.findEmailList(member);
    }

    // Member 를 찾아 해당 Member 에 연동할 email 을 등록하고 해당 Email 의 ID 반환
    public Long registerEmail(Long memberId, Email email) {
        Member findMember = memberRepository.findOne(memberId);

        if (findMember == null) {
            throw new IllegalArgumentException("해당 ID의 Member 가 존재하지 않습니다. ID: " + memberId);
        }

        findMember.addEmail(email);
        memberRepository.save(findMember);

        return email.getId();
    }


    /**
     *  추후 비밀번호 로직 자리
     *
     */


}
