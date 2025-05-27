package vn.edu.nghiant.Spotifo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.edu.nghiant.Spotifo.Entities.Music;
import vn.edu.nghiant.Spotifo.Services.MusicService;

@Controller
public class MusicPlayerController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/play/{id}")
    public String playMusic(@PathVariable("id") Long musicId, ModelMap model) {
        Music music = musicService.getMusicById(musicId);
        if (music == null) {
            return "redirect:/";
        }
        model.addAttribute("music", music);
        return "player";
    }
} 