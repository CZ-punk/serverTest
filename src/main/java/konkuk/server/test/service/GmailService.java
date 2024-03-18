package konkuk.server.test.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.model.*;
import konkuk.server.test.info.MessageForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.google.api.services.gmail.Gmail;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Slf4j
@RequiredArgsConstructor
public class GmailService {

    private static String userId = "me";
    private final WebClient webClient;

    public void login(String accessToken) {
        String emails = this.webClient
                .get()
                .uri("https://gmail.googleapis.com/gmail/v1/users/me/messages")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return;
    }

    public static Gmail initializeGmailService(String accessToken) throws IOException {

        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        Gmail gmail = new Gmail.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName("Your Application Name")
                .build();
        return gmail;
    }


    /**
     * 라벨을 통해서 필터링하여 받은 편지 또는 스팸 등을 따로 가져올 수 있다.
     */
    public static List<MessageForm> fetchInboxAll(String accessToken) throws IOException {

        Gmail gmail = initializeGmailService(accessToken);
        List<MessageForm> messages = new ArrayList<>();

        // 사용자의 메일 목록에서 메일 ID 가져오기
        // 라벨 여기에 추가
        ListMessagesResponse response = gmail.users().messages().list("me").setLabelIds(List.of("INBOX")).execute();

        for (Message message : response.getMessages()) {
            Message msgDetail = gmail.users().messages().get("me", message.getId()).setFormat("metadata").execute();

            String from = "";
            String subject = "";
            LocalDateTime receivedTime = Instant.ofEpochMilli(msgDetail.getInternalDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();

            // 메타데이터에서 "From"과 "Subject" 정보 찾기
            for (MessagePartHeader header : msgDetail.getPayload().getHeaders()) {
                if ("From".equals(header.getName())) {
                    from = header.getValue();
                } else if ("Subject".equals(header.getName())) {
                    subject = header.getValue();
                }
            }

            messages.add(new MessageForm(message.getId(), from, subject, receivedTime));
        }

        return messages;
    }


    /**
     *  기본 받은 편지함
     */
    public static List<MessageForm> fetchInboxBasicMessage(String accessToken) throws IOException {

        Gmail gmail = initializeGmailService(accessToken);

        // Inbox label 가진 메시지들 조회
        ListMessagesResponse response = gmail.users().messages().list(userId)
                .setLabelIds(List.of("INBOX"))
                .setQ("-category:promotions -category:social")
                .setFields("messages(id,threadId), nextPageToken")
                .execute();

        List<MessageForm> filteredMessage = Collections.synchronizedList(new ArrayList<>());
        response.getMessages().parallelStream().forEach(message -> {

            try {
                Message msgDetail = gmail.users().messages().get(userId, message.getId())
                        .setFormat("metadata")
                        .setFields("id, payload(headers), internalDate")
                        .execute();

                String from = "";
                String subject = "";
                LocalDateTime receivedTime = null;

                for(var header : msgDetail.getPayload().getHeaders()) {

                    if (header.getName().equals("From")) {
                        from = header.getValue();
                    } else if (header.getName().equals("Subject")) {
                        subject = header.getValue();
                    } else if (header.getName().equals("Date")) {
                        receivedTime = Instant.ofEpochMilli(msgDetail.getInternalDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                    }
                }

                filteredMessage.add(new MessageForm(message.getId(), from, subject, receivedTime));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        });

        return filteredMessage;
    }




    // label 종류 확인용 코드
    public static List<Label> listLabels(String accessToken) throws IOException {
        Gmail gmail = initializeGmailService(accessToken);
        ListLabelsResponse response = gmail.users().labels().list(userId).execute();
        return response.getLabels();
    }


//    private static Gmail createGmail(String accessToken) throws GeneralSecurityException, IOException {
//
//        // GoogleCredentials를 사용하여 OAuth2 인증 정보 설정
//        Credential credential = new GoogleCredential().setAccessToken(accessToken);
//
//        // Gmail 서비스 객체 생성
//        Gmail service = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
//                .setApplicationName("Your Application Name")
//                .build();
//
//        return service;
//    }



//    public static Message getMessageAllContents(String accessToken) throws IOException, GeneralSecurityException {
//
//        Gmail gmail = createGmail(accessToken);
//        List<Message> messageList = getMessageList(accessToken);
//        List<String> messageIdList = new ArrayList<>();
//
//        if (messageList == null || messageList.isEmpty()) {
//            System.out.println("no messageList found.");
//        } else {
//            for (Message message : messageList) {
//                messageIdList.add(message.getId());
//            }
//        }
//
//        return gmail.users().messages().get(userId, messageIdList.getFirst()).setFormat("full").execute();
//    }


//    public static List<String> extractMessageIds(Gmail gmail) throws IOException {
//
//        /**
//         * 특정 message ID 를 정해야 하므로 수정 필요
//         */
//
//        ListMessagesResponse response = gmail.users().messages().list(userId).execute();
//        List<MessageForm> messages = response.getMessages();
//        List<String> messageIds = new ArrayList<>();
//
//        if (messages != null) {
//            for (MessageForm message : messages) {
//                // 메시지의 ID를 새로운 리스트에 추가합니다.
//                messageIds.add(message.getId());
//            }
//        }
//
//        return messageIds;
//    }





    // mailId, sender, title 정보를 담는 mail Info 객체를 생성하고 초기화한다.
    // 이거를 메일리스트를 보여주는 프론트 부분에 보낸다.
    // 특정 메일을 터치시 프론트에서는 id 정보만 백으로 보내면 된다.







    /**
     * 성능 안 좋은 전부 다 가져오는 케이스 ( 9초 )
     */
//
//    public static List<MessageForm> getMailsPayload(String accessToken) throws IOException, GeneralSecurityException {
//        Gmail gmail = createGmail(accessToken);
//
//        ListMessagesResponse messagesResponse = gmail.users().messages().list(userId).execute();
//        List<MessageForm> messageList = messagesResponse.getMessages();
//
//        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        List<Future<MessageForm>> futures = new ArrayList<>();
//
//        for (MessageForm message : messageList) {
//            Callable<MessageForm> callableTask = () -> {
//                return gmail.users().messages().get(userId, message.getId()).setFormat("full").execute();
//            };
//            futures.add(executor.submit(callableTask));
//        }
//
//        List<MessageForm> messageListPayload = futures.stream().map(future -> {
//            try {
//                return future.get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }).collect(Collectors.toList());
//
//        executor.shutdown();
//
//        return messageListPayload;
//    }


    /**
     *  성능 ㅈ망한 한번에 전부 가져오는 케이스 ( 50초 )
     */

//    public static List<Message> getMailsPayload(String accessToken) throws IOException, GeneralSecurityException {
//
//        Gmail gmail = createGmail(accessToken);
//
//        List<String> messageIds = new ArrayList<>();
//
//
//        ListMessagesResponse messagesResponse = gmail.users().messages().list(userId).execute();
//        List<Message> messageList = messagesResponse.getMessages();
//        List<Message> messageListPayload = new ArrayList<>();
//
//        for (Message message : messageList) {
//            messageIds.add(message.getId());
//        }
//
//        for (String messageId : messageIds) {
//            Message message = gmail.users().messages().get(userId, messageId).setFormat("full").execute();
//            messageListPayload.add(message);
//        }
//
//
//        if (messageListPayload == null || messageListPayload.isEmpty()) {
//            System.out.println("No messageList found.");
//        }
//
//        return messageListPayload;
//    }


}
