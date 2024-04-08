package com.plume.backend.api.auth.repository.jpa;

import com.plume.backend.api.auth.domain.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthEntity,Long> {

    Optional<AuthEntity> findByUserIdAndDelYn(String userId, String delYn);

}
