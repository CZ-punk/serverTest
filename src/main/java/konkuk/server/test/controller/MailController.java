package konkuk.server.test.controller;

import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.Message;
import konkuk.server.test.info.MessageForm;
import konkuk.server.test.service.GmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class MailController {


    private final OAuth2AuthorizedClientService authorizedClientService;

    public MailController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }


    @GetMapping("/checkedlabel")
        public List<Label> checkedLabels(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) throws IOException {

            return GmailService.listLabels(authorizedClient.getAccessToken().getTokenValue());
        }



    //    @GetMapping("/mails")
    //    public List<MailInfo> mailListForm(@AuthenticationPrincipal OAuth2User principal) throws IOException, GeneralSecurityException {
    //        OAuth2AuthorizedClient googleOauth2Client = createGoogleOauth2Client(principal);
    //
    //        return gmailService.extractMailInfoList(googleOauth2Client.getAccessToken().getTokenValue());
    //    }

        @GetMapping("/basic-inbox")
        public List<MessageForm> getMails(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) throws IOException {


            return GmailService.fetchInboxBasicMessage(authorizedClient.getAccessToken().getTokenValue());
        }






//        public OAuth2AuthorizedClient createGoogleOauth2Client(@AuthenticationPrincipal OAuth2User principal) {
//            String registrationId = "google";
//
//            OAuth2AuthorizedClient client = this.authorizedClientService.loadAuthorizedClient(registrationId, principal.getName());
//
//            return client;
//        }

}
