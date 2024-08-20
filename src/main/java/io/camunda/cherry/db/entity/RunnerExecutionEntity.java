package io.camunda.cherry.db.entity;

import io.camunda.cherry.definition.AbstractRunner;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "ChRunnerexecution")
public class RunnerExecutionEntity {

  @Column(name = "type_executor", length = 10)
  @Enumerated(EnumType.STRING)
  public TypeExecutor typeExecutor;

  @Column(name = "runner_type", length = 1000)
  public String runnerType;

  /**
   * execution (in UTC) Instant will saved the date.. in the local timezone (example 15:04), that we
   * don't want (and make no sense) We want to save the date in UTC, so let's use a LocalDateTime,
   * and the code is reponsible to provide this time in the UTC time zone.
   */
  @Column(name = "execution_time")
  public LocalDateTime executionTime;

  @Column(name = "execution_ms")
  public Long executionMs;

  @Column(name = "status", length = 100)
  @Enumerated(EnumType.STRING)
  public AbstractRunner.ExecutionStatusEnum status;

  @Column(name = "error_code", length = 100)
  public String errorCode;

  @Column(name = "error_explanation", length = 500)
  public String errorExplanation;

  @Id
  @SequenceGenerator(name = "seqexecution", sequenceName = "seqexecution", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  public enum TypeExecutor {
    CONNECTOR, WORKER, WATCHER
  }

  // Save OperationLog information: https://github.com/janzyka/blobs-jpa/

}
