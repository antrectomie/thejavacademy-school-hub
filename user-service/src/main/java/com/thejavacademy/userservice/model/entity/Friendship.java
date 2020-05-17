package com.thejavacademy.userservice.model.entity;

import com.thejavacademy.userservice.model.dto.ActionType;
import com.thejavacademy.userservice.util.FriendshipType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
public class Friendship {

    @Id
    private String id;
    @Column(nullable = false)
    private  String userOneId;
    @Column(nullable = false)
    private  String userTwoId;
    @Column(nullable = false)
    private ActionType relationshipStatus;
    @Column(nullable = false)
    private String actionUserId;

    public Friendship() {
    }

    public Friendship(String userOneId, String userTwoId) {
        if(userOneId == null ||userTwoId ==null){
            throw new IllegalArgumentException();
        }
        if(userOneId.compareTo(userTwoId) < 0){
            this.userOneId = userOneId;
            this.userTwoId = userTwoId;
        }else{
            this.userOneId = userTwoId;
            this.userTwoId = userOneId;
        }
    }


    public String getFriendId(String id){
        return id.equals(this.userOneId) ? userTwoId : userOneId;
    }


}



