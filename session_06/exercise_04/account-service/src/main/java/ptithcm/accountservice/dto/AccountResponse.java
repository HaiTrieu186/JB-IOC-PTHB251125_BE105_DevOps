package ptithcm.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ptithcm.accountservice.entity.Account;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private String ownerName;
    private Double balance;
    private LocalDateTime createdAt;

    public static AccountResponse fromEntity(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getOwnerName(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }
}
