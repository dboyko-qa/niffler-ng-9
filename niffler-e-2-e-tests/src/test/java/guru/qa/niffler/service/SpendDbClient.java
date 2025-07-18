package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.CategoryDao;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.dao.impl.SpendDaoJdbc;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.SpendJson;

import java.sql.Connection;

import static guru.qa.niffler.data.Databases.transaction;

public class SpendDbClient {

  private static final Config CFG = Config.getInstance();

  public SpendJson createSpend(SpendJson spend) {
    return transaction(connection -> {
              SpendEntity spendEntity = SpendEntity.fromJson(spend);
              if (spendEntity.getCategory().getId() == null) {
                  CategoryDao categoryDao = new CategoryDaoJdbc(connection);
                  categoryDao.findCategoryByUsernameAndCategoryName(spendEntity.getUsername(), spendEntity.getCategory().getName())
                          .ifPresentOrElse(entity -> spendEntity.setCategory(entity),
                                  () -> {
                                      CategoryEntity categoryEntity = categoryDao.create(spendEntity.getCategory());
                                      spendEntity.setCategory(categoryEntity);
                                  });
              }
              return SpendJson.fromEntity(
                      new SpendDaoJdbc(connection).create(spendEntity)
              );
            },
            CFG.spendJdbcUrl(),
            Connection.TRANSACTION_SERIALIZABLE
    );
  }
}
