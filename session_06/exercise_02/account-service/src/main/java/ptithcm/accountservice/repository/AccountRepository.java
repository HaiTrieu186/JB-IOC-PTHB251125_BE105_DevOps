package ptithcm.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ptithcm.accountservice.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
