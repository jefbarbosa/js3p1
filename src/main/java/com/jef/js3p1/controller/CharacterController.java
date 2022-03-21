package com.jef.js3p1.controller;

import com.jef.js3p1.dto.CharacterDTO;
import com.jef.js3p1.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class CharacterController {

    private CharacterService characterService;

    public CharacterController(@Autowired CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/search-character/{patternCharacter}")
    public ResponseEntity<List<CharacterDTO>> getCharacter(@PathVariable String patternCharacter) {
        return new ResponseEntity<>(characterService.searchCharacter(patternCharacter), HttpStatus.OK);
    }
}
