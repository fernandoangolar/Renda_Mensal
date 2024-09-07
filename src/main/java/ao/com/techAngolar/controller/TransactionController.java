package ao.com.techAngolar.controller;

import ao.com.techAngolar.dto.TransactionDTO;
import ao.com.techAngolar.entity.Transaction;
import ao.com.techAngolar.service.TransactionService;
import ao.com.techAngolar.service.impl.TransactionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/renda/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @PostMapping
    public ResponseEntity<TransactionDTO> save( @Valid @RequestBody TransactionDTO transactionDTO) {

            transactionDTO = transactionService.save(transactionDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(transactionDTO);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<TransactionDTO>> findAllSorted(
            @RequestParam String sortBy,  // Campo de ordenação (ex.: date, valor, category)
            @RequestParam String direction // Direção de ordenação (as ou desc)
    ) {

        List<TransactionDTO> allTransactionsSorted = transactionServiceImpl.findAllTransactionsSorted(sortBy, direction);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allTransactionsSorted);
    }

    @GetMapping("/{transaction_id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable Integer transaction_id) {

        TransactionDTO transactionDTO = transactionService.findById(transaction_id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTO);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TransactionDTO>> filterTransctions(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<TransactionDTO> transactionDTO = transactionServiceImpl.filterTransactions(categoryName, type, startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTO);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<TransactionDTO>> filterByCategoryName(@PathVariable String categoryName) {

        List<TransactionDTO> transactionDTOS = transactionServiceImpl.findByCategoryName(categoryName);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTOS);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<TransactionDTO>> filterByType(@PathVariable String type) {

        List<TransactionDTO> transactionDTOS = transactionServiceImpl.findByType(type);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTOS);
    }

    @GetMapping("/date")
    public ResponseEntity<List<TransactionDTO>> filterByDateBetween(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<TransactionDTO> transactionDTOS = transactionServiceImpl.findByDateBetween(startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTOS);
    }

    @GetMapping("/filter/minValue")
    public ResponseEntity<List<TransactionDTO>> filterByMinValue(@RequestParam Double minValue) {

        List<TransactionDTO> transactionDTOS = transactionServiceImpl.filterTransctionsByMinValue(minValue);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTOS);
    }

    @GetMapping("/filter/maxValue")
    public ResponseEntity<List<TransactionDTO>> filterByMaxValue(@RequestParam Double maxValue) {

        List<TransactionDTO> transactionDTOS = transactionServiceImpl.filterTransctionsByMaxValue(maxValue);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTOS);
    }

    @GetMapping("/filter/valueRange")
    public ResponseEntity<List<TransactionDTO>> filterByValueRange(
            @RequestParam Double minValue,
            @RequestParam Double maxValue
    ) {

        List<TransactionDTO> transactionDTOS = transactionServiceImpl.filterTransctionsByValueRange(minValue, maxValue);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTOS);
    }

    @PutMapping("/{transaction_id}")
    public ResponseEntity<TransactionDTO> update(@PathVariable Integer transaction_id, @Valid @RequestBody TransactionDTO transactionDTO) {

        transactionDTO = transactionService.update(transactionDTO, transaction_id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTO);
    }
}
