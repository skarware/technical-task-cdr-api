package edu.technical.task.cdrapi.service;

import edu.technical.task.cdrapi.model.UserAccount;
import edu.technical.task.cdrapi.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.saveAndFlush(userAccount);
    }
}
