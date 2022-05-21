package com.tecnotree.sadeghkhanzadi.modules.controller;

import com.google.gson.Gson;
import com.tecnotree.sadeghkhanzadi.models.PostsModel;
import com.tecnotree.sadeghkhanzadi.modules.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/posts")
@Slf4j
public class PostsController {
    private final PostsService postsService;
    private final CommentsController commentsController;

    @Autowired
    public PostsController(PostsService postsService, CommentsController commentsController) {
        this.postsService = postsService;
        this.commentsController = commentsController;
    }

    //GetAll And Pagination
    // -------------------> /posts/pagination?page=2&size=10
    @RequestMapping(value = "/pagination" , method = RequestMethod.GET)
    public ResponseEntity getAllWithPagination(@PageableDefault(size = 5) Pageable pageable){
        Page<PostsModel> postsModels = this.postsService.findAll(pageable);
        return new ResponseEntity( new Gson().toJson(postsModels) , HttpStatus.OK);
    }//End Method

    //Get Post By Post Id --- GET /posts/1
    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public ResponseEntity getPostByPostID(@PathVariable(value = "id" , required = true) Long id){
        PostsModel postsModel = this.postsService.findById(id);
        if (postsModel.getId() == 0){
            return new ResponseEntity("Is not Exist" , HttpStatus.BAD_REQUEST);
        }else if (postsModel.toString().isEmpty()){
            return new ResponseEntity("Is not Exist" , HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity(new Gson().toJson(postsModel) , HttpStatus.OK);
        }
    }//End Method

    //Get comments of specific post by post id --- /posts/1/comments
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public ResponseEntity getAllCommentsOfSpecificPost(@PathVariable(value = "id" , required = true) Long id){
        ResponseEntity responseEntity = this.commentsController.getAllCommentsWithPostID(id);
        try {
            return new ResponseEntity(responseEntity.getBody() , HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Error :" + e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }//End Method

    //Get all posts that have the “eos” keyword in their title  --- GET /posts?title=eos
    @RequestMapping(value = "" , method = RequestMethod.GET)
    public ResponseEntity getAllPosts_FindKeyword_Title(@RequestParam(value = "title" , required = true) String title){
        List<PostsModel> p = postsService.findAllByTitle_FindKeyword_Title(title);
        return new ResponseEntity(p , HttpStatus.OK);
    }//End Method

    //Create a post -- POST
    @RequestMapping(value = {"" , "/"} , method = RequestMethod.POST)
    public ResponseEntity creatNewPost(@RequestBody PostsModel postsModel){
        PostsModel po = this.postsService.findById(postsModel.getId());
        if (po.getId() == postsModel.getId()){
            return new ResponseEntity("Post Is Exist" , HttpStatus.BAD_REQUEST);
        } else{
            PostsModel post = this.postsService.save_Edit(postsModel);
            return new ResponseEntity(new Gson().toJson(post) , HttpStatus.CREATED);
        }
    }//End Method

    //Update a post by post id ---- PATCH /posts/1
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity updatePostByID(@PathVariable(value = "id" , required = true) Long id ,
                                         @RequestBody PostsModel postsModel){
        PostsModel posts = this.postsService.findById(id);
        if (posts.toString().equals(postsModel)){
            return new ResponseEntity("Entity is not different , Entity is Exist" , HttpStatus.BAD_REQUEST);
        }else if(posts.getId() == 0){
            return new ResponseEntity("Is Not Exist!!" , HttpStatus.BAD_REQUEST);
        }else if(id != postsModel.getId()){
            return new ResponseEntity("Id Is Not Equal to postId ----> /posts/{id} != postsModel.getId " , HttpStatus.BAD_REQUEST);
        }else {
            PostsModel p = this.postsService.save_Edit(postsModel);
            return new ResponseEntity(new Gson().toJson(p), HttpStatus.CREATED);
        }
    }//End Method

    //Delete a post by post id ---- DELETE /posts/1
    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity deletePostByID(@PathVariable(value = "id" , required = true) Long id){
        PostsModel p = this.postsService.findById(id);
        if (p.getId() == 0){
            return new ResponseEntity("Is not Exist!!" , HttpStatus.BAD_REQUEST);
        }else {
            this.postsService.deleteById(id);
            return new ResponseEntity("Deleted" , HttpStatus.OK);
        }
    }//End Method
}
