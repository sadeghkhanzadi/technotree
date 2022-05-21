package com.tecnotree.sadeghkhanzadi;

import com.tecnotree.sadeghkhanzadi.modules.controller.GetEntityForURlController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
        SpringApplication.run(Application.class, args);

        GetEntityForURlController getEntityForURlController = context.getBean(GetEntityForURlController.class);
        getEntityForURlController.getEntityPosts();
        getEntityForURlController.getEntityComments();
        getEntityForURlController.getEntityTodos();

    }

}
