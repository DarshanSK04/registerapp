package com.example.registerapp.Services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailSenderService {

    @Value("${RESEND_API_KEY}")
    private String resendApiKey;

    private final String RESEND_URL = "https://api.resend.com/emails";

    public void sendSimpleMEssage(String to, String subject, String text) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(resendApiKey);

            Map<String, Object> body = Map.of(
                "from", "Darshan <onboarding@resend.dev>",
                "to", new String[]{to},
                "subject", subject,
                "text", text
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(RESEND_URL, request, String.class);

            System.out.println("✅ Resend API Response: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to send email: " + e.getMessage());
        }
    }
}
