package com.ssafy.fullerting.user.repository;
// import com.ssafy.fullerting.user.model.entity.Member;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberProfile,Long> {
    Optional<MemberProfile> findByEmail(String email);
    Optional<MemberProfile> findByEmailAndAuthProvider(String email, String provider);

    Optional<MemberProfile> findByNickname(String bidderUserName);
}
