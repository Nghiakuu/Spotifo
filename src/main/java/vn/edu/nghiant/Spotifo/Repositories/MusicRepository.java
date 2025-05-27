package vn.edu.nghiant.Spotifo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.nghiant.Spotifo.Entities.Music;
import vn.edu.nghiant.Spotifo.Entities.User;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByUploadedId_Id(Long uploadedId);

    List<Music> findByTitleContainingIgnoreCase(String title);

    List<Music> findByUploadedId(User user);

    List<Music> findByCategoryId(Long categoryId);
}