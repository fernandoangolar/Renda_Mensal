package ao.com.techAngolar.service.impl;

import ao.com.techAngolar.dto.EconomicGoalDTO;
import ao.com.techAngolar.entity.EconomicGoal;
import ao.com.techAngolar.exception.InvalidEconomicGoalDateException;
import ao.com.techAngolar.repository.EconomicGoalRepository;
import ao.com.techAngolar.service.EconomicGoalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EconomicGoalServiceImpl implements EconomicGoalService {

    @Autowired
    private EconomicGoalRepository economicGoalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EconomicGoalDTO save(EconomicGoalDTO economic) {

        validateDate(economic.getStartdDate(), economic.getEndDate());

        EconomicGoal economicGoal = modelMapper.map(economic, EconomicGoal.class);
        EconomicGoal createEconomic = economicGoalRepository.save(economicGoal);

        return modelMapper.map(createEconomic, EconomicGoalDTO.class);

    }

    private void validateDate(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();

        if ( startDate != null && startDate.isBefore(today) ) {
            throw new InvalidEconomicGoalDateException("A data de início não pode ser no passado. ");
        }

        if ( startDate != null && endDate != null && endDate.isBefore(startDate) ) {
            throw new InvalidEconomicGoalDateException("A data de fim deve ser posterior a data de início");
        }
    }
}
