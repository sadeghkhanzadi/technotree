package com.tecnotree.sadeghkhanzadi.modules.service;

import com.google.gson.Gson;
import com.tecnotree.sadeghkhanzadi.models.PostsModel;
import com.tecnotree.sadeghkhanzadi.modules.repository.PostsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
public class PostsService {
    private final PostsRepository postsRepository;

    @Autowired
    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    //Find All Posts
    public List<PostsModel> findAll(){
        return this.postsRepository.findAll();
    }

    //Find By id
    public PostsModel findById(Long id) {
        try {
            PostsModel postsModel = this.postsRepository.findById2(id);
            if (postsModel.getId() == 0){
                return new PostsModel();
            } else if (postsModel.toString().isEmpty()){
                return new PostsModel();
            }else {
                return postsModel;
            }
        } catch (Exception e){
            return new PostsModel();
        }
    }

    //Find All By userId
    public List<PostsModel> findAllByUserID(Long id){
        return this.postsRepository.findAllByUserId(id);
    }

    //Find All By Title
    public List<PostsModel> findAllByTitle(String Title){
        return this.postsRepository.findAllByTitle(Title);
    }

    //Find All By Title
    //Get all posts that have the “eos” keyword in their title
    public List<PostsModel> findAllByTitle_FindKeyword_Title(String title){
        try {
            return this.postsRepository.findAllByTitle_Keyword(title);
        }catch (Exception e){
            List<PostsModel> p = new ArrayList<>();
            return p;
        }
    }

    //Find ALL Posts And Pagination
    public Page<PostsModel> findAll(Pageable pageable){
        return this.postsRepository.findAll(pageable);
    }

    //Edit && Update
    //save
    //By Entity
    @Transactional
    public PostsModel save_Edit(PostsModel postsModel){
        return this.postsRepository.saveAndFlush(postsModel);
    }

    @Transactional
    @Async
    public List<PostsModel> saveAll(List<PostsModel> postsModels){
        return this.postsRepository.saveAllAndFlush(postsModels);
    }

    //Delete Entity
    @Transactional
    public Boolean deleteByEntity(PostsModel postsModel){
        try {
            this.postsRepository.delete(postsModel);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //Delete By Id
    @Transactional
    public Boolean deleteById(Long id){
        try {
            this.postsRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
