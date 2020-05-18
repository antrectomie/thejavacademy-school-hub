package com.thejavacademy.userservice.service;

import com.thejavacademy.userservice.exception.UserServiceException;
import com.thejavacademy.userservice.model.dto.UserContact;
import com.thejavacademy.userservice.model.dto.UserIdentity;
import com.thejavacademy.userservice.model.dto.UserResponse;
import com.thejavacademy.userservice.model.entity.User;
import com.thejavacademy.userservice.repo.MySqlUserRepo;
import com.thejavacademy.userservice.service.adapters.UserStorageAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class UserServiceGetFriendsTest {


    @Autowired
    UserService userService;

    @MockBean
    UserStorageAdapter userStorageAdapter;

    @MockBean
    KafkaUserProducer kafkaUserProducer;

    @MockBean
    ESUserStorageAdapter esUserStorageAdapter;

    @MockBean
    MySqlUserRepo mySqlUserRepo;


    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService(UserStorageAdapter userStorageAdapter, KafkaUserProducer kafkaUserProducer, ESUserStorageAdapter esUserStorageAdapter) {
            return new UserService(userStorageAdapter, kafkaUserProducer,esUserStorageAdapter);
        }
    }


    @Test
    public void uerTest_getUserById(){
        String id = UUID.randomUUID().toString();
        User user = new User();
        user.setId(id);
        given(userStorageAdapter.getUserById(id)).willReturn(Optional.of(user));
        UserResponse userResponse1 = userService.getUser(id);
        assertEquals(userResponse1.getUserIdentity().getId(), id);

    }


    @Test
    public void test_whenIdNotFoundThenThrowException(){
        String id = UUID.randomUUID().toString();
        Mockito.when(mySqlUserRepo.findById(id)).thenReturn(null);
        Mockito.when(userStorageAdapter.getUserById(id)).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> userService.getUser(id));
    }

    @Test
    public void test_findByEmail(){
        String email = "test@yahoo.com";
        User user = new User();
        user.setEmail(email);
        Mockito.when(mySqlUserRepo.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(userStorageAdapter.getUserByEmail(email)).thenReturn(Optional.of(user));
        assertEquals(userService.getUserByEmail(email).get().getEmail(), email);
    }

    @Test
    public void test_getUsers(){
        List<UserIdentity> users = new ArrayList<>();
        Mockito.when(mySqlUserRepo.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(userStorageAdapter.getUsers()).thenReturn(createListOfUserIdentity());
        assertEquals(userService.getUsers().size(), 3);
    }

    @Test
    public void test_saveWhenUserEmpty_throwsException(){
        Mockito.when(userStorageAdapter.save(null)).thenThrow(UserServiceException.class);
        Assertions.assertThrows(Exception.class, () -> userService.save(null));
    }


    @Test
    public void test_saveWhenUserNotNull(){
        String id = UUID.randomUUID().toString();
        User saved = new User();
        saved.setId(id);
        Mockito.when(userStorageAdapter.save(new User())).thenReturn(saved);
        Assertions.assertEquals(saved.getId(), id);
    }


    @Test
    public void test_deleteWhenUserNull_throwsException(){
      //  Mockito.when(userStorageAdapter.deleteUser("")).thenThrow(UserServiceException.class);
       // Assertions.assertThrows(Exception.class, () -> userService.save(null));
    }







   // @Test
  //  public void givenStorageManagerReturnsEmptyFriends_whenGetFriends_expectedEmptyResponse() {
  //      given(userService.getUser("a")).willReturn(new UserResponse());

        //given(userStorageAdapter.getUserById("A")).willReturn(Optional.of(new User()));
//        final SearchUsersResponse expected = new SearchUsersResponse();
//        given(userStorageAdapter.getUserFriends("A")).willReturn(expected);
//        assertEquals(expected, userService.getFriends("A"));
//    }

//    @Test
//    public void testGetFriends() {
//        final SearchUsersResponse expectedFriends = buildSearchUserResponse(5);
//        given(userStorageAdapter.getUserById("A")).willReturn(Optional.of(new User()));
//        given(userStorageAdapter.getUserFriends("A")).willReturn(expectedFriends);
//        final SearchUsersResponse actualFriends = userService.getFriends("A");
//        Assertions.assertEquals(actualFriends, expectedFriends);
//    }

    @Test
    public void givenWronUserID_whenGetFriends_ExpectedThrownUserNotFound() {
        given(userStorageAdapter.getUserById("A")).willReturn(Optional.empty());
        assertThrowsType(UserServiceException.ExceptionType.USER_NOT_FOUND, () -> userService.getFriends("A"));
    }


    @Test
    public void givenStorageManagerThrowsRuntimeException_whenGetFriends_expectedThrownUserServiceException() {
        given(userStorageAdapter.getUserById("A")).willReturn(Optional.of(new User()));
        given(userStorageAdapter.getUserFriends("A")).willThrow(new RuntimeException());
        assertThrowsType(UserServiceException.ExceptionType.SERVER_ERROR, () -> userService.getFriends("A"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"     ", ""})
    @NullSource
    public void givenUserIdNull_whenGetFriends_expectedThrownUserServiceException(String id) {
        assertThrowsType(UserServiceException.ExceptionType.EMPTY_USER_ID, () -> userService.getFriends(id));
    }

    public void assertThrowsType(UserServiceException.ExceptionType type, Runnable runnable) {
        try {
            runnable.run();
            fail();
        } catch (UserServiceException ex) {
            assertEquals(type, ex.getType());
        }
    }

//    private List<UserIdentity> buildSearchUserResponse(int nrOfUsers) {
//        List<UserIdentity> users = new ArrayList<>();
//        for (int i = 0; i < nrOfUsers; i++) {
//            UserIdentity searchedUser = new UserIdentity();
//            final UserIdentity userIdentity = new UserIdentity();
//            userIdentity.setId(String.valueOf(i));
//            userIdentity.setFirstName(String.valueOf(i));
//            userIdentity.setLastName(String.valueOf(i));
//            userIdentity.setUsername(String.valueOf(i));
//            users.add(searchedUser);
//        }
//        final SearchUsersResponse searchUserResponse = new SearchUsersResponse();
//        searchUserResponse.setUsers(users);
//        return searchUserResponse;
//    }

    private UserIdentity generateUserIdentity(String id){

        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setId(id);
        userIdentity.setFirstName("Wayne");
        userIdentity.setLastName("John");
        userIdentity.setUsername("johnny");
        userIdentity.setEmail("aa@yahoo.com");
        userIdentity.setProfilePicture("path/to/picture");
        return userIdentity;
    }

    private UserContact generateUserContact(){
        UserContact userContact = new UserContact();
        userContact.setEmail("aaa@yahoo.com");
        userContact.setPhoneNumber("123456");
        return userContact;
    }

    private List<UserIdentity> createListOfUserIdentity(){
        UserIdentity u1 =new UserIdentity();
        u1.setId(UUID.randomUUID().toString());
        UserIdentity u2 =new UserIdentity();
        u2.setId(UUID.randomUUID().toString());
        UserIdentity u3 =new UserIdentity();
        u2.setId(UUID.randomUUID().toString());
        return  Arrays.asList(u1, u2, u3);
    }



}