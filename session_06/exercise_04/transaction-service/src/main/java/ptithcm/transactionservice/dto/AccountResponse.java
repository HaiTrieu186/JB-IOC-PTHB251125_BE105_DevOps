package ptithcm.transactionservice.dto;

import lombok.Getter;
import lombok.Setter;

// DTO map lại response từ account-service (GET /api/accounts/{id})
@Getter
@Setter
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private String ownerName;
    private Double balance;
}
