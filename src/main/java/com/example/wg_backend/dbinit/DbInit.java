package com.example.wg_backend.dbinit;

import com.example.wg_backend.model.Cat;
import com.example.wg_backend.repository.CatRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
public class DbInit implements CommandLineRunner {

    @Autowired
    private CatRepository repository;

    private final Logger logger = LoggerFactory.getLogger(DbInit.class);

    private void insertData() {
        if (repository.count() != 0) {
            logger.info("Data already inserted");
        } else {
            try {
                File fileWithData = new File(getClass().getClassLoader().getResource("data.json").getFile());
                String data = Files.readString(fileWithData.toPath());

                ObjectMapper objectMapper = new ObjectMapper();
                List<Cat> cats = objectMapper.readValue(data, new TypeReference<List<Cat>>() {
                });

                repository.saveAll(cats);
                logger.info("Data inserted");

            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        insertData();
        // logger.info("Inserting data process will be here");
    }
}
