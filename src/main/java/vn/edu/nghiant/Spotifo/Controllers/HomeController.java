package vn.edu.nghiant.Spotifo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import vn.edu.nghiant.Spotifo.Entities.Category;
import vn.edu.nghiant.Spotifo.Entities.Music;
import vn.edu.nghiant.Spotifo.Entities.User;
import vn.edu.nghiant.Spotifo.Services.CategoryService;
import vn.edu.nghiant.Spotifo.Services.MusicService;
import vn.edu.nghiant.Spotifo.Services.UserService;

@Controller
public class HomeController {
  @Autowired
  private MusicService musicService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private UserService userService;
  // Phương thức để hiển thị trang chủ
  @GetMapping("/")
  public String showHomePage(ModelMap model) {
    // Lấy danh sách bài hát mới nhất
    List<Music> latestMusics = musicService.getLatestMusics();
    List<Category> popularCategories = categoryService.getRandomCategories();
    List<User> popularUsers = userService.getRandomUsers();
    // Thêm danh sách bài hát mới nhất vào model
    model.addAttribute("latestMusics", latestMusics);
    // Lấy danh sách thể loại
    model.addAttribute("popularCategories", popularCategories);
    // Lấy danh sách người dùng phổ biến
    model.addAttribute("popularUsers", popularUsers);
    return "index"; // Trả về tên view để hiển thị trang chủ
  }
}
