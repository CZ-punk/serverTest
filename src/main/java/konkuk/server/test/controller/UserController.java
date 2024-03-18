package konkuk.server.test.controller;


import com.google.api.services.gmail.model.Message;
import konkuk.server.test.service.GmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/")
    public String home() {
        return "index";
    }


//    @GetMapping("/login")
//    public String getloginPage() {
//        return "login";
//    }



    @ResponseBody
    @GetMapping("/login/user")
    public String getUserInfo(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {


        return "Access Token: " + authorizedClient.getAccessToken().getTokenValue();
    }

//    @ResponseBody
//    @GetMapping("/login/user/mails")
//    public List<Message> listMails(@AuthenticationPrincipal OAuth2User principal) throws IOException, GeneralSecurityException {
//        // OAuth2 제공자의 registrationId. 예: google, github 등
//        // 이 값은 실제 애플리케이션의 보안 설정에 정의된 클라이언트 ID와 일치해야 합니다.
//        // 액세스 토큰을 얻기 위해 OAuth2AuthorizedClientService 사용
//
//        OAuth2AuthorizedClient googleOauth2Client = createGoogleOauth2Client(principal);
//
//        log.info("Access Token {}", googleOauth2Client.getAccessToken().getTokenValue());
//
//        // 액세스 토큰을 사용하여 메일 목록 가져오기
//        return gmailService.getMessageList(googleOauth2Client.getAccessToken().getTokenValue());
//    }

//    @ResponseBody
//    @GetMapping("/login/user/mails/mail")
//    public Message getMail(@AuthenticationPrincipal OAuth2User principal) throws IOException, GeneralSecurityException {
//
//        OAuth2AuthorizedClient googleOauth2Client = createGoogleOauth2Client(principal);
//        log.info("Access Token {}", googleOauth2Client.getAccessToken().getTokenValue());
//
//        // 액세스 토큰을 사용하여 메일 목록 가져오기
//        List<Message> messages = gmailService.getMessageList(googleOauth2Client.getAccessToken().getTokenValue());
//
//        Message message = messages.getFirst();
//
//        return message;
//
//    }
//
//    @ResponseBody
//    @GetMapping("/login/user/mails/mail/all")
//    public Message getMailAllContents(@AuthenticationPrincipal OAuth2User principal) throws IOException, GeneralSecurityException {
//
//        OAuth2AuthorizedClient googleOauth2Client = createGoogleOauth2Client(principal);
//
//
//
//        return gmailService.getMessageAllContents(googleOauth2Client.getAccessToken().getTokenValue());
//    }




    public OAuth2AuthorizedClient createGoogleOauth2Client(@AuthenticationPrincipal OAuth2User principal) {
        String registrationId = "google";

        OAuth2AuthorizedClient client = this.authorizedClientService.loadAuthorizedClient(registrationId, principal.getName());

        return client;
    }


}
