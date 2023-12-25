package com.practice.transaction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.transaction.entity.UserInfo;
import com.practice.transaction.repository.UserRepository;

public interface UserService {

	List<UserInfo> getUsers();

	void addUser(final UserInfo user);

}
