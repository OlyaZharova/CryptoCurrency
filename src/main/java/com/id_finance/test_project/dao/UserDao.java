package com.id_finance.test_project.dao;

import com.id_finance.test_project.entity.User;
import com.id_finance.test_project.model.UserInfo;

import java.util.List;

public interface UserDao {

    List<UserInfo> showAll();

    void createUser(User user);
}
