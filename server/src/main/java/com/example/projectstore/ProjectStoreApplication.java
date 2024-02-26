package com.example.projectstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories("com.example.projectstore.*")
public class ProjectStoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjectStoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        var path = new java.io.File(".").getCanonicalPath();
        var dirs = List.of("avatars", "projects", "chatImages");
        dirs.forEach(e -> {
            File dir = new File(path + "\\cdn\\" + e);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        });
    }
}
