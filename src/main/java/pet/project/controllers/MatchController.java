package pet.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pet.project.dto.*;
import pet.project.services.MatchService;

import java.util.UUID;

@Controller
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<CreateMatchResponse> createMatch(@RequestBody CreateMatchRequest createMatchRequest){
        UUID uuid = matchService.createMatch(createMatchRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/matches/" + uuid)
                .body(new CreateMatchResponse(uuid));
    }

    @PostMapping("/{uuid}/point")
    public ResponseEntity<MatchDto> addPoint(@RequestBody AddPointRequest addPointRequest, @PathVariable("uuid") UUID uuid){
        MatchDto matchDto = matchService.addPoint(uuid, addPointRequest);
        return ResponseEntity.ok(matchDto);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MatchDto> getScore(@PathVariable("uuid") UUID uuid){
        MatchDto matchDto = matchService.getScore(uuid);
        return ResponseEntity.ok(matchDto);
    }

    @GetMapping
    public ResponseEntity<ListFinishedMatchDto> getFinishedMatches(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                                                   @RequestParam(name = "player_name", required = false) String playerName){
        ListFinishedMatchDto listFinishedMatchDto = matchService.getFinishedMatches(page - 1, playerName);
        listFinishedMatchDto.setCurrentPage(page);
        return ResponseEntity.ok(listFinishedMatchDto);
    }

}
