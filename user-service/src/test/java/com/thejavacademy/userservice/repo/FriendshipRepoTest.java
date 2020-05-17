package com.thejavacademy.userservice.repo;

import com.thejavacademy.userservice.model.dto.ActionType;
import com.thejavacademy.userservice.model.entity.Friendship;
import com.thejavacademy.userservice.service.adapters.MysqlFriendshipStorageAdapter;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FriendshipRepoTest {

    @Autowired
    MySqlFriendshipRepository repo;

    @Test
    public void testSave_whenActionUserIdIsNull_expectException(){
        Friendship f = generateFriendship(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        f.setActionUserId(null);
        Assertions.assertThrows(Exception.class, () -> repo.saveAndFlush(f));
    }

    @Test
    public void testSave_whenRelationshipStatusIsNull_expectException(){
        Friendship f = generateFriendship(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        f.setRelationshipStatus(null);
        Assertions.assertThrows(Exception.class, () -> repo.saveAndFlush(f));
    }




    private Friendship generateFriendship(String idOne, String idTwo){

        Friendship friendship = new Friendship(idOne, idTwo);
        friendship.setId(UUID.randomUUID().toString());
        friendship.setActionUserId(idOne);
        friendship.setRelationshipStatus(ActionType.REQUESTED);
        return friendship;
    }

}
