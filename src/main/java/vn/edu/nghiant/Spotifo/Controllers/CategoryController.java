package vn.edu.nghiant.Spotifo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.edu.nghiant.Spotifo.Entities.Category;
import vn.edu.nghiant.Spotifo.Entities.Music;
import vn.edu.nghiant.Spotifo.Services.CategoryService;
import vn.edu.nghiant.Spotifo.Services.MusicService;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MusicService musicService;

    @GetMapping("/category/{id}")
    public String showCategoryPage(@PathVariable("id") Long categoryId, ModelMap model) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            return "redirect:/";
        }

        List<Music> songs = musicService.getMusicByCategoryId(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("songs", songs);
        return "category";
    }
} 