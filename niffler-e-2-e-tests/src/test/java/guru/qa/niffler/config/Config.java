package guru.qa.niffler.config;

import javax.annotation.Nonnull;

public interface Config {

  @Nonnull
  static Config getInstance() {
    return LocalConfig.INSTANCE;
  }

  @Nonnull
  String frontUrl();

  @Nonnull
  String authUrl();
  String defaultUser();
  String defaultPassword();

  String authJdbcUrl();
  @Nonnull
  String gatewayUrl();
  @Nonnull
  String userdataUrl();
  @Nonnull
  String userdataJdbcUrl();
  @Nonnull
  String spendUrl();
  @Nonnull
  String spendJdbcUrl();
  @Nonnull
  String currencyJdbcUrl();
  @Nonnull
  default String ghUrl() {
    return "https://api.github.com/";
  }
}
