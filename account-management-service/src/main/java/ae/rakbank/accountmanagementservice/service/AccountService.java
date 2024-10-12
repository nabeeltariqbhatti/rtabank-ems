package ae.rakbank.accountmanagementservice.service;

import ae.rakbank.accountmanagementservice.dto.AccountDTO;
import ae.rakbank.accountmanagementservice.model.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    Account createAccount(AccountDTO dto);
    void updateAccount(Long id, AccountDTO dto);
    AccountDTO getAccount(Long id);
    Page<AccountDTO> getAccounts(int pageNo, int pageSize);
    String getUserEmail (Long id);

    void deleteAccount(Long id);

    boolean exists(String email);
}

