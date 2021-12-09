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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

@Component
public class DbInit implements CommandLineRunner {

    @Autowired
    private CatRepository repository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private final Logger logger = LoggerFactory.getLogger(DbInit.class);

    private String getStringFromResourceFile(String filename) throws IOException {
        URL fileUrl = getClass().getClassLoader().getResource(filename);
        if (fileUrl == null) {
            throw new IOException("file " + filename + "doesnt exist");
        }
        File fileWithData = new File(fileUrl.getFile());
        return Files.readString(fileWithData.toPath());
    }

    private void insertInitData() {
        if (repository.count() != 0) {
            logger.info("Data already inserted");
        } else {
            try {
                String data = getStringFromResourceFile("data.json");

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

    private void executeSqlInsertStatementFrom(String filename, String table, boolean doDeleteFirst) {
        String statement = null;
        try {
            statement = getStringFromResourceFile(filename);
        } catch (IOException e) {
            logger.error(e.toString());
            return;
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if (doDeleteFirst) {
            String deleteStatement = "DELETE FROM " + table;
            Query query = entityManager.createNativeQuery(deleteStatement);
            query.executeUpdate();
            logger.info("all data from " + table + " has been deleted");
        }
        Query query = entityManager.createNativeQuery(statement);
        query.executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();

        logger.info("table " + table + " has been updated");
    }

    @Override
    public void run(String... args) throws Exception {
        insertInitData();
    }
}
