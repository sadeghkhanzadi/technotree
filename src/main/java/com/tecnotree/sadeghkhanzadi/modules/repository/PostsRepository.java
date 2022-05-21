package com.tecnotree.sadeghkhanzadi.modules.repository;

import com.tecnotree.sadeghkhanzadi.models.CommentsModel;
import com.tecnotree.sadeghkhanzadi.models.PostsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Made By sadegh khanzadi
 * first name : sadegh
 * last name : khanzadi
 * Tell: +989117018908 - +989030908290
 * mail: khanzadisadegh@gmail.com
 * */

@Repository
public interface PostsRepository extends JpaRepository<PostsModel , Long> {
    //Find By Id
    @Query("select c from PostsModel c where c.id= :id")
    PostsModel findById2(@Param("id") long id);

    //Find All By Title
    @Query("select c from PostsModel c where c.title= :title")
    List<PostsModel> findAllByTitle(@Param("title") String title);

    //Find All By UserID
    @Query("select c from PostsModel c where c.userId= :id")
    List<PostsModel> findAllByUserId(@Param("id") long id);

    @Query("select c from PostsModel c where c.title like %:title%")
    List<PostsModel> findAllByTitle_Keyword(@Param("title") String title);
}
