package vn.edu.nghiant.Spotifo.Services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.edu.nghiant.Spotifo.Entities.Music;
import vn.edu.nghiant.Spotifo.Entities.User;
import vn.edu.nghiant.Spotifo.Repositories.MusicRepository;

@Service
public class MusicService {
  @Autowired
  private MusicRepository musicRepository;

  public String uploadCoverImage(MultipartFile file) throws IOException {
    if (file == null || file.isEmpty()) {
      return null;
    }

    try {
      // Tạo thư mục nếu chưa tồn tại
      String uploadDir = "src/main/resources/static/images/coverImg/";
      File directory = new File(uploadDir);
      if (!directory.exists()) {
        directory.mkdirs();
      }

      // Kiểm tra kích thước file (giới hạn 5MB)
      if (file.getSize() > 5 * 1024 * 1024) {
        throw new IOException("File ảnh quá lớn. Kích thước tối đa là 5MB");
      }

      // Kiểm tra định dạng file
      String contentType = file.getContentType();
      if (contentType == null || !contentType.startsWith("image/")) {
        throw new IOException("File không phải là ảnh");
      }

      String originalFilename = file.getOriginalFilename();
      String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
      String fileName = "cover_" + System.currentTimeMillis() + fileExtension;
      String filePath = uploadDir + fileName;

      // Lưu file
      Path path = Paths.get(filePath);
      Files.copy(file.getInputStream(), path);

      // Trả về đường dẫn tương đối cho web
      return "/images/coverImg/" + fileName;
    } catch (IOException e) {
      throw new IOException("Lỗi khi upload ảnh: " + e.getMessage());
    }
  }

  public String uploadAudioFile(MultipartFile file) throws IOException {
    if (file == null || file.isEmpty()) {
      return null;
    }

    try {
      // Tạo thư mục nếu chưa tồn tại
      String uploadDir = "src/main/resources/static/images/audio/";
      File directory = new File(uploadDir);
      if (!directory.exists()) {
        directory.mkdirs();
      }

      // Kiểm tra kích thước file (giới hạn 20MB)
      if (file.getSize() > 20 * 1024 * 1024) {
        throw new IOException("File nhạc quá lớn. Kích thước tối đa là 20MB");
      }

      // Kiểm tra định dạng file
      String contentType = file.getContentType();
      if (contentType == null || !contentType.startsWith("audio/")) {
        throw new IOException("File không phải là file nhạc");
      }

      String originalFilename = file.getOriginalFilename();
      String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
      String fileName = "audio_" + System.currentTimeMillis() + fileExtension;
      String filePath = uploadDir + fileName;

      // Lưu file
      Path path = Paths.get(filePath);
      Files.copy(file.getInputStream(), path);

      // Trả về đường dẫn tương đối cho web
      return "/images/audio/" + fileName;
    } catch (IOException e) {
      throw new IOException("Lỗi khi upload file nhạc: " + e.getMessage());
    }
  }

  public void saveMusic(Music music) {
    musicRepository.save(music);
  }


  public List<Music> getMusicByArtistId(Long id) {
    return musicRepository.findByUploadedId_Id(id);
  }

  public List<Music> findByArtist(User user) {
    return musicRepository.findByUploadedId(user);
  }

  public List<Music> searchMusic(String query) {
    return musicRepository.findByTitleContainingIgnoreCase(query);
  }

  public void deleteMusic(Long id) {
    musicRepository.deleteById(id);
  }

  public Music getMusicById(Long id) {
    return musicRepository.findById(id).orElse(null);
  }

  public List<Music> getLatestMusics() {
    return musicRepository.findAll().stream()
    .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
    .limit(4)
    .toList();
    
  }

  public List<Music> getMusicByCategoryId(Long categoryId) {
    return musicRepository.findByCategoryId(categoryId);
  }
}
