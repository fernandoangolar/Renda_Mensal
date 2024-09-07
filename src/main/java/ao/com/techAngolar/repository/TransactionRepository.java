package ao.com.techAngolar.repository;

import ao.com.techAngolar.entity.Category;
import ao.com.techAngolar.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    boolean existsByValorAndDateAndCategoryAndType(Double valor, LocalDate date, Category categoria, String type);

    List<Transaction> findByCategoryName(String categoryName);
    List<Transaction> findByType(String type);
    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);

//    Filtrar por categoria, tipo e data

    @Query("SELECT t FROM Transaction t WHERE "
            + "(:categoryName IS NULL OR t.category.name = :categoryName) "
            + "AND (:type IS NULL OR t.type = :type) "
            + "AND (:startDate IS NULL OR t.date >= :startDate) "
            + "AND (:endDate IS NULL OR t.date <= :endDate)")
    List<Transaction> filterTransctions(
            @Param("categoryName") String categoryName,
            @Param("type") String type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
