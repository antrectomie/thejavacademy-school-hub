//package com.thejavacademy.userservice.service;
//
//import com.thejavacademy.userservice.exception.UserServiceException;
//import com.thejavacademy.userservice.model.dto.UserIdentity;
//import com.thejavacademy.userservice.model.entity.User;
//import com.thejavacademy.userservice.service.adapters.UserStorageAdapter;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.NullSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(SpringExtension.class)
//class UserServiceGetFriendsTest {
//
//    @TestConfiguration
//    static class TestConfig {
//        @Bean
//        public UserService userService(UserStorageAdapter userStorageAdapter, KafkaUserProducer kafkaUserProducer) {
//            return new UserService(userStorageAdapter, kafkaUserProducer);
//        }
//    }
//
//    @Autowired
//    UserService userService;
//
//    @MockBean
//    UserStorageAdapter userStorageAdapter;
//
//    @MockBean
//    KafkaUserProducer kafkaUserProducer;
//
//    @Test
//    public void givenStorageManagerReturnsEmptyFriends_whenGetFriends_expectedEmptyResponse() {
//        given(userStorageAdapter.getUserById("A")).willReturn(Optional.of(new User()));
//        final SearchUsersResponse expected = new SearchUsersResponse();
//        given(userStorageAdapter.getUserFriends("A")).willReturn(expected);
//        assertEquals(expected, userService.getFriends("A"));
//    }
//
//    @Test
//    public void testGetFriends() {
//        final SearchUsersResponse expectedFriends = buildSearchUserResponse(5);
//        given(userStorageAdapter.getUserById("A")).willReturn(Optional.of(new User()));
//        given(userStorageAdapter.getUserFriends("A")).willReturn(expectedFriends);
//        final SearchUsersResponse actualFriends = userService.getFriends("A");
//        Assertions.assertEquals(actualFriends, expectedFriends);
//    }
//
//    @Test
//    public void givenWronUserID_whenGetFriends_ExpectedThrownUserNotFound() {
//        given(userStorageAdapter.getUserById("A")).willReturn(Optional.empty());
//        assertThrowsType(UserServiceException.ExceptionType.USER_NOT_FOUND, () -> userService.getFriends("A"));
//    }
//
//
//    @Test
//    public void givenStorageManagerThrowsRuntimeException_whenGetFriends_expectedThrownUserServiceException() {
//        given(userStorageAdapter.getUserById("A")).willReturn(Optional.of(new User()));
//        given(userStorageAdapter.getUserFriends("A")).willThrow(new RuntimeException());
//        assertThrowsType(UserServiceException.ExceptionType.SERVER_ERROR, () -> userService.getFriends("A"));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"     ", ""})
//    @NullSource
//    public void givenUserIdNull_whenGetFriends_expectedThrownUserServiceException(String id) {
//        assertThrowsType(UserServiceException.ExceptionType.EMPTY_USER_ID, () -> userService.getFriends(id));
//    }
//
//    public void assertThrowsType(UserServiceException.ExceptionType type, Runnable runnable) {
//        try {
//            runnable.run();
//            fail();
//        } catch (UserServiceException ex) {
//            assertEquals(type, ex.getType());
//        }
//    }
//
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
//
//}