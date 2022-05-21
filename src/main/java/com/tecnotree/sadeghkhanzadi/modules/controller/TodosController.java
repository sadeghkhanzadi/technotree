package com.tecnotree.sadeghkhanzadi.modules.controller;

import com.google.gson.Gson;
import com.tecnotree.sadeghkhanzadi.models.TodosModel;
import com.tecnotree.sadeghkhanzadi.modules.service.TodosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Made By sadegh khanzadi
 * first name : sadegh
 * last name : khanzadi
 * Tell: +989117018908 - +989030908290
 * mail: khanzadisadegh@gmail.com
 * */

@RestController
@RequestMapping(value = "/todos")
@Slf4j
public class TodosController {
    private final TodosService todosService;

    @Autowired
    public TodosController(TodosService todosService) {
        this.todosService = todosService;
    }

    // Get all to-do’s  ----->  GET
    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public ResponseEntity getAllTodos(){
        List<TodosModel> todosModels = this.todosService.findAll();
        return new ResponseEntity(new Gson().toJson(todosModels) , HttpStatus.OK);
    }//End Method

    //  Get to-do’s of specific user by user id and completed field ------> GET /todos?userId=1&completed=true
    @RequestMapping(value = "" , method = RequestMethod.GET)
    public ResponseEntity getTodosWithUserIDAndCompleted(@RequestParam(value = "userId" , required = true) Long userId,
                                                         @RequestParam(value = "completed" , required = true) Boolean completed){
        if (userId == null){
            return new ResponseEntity("Please Entry Value --> userId is null" , HttpStatus.BAD_REQUEST);
        }else if (completed == null){
            return new ResponseEntity("Please Entry Value --> completed is null" , HttpStatus.BAD_REQUEST);
        }else {
            List<TodosModel> todosModels = this.todosService.findAllTodosWhereUserIdAndCompleted(userId,completed);
            return new ResponseEntity(new Gson().toJson(todosModels) , HttpStatus.OK);
        }
    }//End Method

    //Create Todos --> POST
    @RequestMapping(value = {"","/"} , method = RequestMethod.POST)
    public ResponseEntity createTodo(@RequestBody TodosModel todosModel){
        TodosModel todo = this.todosService.findById(todosModel.getId());
        if (todo.getId() == 0){
            TodosModel t = this.todosService.save_Edit(todosModel);
            return new ResponseEntity(new Gson().toJson(t) , HttpStatus.CREATED);
        } else {
            return new ResponseEntity("Todo is available , Please Try again with Another TODO ID" , HttpStatus.BAD_REQUEST);
        }
    }//End Method

    //Get One With id ------> /todos/{id}
    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public ResponseEntity getOneTodo(@PathVariable(value = "id" , required = true) Long id){
        TodosModel todosModel = this.todosService.findById(id);
        if (todosModel.getId() == 0){
            return new ResponseEntity("Is Not Exist" , HttpStatus.BAD_REQUEST);
        }else if (todosModel.toString().isEmpty()){
            return new ResponseEntity("Is Not Exist" , HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity( new Gson().toJson(todosModel) , HttpStatus.OK);
        }
    }


    //Create All Todos ---> POSTS
    @RequestMapping(value = "/all" , method = RequestMethod.POST)
    @Async
    public ResponseEntity createMultiTodos(@RequestBody List<TodosModel> todosModels){
        List<TodosModel> t = this.todosService.saveAll(todosModels);
        return new ResponseEntity(new Gson().toJson(t) , HttpStatus.CREATED);
    }//End Method
}
