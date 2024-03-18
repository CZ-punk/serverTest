package konkuk.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import konkuk.server.domain.LoginForm;
import konkuk.server.domain.Member;
import konkuk.server.domain.SessionConst;
import konkuk.server.repository.MemberRepository;
import konkuk.server.service.LoginService;
import konkuk.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getLoginPw());
        log.info("login? {}", loginMember);

        if (loginMember == null) {
            throw new IllegalStateException("ID 또는 PASSWORD를 다시 확인해주십시오.");
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_SESSION, loginMember);

        Map<String, Object> body = getRespEntityBody("성공적으로 로그인이 완료되었습니다.");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        Map<String, Object> body = getRespEntityBody("성공적으로 로그아웃이 완료되었습니다.");

        return new ResponseEntity<>(body, HttpStatus.OK);

    }

    private static Map<String, Object> getRespEntityBody(String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        return body;
    }


}
