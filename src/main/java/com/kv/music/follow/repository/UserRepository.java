package com.kv.music.follow.repository;

import com.kv.music.follow.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findUserByUserId(String userId);
}
