package pet.project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pet.project.dto.*;
import pet.project.services.FinishedMatchService;
import pet.project.services.MatchService;

import java.util.UUID;

@Controller
@RequestMapping("/matches")
public class MatchController {
    private static final int DEFAULT_PAGE_SIZE = 2;

    private final MatchService matchService;
    private final FinishedMatchService finishedMatchService;

    @Autowired
    public MatchController(MatchService matchService, FinishedMatchService finishedMatchService) {
        this.matchService = matchService;
        this.finishedMatchService = finishedMatchService;
    }

    @PostMapping
    public ResponseEntity<CreateMatchResponse> createMatch(@Valid @RequestBody CreateMatchRequest createMatchRequest){
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
        ListFinishedMatchDto listFinishedMatchDto = finishedMatchService.getFinishedMatches(page - 1, playerName, DEFAULT_PAGE_SIZE);
        listFinishedMatchDto.setCurrentPage(page);
        return ResponseEntity.ok(listFinishedMatchDto);
    }

}
