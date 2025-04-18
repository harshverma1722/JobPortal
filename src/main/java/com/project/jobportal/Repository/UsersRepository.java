package com.project.jobportal.Repository;


import com.project.jobportal.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Interface to use spring data jpa repository
public interface UsersRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByEmail(String email);
}
