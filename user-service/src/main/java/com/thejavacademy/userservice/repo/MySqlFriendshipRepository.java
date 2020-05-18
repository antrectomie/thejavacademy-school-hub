package com.thejavacademy.userservice.repo;

import com.thejavacademy.userservice.model.entity.Friendship;
import com.thejavacademy.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface MySqlFriendshipRepository extends JpaRepository<Friendship, String> {
    @Query(value = "SELECT f from Friendship f WHERE userOneId = ?1 or userTwoId = ?1")
    //TODO check if friendship is accepted
    List<Friendship> findUserFriendhips(String id);
    @Query(value = "SELECT f from Friendship f WHERE userOneId = ?1 and userTwoId = ?2")
    @Transactional(propagation = Propagation.MANDATORY)
    Optional<Friendship> findFriendship(String userOneId, String userTwoId);
}
