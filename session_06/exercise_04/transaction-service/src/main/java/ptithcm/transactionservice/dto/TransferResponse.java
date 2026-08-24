package ptithcm.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransferResponse {
    private String status;
    private String message;
}
