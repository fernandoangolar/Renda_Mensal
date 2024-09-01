package ao.com.techAngolar.controller;

import ao.com.techAngolar.dto.CategoryDTO;
import ao.com.techAngolar.exception.ResourceJaExistenteException;
import ao.com.techAngolar.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/renda/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/{category_id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Integer category_id) {
        CategoryDTO categoryDTO = categoryService.findById(category_id);

        return ResponseEntity.ok()
                .body(categoryDTO);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO categoryDTO) {

            CategoryDTO categorySave = categoryService.save(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(categorySave);
    }
    
    @PutMapping(value = "/{category_id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Integer category_id, @Valid @RequestBody CategoryDTO categoryDTO) {

        categoryDTO = categoryService.update(category_id, categoryDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryDTO);
    }
}
