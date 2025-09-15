package guru.qa.niffler.data.repository.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.auth.AuthUserEntity;
import guru.qa.niffler.data.entity.auth.Authority;
import guru.qa.niffler.data.entity.auth.AuthorityEntity;
import guru.qa.niffler.data.mapper.AuthUserEntityRowMapper;
import guru.qa.niffler.data.repository.AuthUserRepository;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.jdbc.Connections.holder;

@ParametersAreNonnullByDefault
public class AuthUserRepositoryJdbc implements AuthUserRepository {

  private static final Config CFG = Config.getInstance();
  private static final String URL = CFG.authJdbcUrl();

  @Nonnull
  @Override
  public AuthUserEntity create(AuthUserEntity user) {
    try (PreparedStatement userPs = holder(URL).connection().prepareStatement(
        "INSERT INTO \"user\" (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) " +
            "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
         PreparedStatement authorityPs = holder(CFG.authJdbcUrl()).connection().prepareStatement(
             "INSERT INTO \"authority\" (user_id, authority) VALUES (?, ?)")) {
      userPs.setString(1, user.getUsername());
      userPs.setString(2, user.getPassword());
      userPs.setBoolean(3, user.getEnabled());
      userPs.setBoolean(4, user.getAccountNonExpired());
      userPs.setBoolean(5, user.getAccountNonLocked());
      userPs.setBoolean(6, user.getCredentialsNonExpired());

      userPs.executeUpdate();

      final UUID generatedKey;
      try (ResultSet rs = userPs.getGeneratedKeys()) {
        if (rs.next()) {
          generatedKey = rs.getObject("id", UUID.class);
        } else {
          throw new SQLException("Can`t find id in ResultSet");
        }
      }
      user.setId(generatedKey);

      for (AuthorityEntity a : user.getAuthorities()) {
        authorityPs.setObject(1, generatedKey);
        authorityPs.setString(2, a.getAuthority().name());
        authorityPs.addBatch();
        authorityPs.clearParameters();
      }
      authorityPs.executeBatch();
      return user;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Nonnull
  @Override
  public AuthUserEntity update(AuthUserEntity user) {
    try (PreparedStatement userPs = holder(URL).connection().prepareStatement(
            "UPDATE \"user\" (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) " +
                    "VALUES (?, ?, ?, ?, ?, ?) where id = ?", PreparedStatement.RETURN_GENERATED_KEYS);
         PreparedStatement authorityPs = holder(CFG.authJdbcUrl()).connection().prepareStatement(
                 "UPDATE \"authority\" (user_id, authority) VALUES (?, ?) where id = ?")) {
      userPs.setString(1, user.getUsername());
      userPs.setString(2, user.getPassword());
      userPs.setBoolean(3, user.getEnabled());
      userPs.setBoolean(4, user.getAccountNonExpired());
      userPs.setBoolean(5, user.getAccountNonLocked());
      userPs.setBoolean(6, user.getCredentialsNonExpired());
      userPs.setObject(7, user.getId());

      userPs.executeUpdate();

      for (AuthorityEntity a : user.getAuthorities()) {
        authorityPs.setObject(1, user.getId());
        authorityPs.setString(2, a.getAuthority().name());
        authorityPs.setObject(2, a.getId());
        authorityPs.addBatch();
        authorityPs.clearParameters();
      }
      authorityPs.executeBatch();
      return user;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Nonnull
  @Override
  public Optional<AuthUserEntity> findById(UUID id) {
    try (PreparedStatement ps = holder(URL).connection().prepareStatement(
        "select * from \"user\" u join authority a on u.id = a.user_id where u.id = ?"
    )) {
      ps.setObject(1, id);

      ps.execute();

      try (ResultSet rs = ps.getResultSet()) {
        AuthUserEntity user = null;
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        while (rs.next()) {
          if (user == null) {
            user = AuthUserEntityRowMapper.instance.mapRow(rs, 1);
          }

          AuthorityEntity ae = new AuthorityEntity();
          ae.setUser(user);
          ae.setId(rs.getObject("a.id", UUID.class));
          ae.setAuthority(Authority.valueOf(rs.getString("authority")));
          authorityEntities.add(ae);
        }
        if (user == null) {
          return Optional.empty();
        } else {
          user.setAuthorities(authorityEntities);
          return Optional.of(user);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Nonnull
  @Override
  public Optional<AuthUserEntity> findByUsername(String username) {
    try (PreparedStatement ps = holder(URL).connection().prepareStatement(
        "select * from \"user\" u join authority a on u.id = a.user_id where u.username = ?"
    )) {
      ps.setString(1, username);

      ps.execute();

      try (ResultSet rs = ps.getResultSet()) {
        AuthUserEntity user = null;
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        while (rs.next()) {
          if (user == null) {
            user = AuthUserEntityRowMapper.instance.mapRow(rs, 1);
          }

          AuthorityEntity ae = new AuthorityEntity();
          ae.setUser(user);
          ae.setId(rs.getObject("a.id", UUID.class));
          ae.setAuthority(Authority.valueOf(rs.getString("authority")));
          authorityEntities.add(ae);
        }
        if (user == null) {
          return Optional.empty();
        } else {
          user.setAuthorities(authorityEntities);
          return Optional.of(user);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void remove(AuthUserEntity user) {
    try (PreparedStatement userPs = holder(URL).connection().prepareStatement(
            "DELETE FROM \"user\" where id = ?");
         PreparedStatement authorityPs = holder(CFG.authJdbcUrl()).connection().prepareStatement(
                 "DELETE FROM \"authority\" where id = ?")) {
      userPs.setObject(1, user.getId());

      userPs.executeUpdate();

      for (AuthorityEntity a : user.getAuthorities()) {
        authorityPs.setObject(1, a.getId());
        authorityPs.addBatch();
        authorityPs.clearParameters();
      }
      authorityPs.executeBatch();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
