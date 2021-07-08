package com.thejavacademy.userservice.service.adapters;

import com.thejavacademy.userservice.exception.UserServiceException;
import com.thejavacademy.userservice.mapper.UserMapper;
import com.thejavacademy.userservice.model.dto.UserIdentity;
import com.thejavacademy.userservice.model.entity.Friendship;
import com.thejavacademy.userservice.model.entity.User;
import com.thejavacademy.userservice.repo.MySqlUserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MysqlUserStorageAdapter implements UserStorageAdapter {

    private MySqlUserRepo userRepo;
    private MysqlFriendshipStorageAdapter friendshipStorageAdapter;

    public MysqlUserStorageAdapter(MySqlUserRepo userRepo, MysqlFriendshipStorageAdapter friendshipStorageAdapter) {
        this.userRepo = userRepo;
        this.friendshipStorageAdapter = friendshipStorageAdapter;
    }

    @Override
    public Optional<User> getUserById(String id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserServiceException(UserServiceException.ExceptionType.USER_NOT_FOUND));
        return Optional.of(user);
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new UserServiceException(UserServiceException.ExceptionType.USER_NOT_FOUND);
        }
        if(user.getId() == null || user.getId().trim().isEmpty()){
            user.setId(UUID.randomUUID().toString());
        }
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserServiceException(UserServiceException
                .ExceptionType.USER_NOT_FOUND));
        userRepo.delete(user);
    }

    //TODO:delete this method after testing
    @Override
    public List<UserIdentity> getUsers() {
        List<User>users = userRepo.findAll();
        return  users.stream()
                .map(user -> UserMapper.buildUserIdentity(user))
                .collect(Collectors.toList());
    }



    @Override
    public List<UserIdentity> getUserFriends(String id) {


        List<Friendship> listOfFriendships = friendshipStorageAdapter.getFriendships(id);
        List<String> usersIds = extractFriendsIds(listOfFriendships, id);
        List<User> listOfFriends = userRepo.findByIdIn(usersIds);
        return listOfFriends.stream()
                .map(UserMapper::entityToDto)
                .collect(Collectors.toList());

    }

    /**
     * @param -friendshipList
     * @param -idOfUserWhichWeAreSearchingFriendsFor - index which we are searching friends for
     * @return returneza indexul prietenilor lui idOfUserWhichWeAreSearchingFriendsFor
     */
    private List<String> extractFriendsIds(List<Friendship> friendshipList, String currentUserId) {
        return friendshipList.stream()
                .map(friendship -> friendship.getFriendId(currentUserId))
                .collect(Collectors.toList());
    }
}
