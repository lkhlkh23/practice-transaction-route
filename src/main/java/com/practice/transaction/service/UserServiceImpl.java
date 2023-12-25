package com.practice.transaction.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.transaction.entity.UserInfo;
import com.practice.transaction.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<UserInfo> getUsers() {
		final List<UserInfo> users = repository.findAll();
		users.forEach(u -> u.setName("changed -" + u.getName()));
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void addUser(final UserInfo user) {
		repository.save(user);
	}

}
