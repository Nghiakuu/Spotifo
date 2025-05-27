package vn.edu.nghiant.Spotifo.Controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import vn.edu.nghiant.Spotifo.Entities.Music;
import vn.edu.nghiant.Spotifo.Entities.User;
import vn.edu.nghiant.Spotifo.Services.CategoryService;
import vn.edu.nghiant.Spotifo.Services.MusicService;
import vn.edu.nghiant.Spotifo.Services.UserService;

@Controller
public class MusicController {
  @Autowired
  private MusicService musicService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private UserService userService;

  // Hien thi form them bai hat moi
  @GetMapping("/add-music")
  public String showAddMusicForm(ModelMap model) {
    model.addAttribute("music", new Music());
    model.addAttribute("categories", categoryService.getAllCategories());
    return "add-music";
  }
  // Them bai hat moi
  @PostMapping("/add-music")
  public String addMusic(
      @RequestParam("title") String title,
      @RequestParam("category") Long categoryId,
      @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
      @RequestParam("audioFile") MultipartFile audioFile,
      @RequestParam(value = "lyrics", required = false) String lyrics,
      HttpSession session,
      RedirectAttributes redirectAttributes) throws IOException {
    
    if (audioFile == null || audioFile.isEmpty()) {
        redirectAttributes.addFlashAttribute("error", "Vui lòng chọn file nhạc");
        return "redirect:/add-music";
    }

    Music music = new Music();
    music.setTitle(title);
    music.setCategoryId(categoryId);
    music.setLyrics(lyrics);
    
    // xử lý tác giả
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.getUserByUsername(username);
    music.setUploadedId(user);
    
    // xử lý file ảnh
    if (coverImage != null && !coverImage.isEmpty()) {
        String imageUrl = musicService.uploadCoverImage(coverImage);
        music.setCoverImage(imageUrl);
    }
    
    // xử lý file nhạc
    String audioUrl = musicService.uploadAudioFile(audioFile);
    music.setAudioFile(audioUrl);
    // Thoi gian tao
    music.setCreatedAt(LocalDateTime.now());
    
    musicService.saveMusic(music);
    redirectAttributes.addFlashAttribute("success", "Thêm bài hát thành công!");
    return "redirect:/";
  }
  // Hiển thị bai hat theo ten nghe si
  @GetMapping("/artist/{id}")
  public String showMusicByArtist(
      @PathVariable("id") Long artistId,
      ModelMap model,
      HttpSession session) {

    // Lấy thông tin nghệ sĩ
    User artist = userService.getUserById(artistId);
    if (artist == null) {
      return "redirect:/";
    }

    // Lấy danh sách bài hát của nghệ sĩ
    List<Music> songs = musicService.getMusicByArtistId(artistId);

    // Thêm vào model
    model.addAttribute("artist", artist);
    model.addAttribute("songs", songs);

    return "artist-songs"; // Trả về template hiển thị
  }
  // Hiển thị bài hát của người dùng đăng nhập

  @GetMapping("/my-music")
  public String showMyMusic(ModelMap model, HttpSession session) {
    // Lấy thông tin người dùng đang đăng nhập
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.getUserByUsername(username);
    
    // Lấy danh sách bài hát của người dùng
    List<Music> mySongs = musicService.getMusicByArtistId(user.getId());
    
    // Thêm vào model
    model.addAttribute("user", user);
    model.addAttribute("songs", mySongs);
    
    return "my-music";
  }


  @GetMapping("/search")
  public String search(@RequestParam(required = false) String query, ModelMap model) {
    if (query != null && !query.trim().isEmpty()) {
      List<Music> songs = musicService.searchMusic(query);
      List<User> artists = userService.searchArtists(query);
      model.addAttribute("songs", songs);
      model.addAttribute("artists", artists);
      model.addAttribute("query", query);
    }
    return "search";
  }

  @PostMapping("/delete-music/{id}")
  public String deleteMusic(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
      musicService.deleteMusic(id);
      redirectAttributes.addFlashAttribute("success", "Bài hát đã được xóa thành công!");
      return "redirect:/my-music";
  }

  // Hien thi form chinh sua bai hat
  @GetMapping("/edit-music/{id}")
  public String showEditMusicForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
      Music music = musicService.getMusicById(id);
      if (music == null) {
          redirectAttributes.addFlashAttribute("error", "Bài hát không tồn tại!");
          return "redirect:/my-music";
      }
      model.addAttribute("music", music);
      model.addAttribute("categories", categoryService.getAllCategories());
      return "edit-music";
  }

  // Chinh sua bai hat
  @PostMapping("/edit-music/{id}")
  public String editMusic(
          @PathVariable("id") Long id,
          @RequestParam("title") String title,
          @RequestParam("category") Long categoryId,
          @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
          @RequestParam(value = "audioFile", required = false) MultipartFile audioFile,
          @RequestParam(value = "lyrics", required = false) String lyrics,
          RedirectAttributes redirectAttributes) throws IOException {
      Music music = musicService.getMusicById(id);
      if (music == null) {
          redirectAttributes.addFlashAttribute("error", "Bài hát không tồn tại!");
          return "redirect:/my-music";
      }

      music.setTitle(title);
      music.setCategoryId(categoryId);
      music.setLyrics(lyrics);

      // Xử lý cập nhật ảnh bìa nếu có
      if (coverImage != null && !coverImage.isEmpty()) {
          String imageUrl = musicService.uploadCoverImage(coverImage);
          music.setCoverImage(imageUrl);
      }

      // Xử lý cập nhật file nhạc nếu có
      if (audioFile != null && !audioFile.isEmpty()) {
          String audioUrl = musicService.uploadAudioFile(audioFile);
          music.setAudioFile(audioUrl);
      }

      musicService.saveMusic(music);
      redirectAttributes.addFlashAttribute("success", "Cập nhật bài hát thành công!");
      return "redirect:/my-music";
  }

}
