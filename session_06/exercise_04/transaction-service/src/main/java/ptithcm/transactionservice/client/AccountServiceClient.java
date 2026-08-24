package ptithcm.transactionservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ptithcm.transactionservice.dto.AccountResponse;

@Slf4j
@Component
public class AccountServiceClient {

    private final RestTemplate restTemplate;

    // Lấy từ application.yml: http://account-service:8080
    // KHÔNG dùng localhost / IP tĩnh -> dùng tên service trong docker-compose
    @Value("${account-service.base-url}")
    private String accountServiceBaseUrl;

    public AccountServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Gọi sang account-service để kiểm tra tài khoản có tồn tại hay không.
     * Trả về AccountResponse nếu tồn tại, null nếu không tìm thấy (404).
     */
    public AccountResponse getAccountById(Long accountId) {
        String url = accountServiceBaseUrl + "/api/accounts/" + accountId;
        log.info(">>> [transaction-service] Gọi HTTP nội bộ sang account-service: GET {}", url);

        try {
            AccountResponse response = restTemplate.getForObject(url, AccountResponse.class);
            log.info("<<< [transaction-service] Nhận phản hồi từ account-service: {}", response);
            return response;
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("<<< [transaction-service] account-service trả về 404: tài khoản id={} không tồn tại", accountId);
            return null;
        }
    }
}
