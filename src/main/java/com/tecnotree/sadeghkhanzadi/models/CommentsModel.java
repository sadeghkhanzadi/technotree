package com.tecnotree.sadeghkhanzadi.models;

import lombok.*;

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
public class CommentsModel {
    @Id
    private long id;
    private long postId;
    private String name;
    private String email;
    @Lob
    private String body;
}
