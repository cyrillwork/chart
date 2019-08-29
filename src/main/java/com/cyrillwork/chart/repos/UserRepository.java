package com.cyrillwork.chart.repos;

import com.cyrillwork.chart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends JpaRepository<User, Long>
{
    User findUserByUsername(String username);

    @Modifying
    @Transactional
    void deleteUserByUsername(String username);
}
