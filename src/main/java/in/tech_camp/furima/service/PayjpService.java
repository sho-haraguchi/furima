package in.tech_camp.furima.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class PayjpService {

    @Value("${payjp.secret.key}")
    private String secretKey;

    public void charge(Long amount, String token) {
        RestTemplate restTemplate = new RestTemplate();

        // Basic認証ヘッダーの作成（秘密鍵:空文字）
        String auth = secretKey + ":";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // リクエストボディの作成
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("amount", String.valueOf(amount));
        body.add("card", token);
        body.add("currency", "jpy");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // RestTemplateでPAY.JP APIを呼び出し、決済リクエストを送信
        try {
            restTemplate.postForEntity(
                    "https://api.pay.jp/v1/charges",
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new RuntimeException("決済処理に失敗しました: " + e.getMessage(), e);
        }
    }
}