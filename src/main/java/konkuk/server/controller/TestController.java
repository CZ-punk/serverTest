package konkuk.server.controller;

import konkuk.server.domain.Email;
import konkuk.server.domain.Member;
import konkuk.server.repository.EmailRepository;
import konkuk.server.service.EmailService;
import konkuk.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final MemberService memberService;
    private final EmailService emailService;


    // Member 의 모든 Email 조회
    @GetMapping("/members/{memberId}/emaillist")
    public List<Email> findEmailList(@PathVariable("memberId") Long memberId) {

        Member findMember = memberService.findOne(memberId);
        return memberService.findEmailList(findMember);

    }

    // Member 의 특정 Email 조회
    @RequestMapping("/members/{memberId}/emaillist/{emailId}")
    public Email findEmail(@PathVariable("memberId") Long memberId, @PathVariable("emailId") Long emailId) {

        Member findMember = memberService.findOne(memberId);

        for (Email email : findMember.getEmailList()) {
            if (email.getId().equals(emailId)) {
                return email;
            }
        }

        throw new IllegalArgumentException("해당 Email 은 Member 에 속해있지 않습니다.");
    }


    // 조회는 {고유id}/query parameter 로 조회하자.


}
