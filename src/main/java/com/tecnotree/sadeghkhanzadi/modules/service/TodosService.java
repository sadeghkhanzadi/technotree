package com.tecnotree.sadeghkhanzadi.modules.service;

import com.tecnotree.sadeghkhanzadi.models.TodosModel;
import com.tecnotree.sadeghkhanzadi.modules.repository.TodosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Made By sadegh khanzadi
 * first name : sadegh
 * last name : khanzadi
 * Tell: +989117018908 - +989030908290
 * mail: khanzadisadegh@gmail.com
 * */

@Service
@Slf4j
public class TodosService {
    private final TodosRepository todosRepository;

    @Autowired
    public TodosService(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    //Find All Todos
    public List<TodosModel> findAll(){
        return this.todosRepository.findAll();
    }

    //Find By id
    public TodosModel findById(Long id) {
        try {
            TodosModel todosModel = this.todosRepository.findById2(id);
            if (todosModel.getId() == 0){
                return new TodosModel();
            }else if (todosModel.toString().isEmpty()){
                return new TodosModel();
            }else{
                return todosModel;
            }
        } catch (Exception e){
            return new TodosModel();
        }
    }

    //Find All By Title
    public List<TodosModel> findAllByName(String title){
        return this.todosRepository.findAllByTitle(title);
    }

    //Find All By UserID
    public List<TodosModel> findAllByUserID(Long id){
        return this.todosRepository.findAllByUserId(id);
    }

    //Find All By Completed
    public List<TodosModel> findAllByCompleted(Boolean act){
        return this.todosRepository.findAllByCompleted(act);
    }

    //  Get to-do’s of specific user by user id and completed field ------> GET /todos?userId=1&completed=true
    public List<TodosModel> findAllTodosWhereUserIdAndCompleted(Long userId , Boolean completed){
        return this.todosRepository.findAllTodosWhereUserIdAndCompleted(userId , completed);
    }

    //Edit && Update
    //save
    @Transactional
    public TodosModel save_Edit(TodosModel todosModel){
        return this.todosRepository.saveAndFlush(todosModel);
    }

    @Transactional
    @Async
    public List<TodosModel> saveAll(List<TodosModel> todosModels){
        return this.todosRepository.saveAllAndFlush(todosModels);
    }

    //Delete Entity
    @Transactional
    public void deleteByEntity(TodosModel todosModel){
        this.todosRepository.delete(todosModel);
    }

    //Delete By Id
    @Transactional
    public void deleteById(Long id){
        this.todosRepository.deleteById(id);
    }
}
