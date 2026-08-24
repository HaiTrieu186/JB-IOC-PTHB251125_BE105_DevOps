package ptithcm.transactionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.transactionservice.dto.TransferRequest;
import ptithcm.transactionservice.dto.TransferResponse;
import ptithcm.transactionservice.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // Chuyển tiền
    // POST http://localhost:8081/api/transactions/transfer
    // Body JSON: { "fromAccountId": 1, "toAccountId": 2, "amount": 100000 }
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        TransferResponse response = transactionService.transfer(request);
        return ResponseEntity.ok(response);
    }
}
