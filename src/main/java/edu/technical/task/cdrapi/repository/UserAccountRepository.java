package edu.technical.task.cdrapi.repository;

import edu.technical.task.cdrapi.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
