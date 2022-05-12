package org.camunda.vercors.ping;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.camunda.vercors.definition.AbstractWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Component
public class PingWorker extends AbstractWorker {

    Logger logger = LoggerFactory.getLogger(PingWorker.class.getName());

    private final static String INPUT_MESSAGE ="message";
    private final static String INPUT_DELAY ="delay";
    private final static String OUTPUT_TIMESTAMP ="timestamp";

    public PingWorker() {
        super("v-ping",
                Arrays.asList(
                        WorkerParameter.getInstance(INPUT_MESSAGE, String.class, Level.OPTIONAL),
                        WorkerParameter.getInstance(INPUT_DELAY, Long.class, Level.OPTIONAL)),

                Arrays.asList(
                        WorkerParameter.getInstance(OUTPUT_TIMESTAMP, String.class, Level.REQUIRED))
                        );
    }

    @ZeebeWorker(type = "v-ping", autoComplete = true)
    public void handleWorkerExecution(final JobClient jobClient, final ActivatedJob activatedJob) {
        super.handleWorkerExecution(jobClient, activatedJob);
    }


    public void execute(final JobClient jobClient, final ActivatedJob activatedJob) {
        String message= getInputStringValue(INPUT_MESSAGE, null, activatedJob);
        Long delay = getInputLongValue(INPUT_DELAY, null,activatedJob);
        logger.info("Vercors-Ping ["+message+"]");
        if (delay!=null) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                // Nothing to do here
            }
        }
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String formattedDate = formatter.format( new Date());
        setValue(OUTPUT_TIMESTAMP, formattedDate);
    }
}
