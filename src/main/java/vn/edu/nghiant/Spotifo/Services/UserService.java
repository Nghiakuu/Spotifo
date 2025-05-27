package vn.edu.nghiant.Spotifo.Services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.edu.nghiant.Spotifo.Entities.User;
import vn.edu.nghiant.Spotifo.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  public PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    return user;
  }
  public User registerUser(User users) {
    if (userRepository.findByUsername(users.getUsername()).isPresent()) {
      throw new RuntimeException("Username đã tồn tại");
    }
    if (userRepository.findByEmail(users.getEmail()).isPresent()) {
      throw new RuntimeException("Email đã tồn tại");
    }
    users.setPassword(passwordEncoder.encode(users.getPassword()));
    users.setEmail(users.getEmail());
    users.setCreatedAt(java.time.LocalDateTime.now());
    users.setAvatar("/images/avatar/default-avatar.jpg");
    return userRepository.save(users);
  }

  public Long getUserIdByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    return user.getId();
  }

  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
  }

  public User getUserById(Long artistId) {
    return userRepository.findById(artistId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + artistId));
  }

  public List<User> searchArtists(String query) {
    return userRepository.findByUsernameContainingIgnoreCase(query);
  }

  public User findById(Long artistId) {
    return userRepository.findById(artistId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + artistId));
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với tên đăng nhập: " + username));
  }

  public List<User> getRandomUsers() {
    List<User> allUsers = userRepository.findAll();
    Collections.shuffle(allUsers);
    return allUsers.stream()
        .limit(4)
        .toList();
  }
}
