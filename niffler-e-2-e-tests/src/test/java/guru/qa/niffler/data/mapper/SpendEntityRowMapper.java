package guru.qa.niffler.data.mapper;

import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.CurrencyValues;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpendEntityRowMapper implements RowMapper<SpendEntity> {

    public static final SpendEntityRowMapper instance = new SpendEntityRowMapper();

    private SpendEntityRowMapper() {
    }

    @Override
    public SpendEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        SpendEntity result = new SpendEntity();
        result.setId(rs.getObject("id", UUID.class));
        result.setAmount(rs.getDouble("amount"));
        result.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
        result.setDescription(rs.getString("description"));
        result.setSpendDate(rs.getDate("spend_date"));
        result.setUsername(rs.getString("username"));

        CategoryEntity category = new CategoryEntity();
        category.setId(rs.getObject("category_id", UUID.class));

        return result;    }
}
