package ao.com.techAngolar.controller;

import ao.com.techAngolar.dto.TransactionDTO;
import ao.com.techAngolar.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/renda/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> save( @Valid @RequestBody TransactionDTO transactionDTO) {

            transactionDTO = transactionService.save(transactionDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(transactionDTO);
    }

    @GetMapping("/{transaction_id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable Integer transaction_id) {

        TransactionDTO transactionDTO = transactionService.findById(transaction_id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionDTO);
    }
}
