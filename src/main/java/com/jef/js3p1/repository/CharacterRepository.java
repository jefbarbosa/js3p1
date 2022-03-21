package com.jef.js3p1.repository;

import com.jef.js3p1.dto.CharacterFile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface CharacterRepository {

    List<CharacterFile> loadData() throws IOException;
}
