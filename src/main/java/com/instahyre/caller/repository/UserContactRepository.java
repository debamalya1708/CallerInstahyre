package com.instahyre.caller.repository;

import com.instahyre.caller.model.ContactResponse;
import com.instahyre.caller.model.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserContactRepository extends JpaRepository<UserContact,Long> {

    public List<UserContact> findByContactNameContainingOrderByContactName(String name);

    List<UserContact> findByContactNumberOrderByContactName(String contact);
}
