package konkuk.server.test.controller;

import com.google.api.services.gmail.Gmail;
import konkuk.server.test.info.MessageForm;
import konkuk.server.test.service.GmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gmail")
public class GmailController {

    private final GmailService gmailService;

    @GetMapping("/login")
    public ResponseEntity googleOauthLogin(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {
        gmailService.login(authorizedClient.getAccessToken().getTokenValue());
        return ResponseEntity.ok().body("Login successful");
    }

    @GetMapping("/mails/all")
    public List<MessageForm> getInboxAll(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) throws IOException {
        return GmailService.fetchInboxAll(authorizedClient.getAccessToken().getTokenValue());
    }

    @GetMapping("/mails/basic-inbox")
    public List<MessageForm> getInboxBasic(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) throws IOException {
        return GmailService.fetchInboxBasicMessage(authorizedClient.getAccessToken().getTokenValue());
    }


}
