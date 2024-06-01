package com.practice.transaction.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.practice.transaction.entity.UserInfo;

@SpringBootTest
@ActiveProfiles("local")
class UserServiceTest {

	@Autowired
	private UserServiceImpl sut;

	@Test
	@DisplayName("slave db 에는 데이터가 없기 때문에 빈 리스트가 리턴")
	void test_1() {
		// when

		// given
		final List<UserInfo> users = sut.getUsers();

		// then
		assertTrue(users.isEmpty());
	}

	@Test
	@DisplayName("master db 에만 데이터 삽입 성공")
	void test_2() {
		// when
		final UserInfo user = new UserInfo();
		user.setId("lkhlkh");
		user.setPassword("1234");
		user.setName("dob");

		// given
		sut.addUser(user);

		// then
	}

}