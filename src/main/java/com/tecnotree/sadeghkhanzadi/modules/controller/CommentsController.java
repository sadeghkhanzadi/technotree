package com.tecnotree.sadeghkhanzadi.modules.controller;

import com.google.gson.Gson;
import com.tecnotree.sadeghkhanzadi.models.CommentsModel;
import com.tecnotree.sadeghkhanzadi.models.PostsModel;
import com.tecnotree.sadeghkhanzadi.modules.service.CommentsService;
import com.tecnotree.sadeghkhanzadi.modules.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping(value = "/comments")
@Slf4j
public class CommentsController {
    private final CommentsService commentsService;
    private PostsService postsService;

    @Autowired
    public CommentsController(CommentsService commentsService, PostsService postsService) {
        this.commentsService = commentsService;
        this.postsService = postsService;
    }

    //  GET Get all comments(with pagination) ----> GET
    // -------------------> /comments/pagination?page=2&size=10
    @RequestMapping(value = "/pagination" , method = RequestMethod.GET)
    public ResponseEntity getAllWithPagination(@PageableDefault(size = 5) Pageable pageable){
        Page<CommentsModel> commentsModels = this.commentsService.findAll(pageable);
        return new ResponseEntity(new Gson().toJson(commentsModels) , HttpStatus.OK);
    }//End Method


    //  Get comments of specific post by post id ----> GET /comments?postId=1
    @RequestMapping(value = "" , method = RequestMethod.GET)
    public ResponseEntity getAllCommentsWithPostID(@RequestParam(value = "postId") Long postId){
        if (postId == null){
            return new ResponseEntity("PostID is Null" , HttpStatus.BAD_REQUEST);
        }else {
            List<CommentsModel>  commentsModels = this.commentsService.findAllByPostID(postId);
            if (commentsModels.get(0).getId() == 0) {
                return new ResponseEntity("Is Not Exist!!" , HttpStatus.BAD_REQUEST);
            }else if (commentsModels.isEmpty()){
                return new ResponseEntity("Posts is Not Exists" , HttpStatus.OK);
            }else {
                return new ResponseEntity(new Gson().toJson(commentsModels) , HttpStatus.OK);
            }
        }
    }//End Method

    // Create a comments ----> POST
    @RequestMapping(value = {"","/"}, method = RequestMethod.POST)
    public ResponseEntity createNewComments(@RequestBody CommentsModel commentsModel){
        Long id = commentsModel.getPostId();
        CommentsModel com = this.commentsService.findById(commentsModel.getId());
        PostsModel postsModel = this.postsService.findById(id);
        if (com.getId() == 0) {
            return new ResponseEntity("Is Not Exist!!" , HttpStatus.BAD_REQUEST);
        }else if (postsModel.toString().isEmpty()){
            return new ResponseEntity("PostId is not Exist in Post DB , Please create Post and try again" , HttpStatus.BAD_REQUEST);
        }else if(com.getId() == commentsModel.getId()) {
            return new ResponseEntity("Comment is available with this ID" , HttpStatus.BAD_REQUEST);
        }else {
            CommentsModel c = this.commentsService.save_Edit(commentsModel);
            return new ResponseEntity(new Gson().toJson(c) , HttpStatus.CREATED);
        }
    }//End Method

    // Create Multiple Comments ---> POST
    @RequestMapping(value = "/all" , method = RequestMethod.POST)
    @Async
    public ResponseEntity createMultiComments(@RequestBody List<CommentsModel> commentsModels){
        List<CommentsModel> c = this.commentsService.saveAll(commentsModels);
        return new ResponseEntity(new Gson().toJson(c) , HttpStatus.CREATED);
    }

    //  Update a Comment by Comment id ------>   PATCH /comments/1
    @RequestMapping(value = "/{id}" , method = RequestMethod.PATCH)
    public ResponseEntity updateCommentsByID(@PathVariable(value = "id" , required = true) Long id , @RequestBody CommentsModel commentsModel) {
        CommentsModel c = this.commentsService.findById(id);
        if (!id.equals(commentsModel.getId())){
            return new ResponseEntity("CommentsId is not Equal with Path id ---> id != /comments/{id}" , HttpStatus.BAD_REQUEST);
        } else if (c.equals(commentsModel)) {
            return new ResponseEntity("Entity is not different , Entity is Exist", HttpStatus.BAD_REQUEST);
        }if (c.getId() == 0) {
            return new ResponseEntity("Is Not Exist!!" , HttpStatus.BAD_REQUEST);
        }else if(c.toString().isEmpty()){
            return new ResponseEntity("Is Not Exist!!" , HttpStatus.BAD_REQUEST);
        }else {
            CommentsModel com = this.commentsService.save_Edit(commentsModel);
            return new ResponseEntity(new Gson().toJson(com) , HttpStatus.CREATED);
        }
    }//End Method

    //  Delete a Comment by comment id ------>   DELETE /comments/1
    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity deleteCommentByID(@PathVariable(value = "id" , required = true) Long id){
        CommentsModel commentsModel = this.commentsService.findById(id);
        if (commentsModel.getId() == 0) {
            return new ResponseEntity("Is Not Exist!!" , HttpStatus.BAD_REQUEST);
        }else if (commentsModel.toString().isEmpty()){
            return new ResponseEntity("Is Not Exist!!" , HttpStatus.BAD_REQUEST);
        } else {
            this.commentsService.deleteById(id);
            return new ResponseEntity("Deleted" , HttpStatus.OK);
        }
    }//End Method

    //Get Comments By id ----> get /comments/{id}
    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public ResponseEntity getCommentsByID(@PathVariable(value = "id" , required = true) Long id){
        CommentsModel commentsModel = this.commentsService.findById(id);
        if (commentsModel.getId() == 0) {
            return new ResponseEntity("Is Not Exist!!" , HttpStatus.BAD_REQUEST);
        }else if (commentsModel.toString().isEmpty()){
            return new ResponseEntity("Is Not Exist!!" , HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity(new Gson().toJson(commentsModel) , HttpStatus.OK);
        }
    }//End Method
}
