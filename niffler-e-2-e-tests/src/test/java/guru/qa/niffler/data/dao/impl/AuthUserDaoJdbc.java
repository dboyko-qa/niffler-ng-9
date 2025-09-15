package guru.qa.niffler.data.dao.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.AuthUserDao;
import guru.qa.niffler.data.entity.auth.AuthUserEntity;
import guru.qa.niffler.data.mapper.AuthUserEntityRowMapper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.jdbc.Connections.holder;

@ParametersAreNonnullByDefault
public class AuthUserDaoJdbc implements AuthUserDao {
    private static final Config CFG = Config.getInstance();
    private static final String URL = CFG.authJdbcUrl();


    @Nonnull
    @SuppressWarnings("resource")
    @Override
    public AuthUserEntity create(AuthUserEntity user) {
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(
                "INSERT INTO user (username, account_non_expired, account_non_locked, credentials_non_expired, enabled, password) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setString(1, user.getUsername());
            ps.setBoolean(2, user.getAccountNonExpired());
            ps.setBoolean(3, user.getAccountNonLocked());
            ps.setBoolean(4, user.getCredentialsNonExpired());
            ps.setBoolean(5, user.getEnabled());
            ps.setString(6, user.getPassword());

            ps.executeUpdate();

            final UUID generatedKey;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedKey = rs.getObject("id", UUID.class);
                } else {
                    throw new SQLException("Can`t find id in ResultSet");
                }
            }
            user.setId(generatedKey);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Nonnull
    @SuppressWarnings("resource")
    @Override
    public Optional<AuthUserEntity> findById(UUID id) {
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement("SELECT * FROM \"user\" WHERE id = ?")) {
            ps.setObject(1, id);

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                if (rs.next()) {
                    AuthUserEntity result = new AuthUserEntity();
                    result.setId(rs.getObject("id", UUID.class));
                    result.setUsername(rs.getString("username"));
                    result.setPassword(rs.getString("password"));
                    result.setEnabled(rs.getBoolean("enabled"));
                    result.setAccountNonExpired(rs.getBoolean("account_non_expired"));
                    result.setAccountNonLocked(rs.getBoolean("account_non_locked"));
                    result.setCredentialsNonExpired(rs.getBoolean("credentials_non_expired"));
                    return Optional.ofNullable(result);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthUserEntity getAuthUserEntityFromResultSet(ResultSet rs) throws SQLException {
        AuthUserEntity result = new AuthUserEntity();
        result.setId(rs.getObject("id", UUID.class));
        result.setUsername(rs.getString("username"));
        result.setPassword(rs.getString("password"));
        result.setEnabled(rs.getBoolean("enabled"));
        result.setAccountNonExpired(rs.getBoolean("account_non_expired"));
        result.setAccountNonLocked(rs.getBoolean("account_non_locked"));
        result.setCredentialsNonExpired(rs.getBoolean("credentials_non_expired"));
        return result;
    }

    @Nonnull
    @SuppressWarnings("resource")
    @Override
    public List<AuthUserEntity> findAll() {
        try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(
                "SELECT * from user")){
            ps.execute();
            List<AuthUserEntity> resultList = new ArrayList<>();
            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    AuthUserEntity ce = getAuthUserEntityFromResultSet(rs);
                    resultList.add(ce);
                }
                return resultList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    @SuppressWarnings("resource")
    @Override
    public Optional<AuthUserEntity> findByUsername(String username) {
        try (PreparedStatement ps = holder(URL).connection().prepareStatement("SELECT * FROM \"user\" WHERE username = ?")) {
            ps.setString(1, username);

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {

                if (rs.next()) {
                    return Optional.ofNullable(
                            AuthUserEntityRowMapper.instance.mapRow(rs, rs.getRow())
                    );
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
