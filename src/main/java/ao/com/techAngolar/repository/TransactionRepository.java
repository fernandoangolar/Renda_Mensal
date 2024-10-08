package ao.com.techAngolar.repository;

import ao.com.techAngolar.entity.Category;
import ao.com.techAngolar.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.ls.LSInput;

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

    // Filtrar transações com o valor acima de um determinado valor
//    @Query("SELECT t FROM Transaction t WHERE t.valor > :minValue")
//    List<Transaction> findByMinValue(@Param("minValue") Double minValue);
    List<Transaction> findByValorGreaterThan(Double minValue);

    // Filtrar transações com valor abaixo de um determinado valor
    @Query("SELECT t FROM Transaction t WHERE t.valor < :maxValue")
    List<Transaction> findByMaxValue(@Param("maxValue") Double maxValue);
    List<Transaction> findByValorLessThan(Double maxValue);

    // Filter transaoes com valor entre dois valores
//    @Query("SELECT t FROM Transaction t WHERE t.valor BETWEEN :minValue AND :maxValue")
//    List<Transaction> findByValueRange(@Param("minValue") Double minValue, @Param("maxValue") Double maxValue);
    List<Transaction> findByValorBetween(Double minvalor, Double maxvalor);


    // Método para buscar todas as transações ordenafas por um campo específico
    List<Transaction> findAll(Sort sort);


    // Usando Pageable para suportar paginação e ordenação
//    List<Transaction> findAllPageable(Pageable pageable);

    // Buscar todas as trnasações para um determinado mês e ano
    @Query("SELECT t FROM Transaction t WHERE YEAR(t.date) = :year AND MONTH(t.date) = :month")
    List<Transaction> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
