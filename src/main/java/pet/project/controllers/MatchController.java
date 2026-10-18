package pet.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pet.project.dao.PlayerDao;
import pet.project.dto.CreateMatchDTO;
import pet.project.entities.Player;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/matches")
public class MatchController {

    private final PlayerDao playerDao;

    @Autowired
    public MatchController(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @PostMapping
    public UUID createMatch(@RequestBody CreateMatchDTO createMatchDTO){

        return UUID.randomUUID();
    }
}
