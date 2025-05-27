package vn.edu.nghiant.Spotifo.Services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.nghiant.Spotifo.Entities.Category;
import vn.edu.nghiant.Spotifo.Repositories.CategoryRepository;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public Category getCategoryById(Long categoryId) {
    return categoryRepository.findById(categoryId).orElse(null);
  }

  public List<Category> getRandomCategories() {
    List<Category> categories = categoryRepository.findAll();
    Collections.shuffle(categories);
    return categories.stream()
        .limit(5)
        .toList();
  }
}
