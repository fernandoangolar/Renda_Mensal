package ao.com.techAngolar.service;

import ao.com.techAngolar.dto.CategoryDTO;
import ao.com.techAngolar.entity.Category;
import ao.com.techAngolar.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);
    CategoryDTO findById(Integer id);

    CategoryDTO update(Integer category_id, CategoryDTO categoryDTO);
}
