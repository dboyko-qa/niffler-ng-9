package guru.qa.niffler.data.mapper;

import guru.qa.niffler.data.entity.auth.AuthorityEntity;
import guru.qa.niffler.data.entity.auth.Authority;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthorityEntityRowMapper implements RowMapper<AuthorityEntity> {

  public static final AuthorityEntityRowMapper instance = new AuthorityEntityRowMapper();

  private AuthorityEntityRowMapper() {
  }

  @Override
  public AuthorityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    AuthorityEntity result = new AuthorityEntity();
    result.setId(rs.getObject("id", UUID.class));
    result.setAuthority(Authority.valueOf(rs.getString("authority")));
//    result.setUserId(rs.getObject("user_id", UUID.class));
    return result;
  }
}
