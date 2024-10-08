package ao.com.techAngolar.service.impl;

import ao.com.techAngolar.dto.MonthlyRepostDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private LocalDate now = LocalDate.now();
    private LocalDate fistDayOfLastMonth;
    private LocalDate lastDayOfLastMonth;
    private LocalDate firstDayOfYear;
    private LocalDate lastDayOfYear;

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

    @Override
    public TransactionDTO update(TransactionDTO transactionDTO, Integer transaction_id) {

        Transaction transaction = transactionRepository.findById(transaction_id)
                .orElseThrow( () -> new ResourceEntityNotFoundException(
                        String.format("Transaction com o id %d não encontrado", transaction_id)
                ));

        modelMapper.map(transactionDTO, transaction);

        if ( transactionDTO.getCategory() != null ) {

            Integer categoryId = transactionDTO.getCategory().getId();
            if ( categoryId != null ) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow( () -> new ResourceEntityNotFoundException(
                                String.format("Category com o id %d não encontrado", categoryId)
                        ));

                transaction.setCategory(category);
            }
        }

        Transaction updateTransaction = transactionRepository.save(transaction);

        return modelMapper.map(updateTransaction, TransactionDTO.class);
    }

    public List<TransactionDTO> filterTransactions(String categoryName, String type, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.filterTransctions(categoryName, type, startDate, endDate);

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return transactionDTOS;
    }

    public List<TransactionDTO> findByCategoryName(String categoryName) {
        List<Transaction> transactions = transactionRepository.findByCategoryName(categoryName);

        if ( transactions.isEmpty() ) {
            throw new ResourceEntityNotFoundException(
                    String.format("Transaction com categoria %s não foi encontrado", categoryName));
        }

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return transactionDTOS;
    }

    public List<TransactionDTO> findByType(String type) {
        List<Transaction> transactions = transactionRepository.findByType(type);

        if ( transactions.isEmpty() ) {
            throw new ResourceEntityNotFoundException(
                    String.format("Transaction com o tipo %s não foi encontrado", type));
        }

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return transactionDTOS;
    }

    public List<TransactionDTO> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findByDateBetween(startDate, endDate);

        if ( transactions.isEmpty() ) {
            throw new ResourceEntityNotFoundException(
                    String.format("Transaction não se faz presete, entre a data %d e %d", startDate, endDate));
        }

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return transactionDTOS;
    }

    // Filtrar Transações do último mês
    public List<TransactionDTO> filterLastMonth() {

        fistDayOfLastMonth = now.withDayOfMonth(1).withDayOfMonth(1);
        lastDayOfLastMonth = now.withDayOfMonth(1).minusMonths(1);

        List<Transaction> byDateBetween = transactionRepository.findByDateBetween(firstDayOfYear, lastDayOfLastMonth);

        List<TransactionDTO> transactionDTOS = byDateBetween.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return transactionDTOS;

    }

    // Filtrar Transações do ano atual
    public List<TransactionDTO> filterCurrentYear() {

        firstDayOfYear = now.withDayOfYear(1);
        lastDayOfYear = now.withDayOfYear(now.lengthOfYear());

        List<Transaction> byDateBetween = transactionRepository.findByDateBetween(firstDayOfYear, lastDayOfYear);

        List<TransactionDTO> transactionDTOS  = byDateBetween.stream()
                .map( transaction -> modelMapper.map(transaction, TransactionDTO.class) )
                .collect(Collectors.toList());

        return transactionDTOS;
    }

    public List<TransactionDTO> filterTransctionsByMinValue(Double minValue) {

        List<Transaction> transactions = transactionRepository.findByValorGreaterThan(minValue);

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return transactionDTOS;
    }

    public List<TransactionDTO> filterTransctionsByMaxValue(Double maxValue) {

        List<Transaction> transactions = transactionRepository.findByValorLessThan(maxValue);

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return transactionDTOS;
    }

    public  List<TransactionDTO> filterTransctionsByValueRange(Double minValue, Double maxValue) {

        List<Transaction> transactions = transactionRepository.findByValorBetween(minValue, maxValue);

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return transactionDTOS;
    }


    // BUsca por SORT
    public List<TransactionDTO> findAllTransactionsSorted(String sortBy, String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sort = Sort.by(sortDirection, sortBy);

        // retorna a lista de transações ordenadas
        List<Transaction> transactions = transactionRepository.findAll(sort);

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());

        return transactionDTOS;

    }

    public MonthlyRepostDTO generateMonthlyReport(int year, int month) {

        List<Transaction> byYearAndMonth = transactionRepository.findByYearAndMonth(year, month);

        double totalEntrada = 0;
        double totalSaida = 0;

        for ( Transaction transaction : byYearAndMonth ) {
            if (transaction.getType().equalsIgnoreCase("ENTRADA")) {
                totalEntrada += transaction.getValor();
            } else if ( transaction.getType().equalsIgnoreCase("SAIDA")) {
                totalSaida += transaction.getValor();
            }
        };

        double saldoTotal = totalEntrada - totalSaida;

        MonthlyRepostDTO monthlyRepostDTO = new MonthlyRepostDTO();
        monthlyRepostDTO.setYear(year);
        monthlyRepostDTO.setMonth(month);
        monthlyRepostDTO.setEntrada(totalEntrada);
        monthlyRepostDTO.setSaida(saldoTotal);

        return monthlyRepostDTO;

    }

    // Busca por PAGEABLE
//    public Page<TransactionDTO> findSortedAndPageable(int page, int size, String sortBy, String direction) {
//
//        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
//
//        List<Transaction> allPageable = transactionRepository.findAllPageable(pageable);
//
//        List<TransactionDTO> transactionDTOS = allPageable.stream()
//                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
//                .collect(Collectors.toList());
//
//        return (Page<TransactionDTO>) transactionDTOS;
//    }

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
