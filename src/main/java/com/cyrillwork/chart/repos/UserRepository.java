package com.example.mvc.repos;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import com.example.mvc.Message;

import javax.transaction.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends CrudRepository<Message, Integer> {

    @Modifying
    @Transactional
    void deleteMessagesByName(String name);
}
