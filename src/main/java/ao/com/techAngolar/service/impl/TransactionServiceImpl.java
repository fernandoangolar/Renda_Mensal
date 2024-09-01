package ao.com.techAngolar.service.impl;

import ao.com.techAngolar.dto.TransactionDTO;
import ao.com.techAngolar.entity.Category;
import ao.com.techAngolar.entity.Transaction;
import ao.com.techAngolar.exception.ResourceEntityNotFoundException;
import ao.com.techAngolar.exception.ResourceInativoExcepton;
import ao.com.techAngolar.exception.ResourceJaExistenteException;
import ao.com.techAngolar.repository.CategoryRepository;
import ao.com.techAngolar.repository.TransactionRepository;
import ao.com.techAngolar.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) {

        Category category = categoryRepository.findById(transactionDTO.getCategory().getId())
                .orElseThrow( () -> new ResourceEntityNotFoundException(
                        String.format("Category com o id %s não foi encontrado", transactionDTO.getCategory().getId())));

        verificarCategoriaAtiva(category);
        verificarTransacaoExistente(transactionDTO, category);

        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        transaction.setCategory(category);


        Transaction saveTransaction = transactionRepository.save(transaction);

        return modelMapper.map(saveTransaction, TransactionDTO.class);
    }

    @Override
    public TransactionDTO findById(Integer transaction_id) {

        Transaction transaction = transactionRepository.findById(transaction_id)
                .orElseThrow(() -> new ResourceEntityNotFoundException(
                        String.format("Transaction com o id %d não encontrado", transaction_id)
                ));

        return modelMapper.map(transaction, TransactionDTO.class);
    }

    private void verificarCategoriaAtiva(Category category) {
        if (!"ATIVA".equalsIgnoreCase(category.getStatus())) {
            throw new ResourceInativoExcepton(String.format("Categoria com o id %d está inativa", category.getId()));
        }
    }


    private void verificarTransacaoExistente(TransactionDTO transactionDTO, Category category) {
        boolean existsTransaction = transactionRepository.existsByValorAndDateAndCategoryAndType(
                transactionDTO.getValor(),
                transactionDTO.getDate(),
                category,
                transactionDTO.getType()
        );

        if (existsTransaction) {
            throw new ResourceJaExistenteException("Uma Transaction com essas descrições já existe");
        }
    }

}
