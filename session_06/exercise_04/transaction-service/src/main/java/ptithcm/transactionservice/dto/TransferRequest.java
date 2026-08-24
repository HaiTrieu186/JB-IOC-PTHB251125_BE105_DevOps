package ptithcm.transactionservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    // id tài khoản nguồn (account-service quản lý theo id)
    private Long fromAccountId;
    // id tài khoản đích
    private Long toAccountId;
    private Double amount;
}
