package ao.com.techAngolar.controller;

import ao.com.techAngolar.dto.EconomicGoalDTO;
import ao.com.techAngolar.service.EconomicGoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/renda/economic-goal")
public class EconomicGoalController {

    @Autowired
    private EconomicGoalService economicGoalService;

    @PostMapping
    public ResponseEntity<EconomicGoalDTO> save(@Valid @RequestBody EconomicGoalDTO economicGoalDTO) {

        economicGoalDTO = economicGoalService.save(economicGoalDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(economicGoalDTO);
    }

    @GetMapping("/{economic_id}")
    public ResponseEntity<EconomicGoalDTO> findByid(@PathVariable Integer economic_id) {

        EconomicGoalDTO economicGoalDTO = economicGoalService.findById(economic_id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(economicGoalDTO);
    }
}
