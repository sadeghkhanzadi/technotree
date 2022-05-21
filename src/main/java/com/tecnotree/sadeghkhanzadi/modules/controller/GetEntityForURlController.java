package com.tecnotree.sadeghkhanzadi.modules.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnotree.sadeghkhanzadi.models.CommentsModel;
import com.tecnotree.sadeghkhanzadi.models.PostsModel;
import com.tecnotree.sadeghkhanzadi.models.TodosModel;
import com.tecnotree.sadeghkhanzadi.modules.service.CommentsService;
import com.tecnotree.sadeghkhanzadi.modules.service.PostsService;
import com.tecnotree.sadeghkhanzadi.modules.service.TodosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Made By sadegh khanzadi
 * first name : sadegh
 * last name : khanzadi
 * Tell: +989117018908 - +989030908290
 * mail: khanzadisadegh@gmail.com
 * */

@Component
@Slf4j
public class GetEntityForURlController {
   /**
    * Variables
    * */
    private final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private final String COMMENTS_URL = "https://jsonplaceholder.typicode.com/comments";
    private final String TODOS_URL = "https://jsonplaceholder.typicode.com/todos";
    /**
     * Methods
     * */
    private RestTemplate restTemplate = new RestTemplate();
    private List<PostsModel> postsModels = new ArrayList<>();

    /**
     * Controller
     * */
    private final PostsService postsService;
    private PostsController postsController;
    private final CommentsService commentsService;
    private final TodosService todosService;

    @Autowired
    public GetEntityForURlController(PostsService postsService, PostsController postsController, CommentsService commentsService, TodosService todosService) {
        this.postsService = postsService;
        this.postsController = postsController;
        this.commentsService = commentsService;
        this.todosService = todosService;
    }

    /**
     * Methods
     * */
    //Get Posts
    public void getEntityPosts(){
        try{
            List<PostsModel> o = this.restTemplate.getForObject(POSTS_URL , List.class);
            List<PostsModel> postsModels = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            for (Object ob : o)
            {
                PostsModel pm = objectMapper.convertValue(ob , PostsModel.class);
                postsModels.add(pm);
            }
            if (!o.toString().isEmpty()){
                log.info("I Can Get Posts Entity From URL:" + POSTS_URL);
                try {
                    this.postsService.saveAll(postsModels);
                }catch (Exception e){
                    log.error("Ooh is an Error ; Can't save Posts To DB , And Error Is:" + e.getMessage());
                }
            }else {
                log.error("Ooh is an Error ; Can't Get Posts Entity From URL:" + POSTS_URL);
            }
        }catch (Exception e){
            log.error("Ooh is an Error ; Can't request to POSTS_URL:" + POSTS_URL + "And Error is:" + e.getMessage());
        }
    }//End Method

    //Get Comments
    public void getEntityComments(){
        try{
            List<CommentsModel> com = this.restTemplate.getForObject(COMMENTS_URL , List.class);
            List<CommentsModel> commentsModels = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            for (Object ob : com)
            {
                CommentsModel cm = objectMapper.convertValue(ob , CommentsModel.class);
                commentsModels.add(cm);
            }

            if (!com.toString().isEmpty()){
                log.info("I Can Get Comments Entity From URL:" + COMMENTS_URL);
                try {
                    this.commentsService.saveAll(commentsModels);
                }catch (Exception e){
                    log.error("Ooh is an Error ; Can't save Comments To DB , And Error Is:" + e.getMessage());
                }
            }else {
                log.error("Ooh is an Error ; Can't Get Comments Entity From URL:" + COMMENTS_URL);
            }
        }catch (Exception e){
            log.error("Ooh is an Error ; Can't request to COMMENTS_URL:" + COMMENTS_URL + "And Error is:" + e.getMessage());
        }
    }//End Method

    //Get Todos
    public void getEntityTodos(){
        try{
            List<TodosModel> tm = this.restTemplate.getForObject(TODOS_URL , List.class);
            List<TodosModel> todosModels = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            for (Object ob : tm)
            {
                TodosModel todosModel= objectMapper.convertValue(ob , TodosModel.class);
                todosModels.add(todosModel);
            }

            if (!tm.toString().isEmpty()){
                log.info("I Can Get Todos Entity From URL:" + TODOS_URL);
                try {
                    this.todosService.saveAll(todosModels);
                }catch (Exception e){
                    log.error("Ooh is an Error ; Can't save Todos To DB , And Error Is:" + e.getMessage());
                }
            }else {
                log.error("Ooh is an Error ; Can't Get Todos Entity From URL:" + TODOS_URL);
            }
        }catch (Exception e){
            log.error("Ooh is an Error ; Can't request to TODOS_URL:" + TODOS_URL + "And Error is:" + e.getMessage());
        }
    }//End Method
}
