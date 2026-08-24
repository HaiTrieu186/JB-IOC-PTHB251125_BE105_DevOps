package ptithcm.accountservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.accountservice.dto.AccountResponse;
import ptithcm.accountservice.dto.CreateAccountRequest;
import ptithcm.accountservice.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // Tạo tài khoản mới
    // POST http://localhost:8080/api/accounts
    // Body JSON: { "accountNumber": "0001", "ownerName": "Nguyen Van A", "balance": 1000000 }
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Lấy thông tin 1 tài khoản theo id
    // GET http://localhost:8080/api/accounts/1
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    // Lấy danh sách tất cả tài khoản
    // GET http://localhost:8080/api/accounts
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
