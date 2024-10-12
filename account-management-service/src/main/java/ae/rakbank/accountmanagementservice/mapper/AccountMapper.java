package ae.rakbank.accountmanagementservice.mapper;

import ae.rakbank.accountmanagementservice.dto.AccountDTO;
import ae.rakbank.accountmanagementservice.model.Account;

public class AccountMapper {


    public static AccountDTO toAccountDto(Account account) {
        if (account == null) {
            return null;
        }
        return AccountDTO.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .phone(account.getPhone())
                .birthDate(account.getBirthDate())
                .registeredAt(account.getRegisteredAt())
                .build();
    }


    public static Account toAccountEntity(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        Account account = new Account();
        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setEmail(accountDTO.getEmail());
        account.setPhone(accountDTO.getPhone());
        account.setBirthDate(accountDTO.getBirthDate());
        account.setRegisteredAt(accountDTO.getRegisteredAt());
        return account;
    }
}
