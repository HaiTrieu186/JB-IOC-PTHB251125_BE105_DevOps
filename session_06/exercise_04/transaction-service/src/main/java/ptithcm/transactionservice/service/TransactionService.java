package ptithcm.transactionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ptithcm.transactionservice.client.AccountServiceClient;
import ptithcm.transactionservice.dto.AccountResponse;
import ptithcm.transactionservice.dto.TransferRequest;
import ptithcm.transactionservice.dto.TransferResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountServiceClient accountServiceClient;

    public TransferResponse transfer(TransferRequest request) {
        log.info("Nhận yêu cầu chuyển tiền: {} -> {}, số tiền: {}",
                request.getFromAccountId(), request.getToAccountId(), request.getAmount());

        // Bước 1: Gọi sang account-service kiểm tra tài khoản nguồn có tồn tại không
        AccountResponse fromAccount = accountServiceClient.getAccountById(request.getFromAccountId());

        if (fromAccount == null) {
            log.warn("Từ chối giao dịch: tài khoản nguồn id={} không tồn tại", request.getFromAccountId());
            return new TransferResponse("FAILED", "Tài khoản nguồn không tồn tại");
        }

        // Bước 2 (đơn giản hoá cho demo): kiểm tra số dư đủ hay không
        if (fromAccount.getBalance() < request.getAmount()) {
            log.warn("Từ chối giao dịch: số dư không đủ. balance={}, amount={}",
                    fromAccount.getBalance(), request.getAmount());
            return new TransferResponse("FAILED", "Số dư không đủ để thực hiện giao dịch");
        }

        // Bước 3: Xử lý chuyển tiền (demo - chưa cần cập nhật DB thật)
        log.info("Xác nhận tài khoản nguồn hợp lệ (owner={}). Tiến hành xử lý chuyển tiền...",
                fromAccount.getOwnerName());

        return new TransferResponse("SUCCESS",
                "Chuyển tiền thành công từ tài khoản " + fromAccount.getAccountNumber());
    }
}
