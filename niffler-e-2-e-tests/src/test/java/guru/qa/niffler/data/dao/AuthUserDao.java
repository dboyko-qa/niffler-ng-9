package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.auth.AuthUserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthUserDao {
    AuthUserEntity create(AuthUserEntity authority);
    Optional<AuthUserEntity> findById(UUID id);
    List<AuthUserEntity> findAll();
}
