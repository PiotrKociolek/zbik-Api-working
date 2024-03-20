package pl.pkociolek.zbik.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "pl.pkociolek.zbik.repository")
public class MongoConfiguration {
  @Bean
  MongoTransactionManager transactionManager(final MongoDatabaseFactory dbFactory) {
    return new MongoTransactionManager(dbFactory);
  }
}
