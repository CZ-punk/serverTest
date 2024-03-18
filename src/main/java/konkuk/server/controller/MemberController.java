package konkuk.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import konkuk.server.domain.Member;
import konkuk.server.repository.MemberRepository;
import konkuk.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.remote.server.HttpStatusHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {


    private final MemberService memberService;

//    @PostMapping("/join")
//    public ResponseEntity join(@RequestBody Member member) {
//
//
//        Member joinMember = new Member(member.getName(), member.getLoginId(), member.getLoginPw());
//
//        Member join = memberService.join(member);
//
//        log.debug("join {}", join);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("message", "회원가입이 성공적으로 완료되었습니다.");
//        return new ResponseEntity<>(body, HttpStatus.CREATED);
//    }
}
