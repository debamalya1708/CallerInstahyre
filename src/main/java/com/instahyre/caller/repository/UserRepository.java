package com.instahyre.caller.repository;

import com.instahyre.caller.model.User;
import com.instahyre.caller.model.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.vavr.control.Option;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Option<User> findByPhone(String phone);
    Option<User> findByEmail(String email);

    List<User> findByPhoneOrderByName(String contact);

//    Option<User> findByMobileOrEmail(String mobile, String email);

}
