package com.ssafy.fullerting.user.repository;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser,Long> {

    Optional<CustomUser> findByEmail(String email);


}
