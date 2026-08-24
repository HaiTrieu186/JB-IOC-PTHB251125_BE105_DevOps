package ptithcm.accountservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {
    private String accountNumber;
    private String ownerName;
    private Double balance;
}
