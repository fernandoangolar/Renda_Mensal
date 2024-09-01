package ao.com.techAngolar.repository;

import ao.com.techAngolar.entity.Category;
import ao.com.techAngolar.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    boolean existsByValorAndDateAndCategoryAndType(Double valor, Date date, Category categoria, String type);
}
