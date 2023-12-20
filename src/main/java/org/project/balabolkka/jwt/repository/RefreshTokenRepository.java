package org.project.balabolkka.jwt.repository;

import org.project.balabolkka.jwt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsRefreshTokenByEmail(String email);

    void deleteRefreshTokenByEmail(String email);

}
