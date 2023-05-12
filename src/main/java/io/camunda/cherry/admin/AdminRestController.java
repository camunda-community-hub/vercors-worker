/* ******************************************************************** */
/*                                                                      */
/*  AdminRestController                                                 */
/*                                                                      */
/*  Rest API for the admin application                                  */
/* example: http://localhost:8080/cherry/api/runtime/nbthreads          */

/* ******************************************************************** */
package io.camunda.cherry.admin;

import io.camunda.cherry.definition.AbstractRunner;
import io.camunda.cherry.runner.JobRunnerFactory;
import io.camunda.cherry.runtime.HistoryFactory;
import io.camunda.cherry.zeebe.ZeebeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cherry")
public class AdminRestController {

  Logger logger = LoggerFactory.getLogger(AdminRestController.class.getName());

  @Autowired
  JobRunnerFactory cherryJobRunnerFactory;

  @Autowired
  HistoryFactory historyFactory;

  @Autowired
  ZeebeConfiguration zeebeConfiguration;

  /**
   * Spring populate the list of all workers
   */
  @Autowired
  private List<AbstractRunner> listRunner;

  @GetMapping(value = "/api/ping", produces = "application/json")
  public Map<String, Object> ping() {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("timestamp", System.currentTimeMillis());
    return parameters;
  }

  @GetMapping(value = "/api/runtime/parameters", produces = "application/json")
  public Map<String, Object> getParameters() {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("zeebekindconnection", zeebeConfiguration.isCloudConfiguration() ? "SAAS" : "GATEWAY");
    parameters.put("gatewayaddress", zeebeConfiguration.getGatewayAddress());
    parameters.put("plaintext",
        zeebeConfiguration.isPlaintext() == null ? null : zeebeConfiguration.isPlaintext().toString());

    parameters.put("cloudregion", zeebeConfiguration.getRegion());
    parameters.put("cloudclusterid", zeebeConfiguration.getClusterId());
    parameters.put("cloudclientid", zeebeConfiguration.getClientId());
    parameters.put("cloudclientsecret", ""); // never send the client Secret

    // we don't want the configuration here, but the running information
    parameters.put("maxjobsactive", cherryJobRunnerFactory.getMaxJobActive());
    parameters.put("nbthreads", cherryJobRunnerFactory.getNumberOfThreads());
    return parameters;
  }

  @GetMapping(value = "/api/runtime/threads", produces = "application/json")
  public Integer getNumberOfThreads() {
    return cherryJobRunnerFactory.getNumberOfThreads();
  }

  @PutMapping(value = "/api/runtime/setthreads", produces = "application/json")
  public void setNumberOfThread(@RequestParam(name = "threads") Integer numberOfThreads) {
    cherryJobRunnerFactory.setNumberOfThreads(numberOfThreads);
  }

}
