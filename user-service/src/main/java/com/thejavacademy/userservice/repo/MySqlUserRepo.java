package com.thejavacademy.userservice.repo;

import com.thejavacademy.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MySqlUserRepo extends JpaRepository<User, String> {

    List<User> findByIdIn(List<String> ids);

}
