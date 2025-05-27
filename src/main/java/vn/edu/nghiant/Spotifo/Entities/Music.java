package vn.edu.nghiant.Spotifo.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "music")
public class Music {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "category_id")
  private Long categoryId;
  @Column(name = "title")
  private String title;
  @Column(name = "lyrics", columnDefinition = "TEXT")
  private String lyrics;
  @Column(name = "audio_file")
  private String audioFile;
  @Column(name = "cover_image")
  private String coverImage;
  @ManyToOne
  @JoinColumn(name = "uploaded_id")
  private User uploadedId;
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLyrics() {
    return lyrics;
  }

  public void setLyrics(String lyrics) {
    this.lyrics = lyrics;
  }

  public String getAudioFile() {
    return audioFile;
  }

  public void setAudioFile(String audioFile) {
    this.audioFile = audioFile;
  }

  public String getCoverImage() {
    return coverImage;
  }

  public void setCoverImage(String coverImage) {
    this.coverImage = coverImage;
  }

  public User getUploadedId() {
    return uploadedId;
  }

  public void setUploadedId(User uploadedId) {
    this.uploadedId = uploadedId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
