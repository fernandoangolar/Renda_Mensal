package ao.com.techAngolar.service.impl;

import ao.com.techAngolar.dto.CategoryDTO;
import ao.com.techAngolar.entity.Category;
import ao.com.techAngolar.exception.ResourceInativoExcepton;
import ao.com.techAngolar.exception.ResourceJaExistenteException;
import ao.com.techAngolar.exception.ResourceEntityNotFoundException;
import ao.com.techAngolar.repository.CategoryRepository;
import ao.com.techAngolar.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl  implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {

         Optional<Category> categoryPresent = categoryRepository.findByName(categoryDTO.getName());

        if ( categoryPresent.isPresent() ) {
            throw new ResourceJaExistenteException("Category com o nome " + categoryDTO.getName() + " já existe");
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoriaSave = categoryRepository.save(category);
        return modelMapper.map(categoriaSave, CategoryDTO.class);
    }

    @Override
    public CategoryDTO findById(Integer category_id) {


        Category objCategory = checkCategoryIspresent(category_id);

        checkIfCategoryIsActive(objCategory, category_id);

        return modelMapper.map(objCategory, CategoryDTO.class);


//        return categoryRepository.findById(category_id)
//                .map(category -> modelMapper.map(category, CategoryDTO.class))
//                .orElseThrow(() -> new ResourceEntityNotFoundException(
//                        String.format("CAtegory com o id %d não foi encontrado", category_id)
//                ));
    }

    @Override
//    @Transactional
    public CategoryDTO update(Integer category_id, CategoryDTO categoryDTO) {

            Category category = checkCategoryIspresent(category_id);

            checkIfCategoryIsActive(category, category_id);

            modelMapper.map(categoryDTO, category);

            Category updateCategory = categoryRepository.save(category);

            return modelMapper.map(updateCategory, CategoryDTO.class);

    }

    private Category checkCategoryIspresent(Integer category_id) {
        return categoryRepository.findById(category_id)
                .orElseThrow(() ->  new ResourceEntityNotFoundException(
                        String.format("Category com o id %d não foi encontrado", category_id)
                ));
    }
    private void checkIfCategoryIsActive(Category category, Integer categoryId) {
        if ("INATIVO".equalsIgnoreCase(category.getStatus())) {
            throw new ResourceInativoExcepton(
                    String.format("Categoria com o id %d está inativa", categoryId));
        }
    }
}
