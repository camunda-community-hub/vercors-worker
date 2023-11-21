/* ******************************************************************** */
/*                                                                      */
/*  SecretProvider                                                      */
/*                                                                      */
/*  Manage secret inside the framework                                  */
/*  There is two secret available:                                      */
/*  - one managed directly from the framework, and saved in the database*/
/*  - one given as a ConfigMap via the Docker configuration             */
/*  TO BE IMPLEMENTED (both)                                            */
/* ******************************************************************** */
package io.camunda.cherry.runtime;

import io.camunda.cherry.db.entity.KeyValueEntity;
import io.camunda.cherry.secretenv.SecretEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Configuration
public class SecretProvider implements io.camunda.connector.api.secret.SecretProvider {

  @Autowired
  SecretEnvService secretEnvService;

  @Override
  public String getSecret(String key) {

    Optional<KeyValueEntity> keyValue = secretEnvService.getKeyValue(KeyValueEntity.KeyValueType.SECRET, key);
    if (keyValue.isPresent()) {
      return keyValue.get().valueKey;
    }
    return null;
  }
}
