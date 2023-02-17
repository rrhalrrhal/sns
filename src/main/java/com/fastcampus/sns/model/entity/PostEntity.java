package com.fastcampus.sns.model.entity;

import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"post\"")
@Getter
@Setter
@SQLDelete(sql = "UPDATED \"post\" SET deleted_at = NOW() where id =?")
@Where(clause = "deleted_at is NULL")
//DB에서 User 테이블을 처리하는 부분
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "body",columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "registerd_at")
    private Timestamp registerdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt(){
        this.registerdAt = Timestamp.from(Instant.now());
    }
    @PreUpdate
    void updatedAt(){
        this.updatedAt = Timestamp.from(Instant.now());
    }



    //User <-> UserEntity 변환?
    public static PostEntity of(String title, String body, UserEntity userEntity){
        PostEntity entity = new PostEntity();
        entity.setTitle(title);
        entity.setBody(body);
        entity.setUser(userEntity);
        return entity;

    }

}
