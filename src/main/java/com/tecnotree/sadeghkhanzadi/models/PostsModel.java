package com.tecnotree.sadeghkhanzadi.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Made By sadegh khanzadi
 * first name : sadegh
 * last name : khanzadi
 * Tell: +989117018908 - +989030908290
 * mail: khanzadisadegh@gmail.com
 * */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostsModel {
    @Id
    private long id;
    private long userId;
    private String title;
    @Lob
    private String body;
}
