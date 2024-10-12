package ae.rakbank.accountmanagementservice.service.impl;

import ae.rakbank.accountmanagementservice.dto.AccountDTO;
import ae.rakbank.accountmanagementservice.exception.AccountNotFoundException;
import ae.rakbank.accountmanagementservice.mapper.AccountMapper;
import ae.rakbank.accountmanagementservice.model.Account;
import ae.rakbank.accountmanagementservice.repository.AccountRepository;
import ae.rakbank.accountmanagementservice.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
class AccountServiceImpl implements AccountService {

    private static final String CACHE_NAME = "accounts";
    private static final String CACHE_ERR = "Cache not found.";
    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(AccountDTO dto) {
        log.info("Create account: " + dto);
        Account account = AccountMapper.toAccountEntity(dto);

        account = accountRepository.saveAndFlush(account);

        log.info("Created account with ID " + account.getId());
        return account;
    }

    @Override
    public void updateAccount(Long id, AccountDTO dto) {


        try {
            log.info("Update account: " + dto);
            Account account = accountRepository.findById(id).orElseThrow( () -> new AccountNotFoundException(id));

            if (dto.getEmail() != null) account.setEmail(dto.getEmail());
            if (dto.getPhone() != null) account.setPhone(dto.getPhone());
            if (dto.getLastName() != null) account.setLastName(dto.getLastName());
            if (dto.getFirstName() != null) account.setFirstName(dto.getFirstName());
            if (dto.getBirthDate() != null) account.setBirthDate(dto.getBirthDate());

            accountRepository.save(account);
            log.info("Updated account with ID " + id);
        } catch (Exception e) {
            log.error("Error serializing to cache", e);
            throw e;
        }

    }
    @Override
    public AccountDTO getAccount(Long id) {

        try{
            Account accFromDB = getAccFromDB(id);
            return AccountMapper.toAccountDto(accFromDB);
        }catch ( Exception e){
            log.error("error while fetching account ", e);
            throw e;
        }


    }


    @Override
    public Page<AccountDTO> getAccounts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Account> page = accountRepository.findAll(pageable);
        List<AccountDTO> content = page.map(AccountMapper::toAccountDto).stream().toList();
        return  new PageImpl<>(content,pageable,page.getTotalElements());
    }

    @Override
    public String getUserEmail(Long id) {
        String email = accountRepository.getReferenceById(id).getEmail();
        log.info("Retrieved user email: " + email);
        return email;
    }

    @Override
    public void deleteAccount(Long id) {
        if(!accountRepository.existsById(id)) throw  new AccountNotFoundException(id);
        accountRepository.deleteById(id);
        log.info("Deleted account with ID " + id);
    }

    @Override
    public boolean exists(String email) {
        return accountRepository.existsAccountByEmail(email);
    }

    private Account getAccFromDB(Long id) {
        return accountRepository.findById(id).orElseThrow(()-> new AccountNotFoundException(id));
    }


}
