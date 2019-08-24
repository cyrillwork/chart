package com.cyrillwork.chart.repos;

import com.cyrillwork.chart.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends JpaRepository<User, Long>
{
    User findUserByUsername(String username);

    @Modifying
    @Transactional
    void deleteUserByUsername(String username);

//    @Modifying
//    @Transactional
//    @Query("delete from User u where u.username = ?1")
//    void deleteMyUsername(String name);

}
