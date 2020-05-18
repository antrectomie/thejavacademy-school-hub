package com.thejavacademy.userservice.repo;

import com.thejavacademy.userservice.model.dto.ActionType;
import com.thejavacademy.userservice.model.entity.Friendship;
import com.thejavacademy.userservice.service.adapters.MysqlFriendshipStorageAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FriendshipRepoTest {

    private static String id1 = UUID.randomUUID().toString();
    private static String id2 = UUID.randomUUID().toString();
    private static String id3 = UUID.randomUUID().toString();


    @Autowired
    MySqlFriendshipRepository repo;

    @Before
    public void setup() {
        repo.deleteAll();
        Friendship f1 = generateFriendship(id1, id2);
        Friendship f2 = generateFriendship(id1, id3);
        Friendship f3 = generateFriendship(id2, id3);
        List<Friendship> friendships = Arrays.asList(f1, f2, f3);
        repo.saveAll(friendships);
    }

    @Test
    public void testSave_whenActionUserIdIsNull_expectException() {
        Friendship f = generateFriendship(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        f.setActionUserId(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> repo.saveAndFlush(f));
    }

    @Test
    public void testSave_whenRelationshipStatusIsNull_expectException() {
        Friendship f = generateFriendship(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        f.setRelationshipStatus(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> repo.saveAndFlush(f));
    }

    @Test
    public void testFindFriendship_whenFriendshipExistsInDatabase_expectFriendshipFound() {
        Friendship query = new Friendship(id1, id2);
        Optional<Friendship> friendship = repo.findFriendship(query.getUserOneId(), query.getUserTwoId());
        Assertions.assertTrue(friendship.isPresent());
    }


    @Test
    public void testFriendship_whenOneOfTheIdsDoesNotExistsInDb_expectEmptyResponse() {
        Friendship query = new Friendship(id1, UUID.randomUUID().toString());
        Optional<Friendship> friendship = repo.findFriendship(query.getUserOneId(), query.getUserTwoId());
        Assertions.assertFalse(friendship.isPresent());
    }
    @Test
    public void testFindUserFriendships_whenFriendshipsExist_expectNotEmptyList(){
        List<Friendship> userFriendhips = repo.findUserFriendhips(id1);
        Assertions.assertEquals(userFriendhips.size(), 2);
    }

    @Test
    public void testFindUserFriendships_whenNoFriendships_expectEmptyList(){
        List<Friendship> userFriendhips = repo.findUserFriendhips(UUID.randomUUID().toString());
        Assertions.assertEquals(userFriendhips.size(), 0);
    }

    private Friendship generateFriendship(String idOne, String idTwo) {
        Friendship friendship = new Friendship(idOne, idTwo);
        friendship.setId(UUID.randomUUID().toString());
        friendship.setActionUserId(idOne);
        friendship.setRelationshipStatus(ActionType.REQUESTED);
        return friendship;
    }


}
