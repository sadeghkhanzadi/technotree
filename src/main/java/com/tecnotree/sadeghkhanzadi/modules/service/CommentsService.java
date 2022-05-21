package com.tecnotree.sadeghkhanzadi.modules.service;

import com.tecnotree.sadeghkhanzadi.models.CommentsModel;
import com.tecnotree.sadeghkhanzadi.modules.repository.CommentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class CommentsService {
    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    //Find All Comments
    public List<CommentsModel> findAll(){
        return this.commentsRepository.findAll();
    }

    //Find ALL Posts And Pagination
    public Page<CommentsModel> findAll(Pageable pageable){
        return this.commentsRepository.findAll(pageable);
    }

    //Find By id
    public CommentsModel findById(Long id) {
        try {
            CommentsModel commentsModel = this.commentsRepository.findById2(id);
            if (commentsModel.getId() == 0){
                return new CommentsModel();
            } if (commentsModel.toString().isEmpty()){
                return new CommentsModel();
            }else {
                return commentsModel;
            }
        } catch (Exception e){
            return new CommentsModel();
        }
    }

    //Find All By name
    public List<CommentsModel> findAllByName(String name){
        return this.commentsRepository.findAllByName(name);
    }

    //Find All By PostID
    public List<CommentsModel> findAllByPostID(Long id){
        return this.commentsRepository.findAllByPostID(id);
    }

    //Find All By Email
    public List<CommentsModel> findAllByEmail(String email){
        return this.commentsRepository.findByEmail(email);
    }

    //Edit && Update
    //save
    @Transactional
    public CommentsModel save_Edit(CommentsModel commentsModel){
        return this.commentsRepository.saveAndFlush(commentsModel);
    }

    //Create Multiple Comments
    //saveALL
    @Transactional
    @Async
    public List<CommentsModel> saveAll(List<CommentsModel> commentsModels){
        return this.commentsRepository.saveAllAndFlush(commentsModels);
    }

    //Delete Entity
    @Transactional
    public void deleteByEntity(CommentsModel commentsModel){
        this.commentsRepository.delete(commentsModel);
    }

    //Delete By Id
    @Transactional
    public void deleteById(Long id){
        this.commentsRepository.deleteById(id);
    }

}
