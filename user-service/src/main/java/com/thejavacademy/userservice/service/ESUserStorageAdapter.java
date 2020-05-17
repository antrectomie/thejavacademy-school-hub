package com.thejavacademy.userservice.service;

import com.thejavacademy.userservice.mapper.UserMapper;
import com.thejavacademy.userservice.model.UserElastic;
import com.thejavacademy.userservice.model.dto.UserIdentity;
import com.thejavacademy.userservice.model.entity.User;
import com.thejavacademy.userservice.repo.UserElasticsearchRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ESUserStorageAdapter {


    private UserElasticsearchRepo userElasticsearchRepo;

    public ESUserStorageAdapter(UserElasticsearchRepo userElasticsearchRepo) {
        this.userElasticsearchRepo = userElasticsearchRepo;
    }

    public void save(User user) {
        final UserElastic userElastic = UserMapper.toUserElastic(user);
        userElasticsearchRepo.save(user.getId(), userElastic);
    }

    public List<UserIdentity> searchUsers(String term) {
        List<UserElastic> users = userElasticsearchRepo.search(term);

        return users.stream()
                .map(UserMapper::toUserIdentityDto)
                .collect(Collectors.toList());
    }
}
