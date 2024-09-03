package ao.com.techAngolar.service;

import ao.com.techAngolar.dto.EconomicGoalDTO;

public interface EconomicGoalService {

    EconomicGoalDTO save(EconomicGoalDTO economic);
    EconomicGoalDTO findById(Integer economicId);

    EconomicGoalDTO update(EconomicGoalDTO economicGoalDTO, Integer economic_id);
}
