package com.cyrillwork.chart.repos;

import com.cyrillwork.chart.SignUpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import com.cyrillwork.chart.Message;

import javax.transaction.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends JpaRepository<SignUpUser, Long> {

    SignUpUser findSignUpUserByUsername(String username);

}
