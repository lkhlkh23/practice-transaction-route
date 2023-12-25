package com.practice.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.transaction.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, String> {
}
