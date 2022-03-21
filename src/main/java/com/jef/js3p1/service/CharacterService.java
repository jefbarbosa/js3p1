package com.jef.js3p1.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jef.js3p1.dto.CharacterDTO;
import com.jef.js3p1.dto.CharacterFile;
import com.jef.js3p1.entity.Character;
import com.jef.js3p1.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class CharacterService implements CharacterRepository {

    List<Character> databaseCharacter = new ArrayList<>();

    private String localPath;

    @Autowired
    public CharacterService(@Value("${database.local.path:\"src/main/resources/static/starwars.json\"}") String localPath) {
        this.localPath = localPath;

        List<CharacterFile> listCharacterFile = loadData();
        transform(listCharacterFile);
    }

    @Override
    public List<CharacterFile> loadData() {
        final ObjectMapper objectMapper = new ObjectMapper();
        List<CharacterFile> listCharacterFile;

        try {
            listCharacterFile = objectMapper.readValue(new File(localPath),
                    new TypeReference<List<CharacterFile>>() {});
        } catch (IOException e) {
            System.out.println(e);
            return new ArrayList<>();
        }

        return listCharacterFile;
    }

    private void transform(List<CharacterFile> listCharacterFile) {
        List<Character> listCharacter = new ArrayList<>();

        for (CharacterFile cf: listCharacterFile) {
            Character character = new Character();

            if (cf.getHeight().equals("NA"))
                character.setHeight(-1);
            if (cf.getMass().equals("NA"))
                character.setMass(-1);

            character.setName(cf.getName());
            character.setHairColor(cf.getHair_color());
            character.setSkinColor(cf.getSkin_color());
            character.setEyeColor(cf.getEye_color());
            character.setBirthYear(cf.getBirth_year());
            character.setGender(cf.getGender());
            character.setHomeworld(cf.getHomeworld());
            character.setSpecies(cf.getSpecies());

            databaseCharacter.add(character);
        }

    }

    public List<CharacterDTO> searchCharacter(String pattern) {
        return databaseCharacter.stream()
                .filter(character -> (character.getName().toLowerCase().contains(pattern) || character.getName().toUpperCase().contains(pattern)))
                .map(ch -> new CharacterDTO(ch.getName(), ch.getHeight(), ch.getMass(), ch.getGender(), ch.getHomeworld(), ch.getSpecies()))
                .collect(Collectors.toList());

    }
}
