package com.tecnotree.sadeghkhanzadi.modules.repository;

import com.tecnotree.sadeghkhanzadi.models.CommentsModel;
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
public interface CommentsRepository extends JpaRepository<CommentsModel , Long> {
    //Find By Id
    @Query("select c from CommentsModel c where c.id= :id")
    CommentsModel findById2(@Param("id") long id);

    //Find All By name
    @Query("select c from CommentsModel c where c.name= :name")
    List<CommentsModel> findAllByName(@Param("name") String name);

    //Find All By PostID
    @Query("select c from CommentsModel c where c.postId= :id")
    List<CommentsModel> findAllByPostID(@Param("id") long id);

    //Find All By Email
    @Query("select c from CommentsModel c where c.email= :email")
    List<CommentsModel> findByEmail(@Param("email") String email);
}
