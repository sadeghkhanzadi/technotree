package com.tecnotree.sadeghkhanzadi.modules.repository;

import com.tecnotree.sadeghkhanzadi.models.CommentsModel;
import com.tecnotree.sadeghkhanzadi.models.TodosModel;
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
public interface TodosRepository extends JpaRepository<TodosModel , Long> {
    //Find By Id
    @Query("select t from TodosModel t where t.id= :id")
    TodosModel findById2(@Param("id") long id);

    //Find All By Title
    @Query("select t from TodosModel t where t.title= :title")
    List<TodosModel> findAllByTitle(@Param("title") String title);

    //Find All By UserID
    @Query("select t from TodosModel t where t.userId= :id")
    List<TodosModel> findAllByUserId(@Param("id") long id);

    //Find All By Completed
    @Query("select t from TodosModel t where t.completed= :act")
    List<TodosModel> findAllByCompleted(@Param("act") Boolean act);

    @Query("select t from TodosModel t where t.userId= :userId and t.completed= :completed")
    List<TodosModel> findAllTodosWhereUserIdAndCompleted(@Param("userId") Long userId, @Param("completed") Boolean completed);
}
