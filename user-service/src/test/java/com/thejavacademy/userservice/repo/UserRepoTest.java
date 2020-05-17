package com.thejavacademy.userservice.repo;

import com.thejavacademy.userservice.model.entity.User;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepoTest {

    @Autowired
    private MySqlUserRepo mySqlUserRepo;

    @Test
    public void testSave_whenSaveUserNoId_expectException() {
        User user = new User();
        Assertions.assertThrows(JpaSystemException.class, () -> mySqlUserRepo.save(user));
    }


    @Test
    public void test_whenSaveWithNullUsername_expectException() {
        User user = generateRandomUser(UUID.randomUUID().toString());
        user.setUsername(null);
        Assertions.assertThrows(Exception.class, () -> mySqlUserRepo.saveAndFlush(user));
    }

    @Test
    public void test_whenSaveWithNullEmail_expectException() {
        User user = generateRandomUser(UUID.randomUUID().toString());
        user.setEmail(null);
        Assertions.assertThrows(Exception.class, () -> mySqlUserRepo.saveAndFlush(user));
    }

    @Test
    public void test_whenSaveWithNullPhoneNumber_expectException() {
        User user = generateRandomUser(UUID.randomUUID().toString());
        user.setPhoneNumber(null);
        Assertions.assertThrows(Exception.class, () -> mySqlUserRepo.saveAndFlush(user));
    }


    @Test
    public void testSave_whenTryingtoSaveANewUserWithNotUniqueUsername_expectException() {
        final String firstRandom = UUID.randomUUID().toString();
        User user1 = generateRandomUser(firstRandom);
        mySqlUserRepo.saveAndFlush(user1);
        User user2 = generateRandomUser(UUID.randomUUID().toString());
        user2.setUsername(firstRandom);
        assertThrows(Exception.class, () -> mySqlUserRepo.saveAndFlush(user2));
    }

    @Test
    public void testSave_whenTryingtoSaveANewUserWithNotUniqueEmail_expectException() {
        final String firstRandom = UUID.randomUUID().toString();
        User user1 = generateRandomUser(firstRandom);
        mySqlUserRepo.saveAndFlush(user1);
        User user2 = generateRandomUser(UUID.randomUUID().toString());
        user2.setEmail(firstRandom);
        assertThrows(Exception.class, () -> mySqlUserRepo.saveAndFlush(user2));
    }

    @Test
    public void testSave_whenTryingtoSaveANewUserWithNotPhoneNumber_expectException() {
        final String firstRandom = UUID.randomUUID().toString();
        User user1 = generateRandomUser(firstRandom);
        mySqlUserRepo.saveAndFlush(user1);
        User user2 = generateRandomUser(UUID.randomUUID().toString());
        user2.setPhoneNumber(firstRandom);
        assertThrows(Exception.class, () -> mySqlUserRepo.saveAndFlush(user2));
    }

    @Test
    public void testFindById_whenUserIdIsNotInDb_expectedOptional() {
        final Optional<User> byId = mySqlUserRepo.findById(UUID.randomUUID().toString());
        assertNotNull(byId);
        assertFalse(byId.isPresent());
    }

    @Test
    public void testFindById_whenUserExistsInDb_expectedUser() {
        //given 1 user and 20 random
        generateRandomUsers(20);
        final String id = UUID.randomUUID().toString();
        User user = generateRandomUser(id);
        final User dbUser = mySqlUserRepo.save(user);

        //when find by id
        final User actual = mySqlUserRepo.findById(id).orElse(null);

        //then
        assertEquals(dbUser, actual);
    }


    @Test
    public void testFindByIn_whenNoExistingIds_expectEmptyList() {
        //given random users
        generateRandomUsers(20);
        final List<User> byIdIn = mySqlUserRepo.findByIdIn(Arrays.asList("a", "b", "c", "d"));
        assertTrue(byIdIn.isEmpty());
    }

    @Test
    public void testFindByIn_whenPartialExistingIds_expectListWithPartial() {
        //given random users
        generateRandomUsers(20);
        mySqlUserRepo.save(generateRandomUser("a"));
        mySqlUserRepo.save(generateRandomUser("b"));
        final List<User> byIdIn = mySqlUserRepo.findByIdIn(Arrays.asList("a", "b", "c", "d"));
        assertEquals(byIdIn.size(), 2);
    }

    private void generateRandomUsers(int nr) {
        IntStream.range(0, nr)
                .forEach(n -> mySqlUserRepo.save(generateRandomUser(UUID.randomUUID().toString())));
    }


    private User generateRandomUser(String randomString) {
        User user = new User();
        user.setId(randomString);
        user.setUsername(randomString);
        user.setPhoneNumber(randomString);
        user.setEmail(randomString);
        return user;
    }

}
