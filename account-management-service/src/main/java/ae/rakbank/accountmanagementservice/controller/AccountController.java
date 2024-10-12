package ae.rakbank.accountmanagementservice.controller;

import ae.rakbank.accountmanagementservice.dto.AccountDTO;
import ae.rakbank.accountmanagementservice.dto.ResponseObject;
import ae.rakbank.accountmanagementservice.mapper.AccountMapper;
import ae.rakbank.accountmanagementservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO dto) {
        var account = accountService.createAccount(dto);
        return ResponseEntity.ok(AccountMapper.toAccountDto(account));
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts(
            @RequestParam("pageNo") int pageNo,
            @RequestParam("pageSize") int pageSize
    ) {
        List<AccountDTO> accounts = accountService.getAccounts(pageNo, pageSize);
        return ResponseEntity.ok(accounts);

    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getAccount(@PathVariable("id") Long id) {
        AccountDTO account = accountService.getAccount(id);
        return ResponseEntity.ok(account);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<String> updateAccount(@PathVariable("id") Long id,
                                                @RequestBody AccountDTO dto) {
        accountService.updateAccount(id, dto);
        return new ResponseEntity<>("Account successfully updated.", HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "{id}/email")
    public ResponseEntity<String> getUserEmail(@PathVariable("id") Long id) {
        return new ResponseEntity<>(accountService.getUserEmail(id), HttpStatus.OK);
    }
}
