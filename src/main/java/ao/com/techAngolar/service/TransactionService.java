package ao.com.techAngolar.service;

import ao.com.techAngolar.dto.TransactionDTO;
import ao.com.techAngolar.entity.Transaction;

public interface TransactionService {

    TransactionDTO save(TransactionDTO transactionDTO);
    TransactionDTO findById(Integer transaction_id);
    TransactionDTO update(TransactionDTO transactionDTO, Integer transaction_id);
}
