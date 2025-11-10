package org.cams.mutualfund.management.repository;

import java.util.Optional;

import org.cams.mutualfund.management.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> getReferenceByUsername(String username);
    void deleteByUsername(String username);
}
