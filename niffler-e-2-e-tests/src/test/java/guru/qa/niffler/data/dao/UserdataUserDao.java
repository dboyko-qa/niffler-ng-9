package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserdataUserDao {
    UserEntity create (UserEntity user);
    Optional<UserEntity> findById(UUID id);
    Optional<UserEntity> findByUsername (String username);
    void delete (UserEntity user);
    List<UserEntity> findAll();
}
