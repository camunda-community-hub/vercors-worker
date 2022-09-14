/* ******************************************************************** */
/*                                                                      */
/*  WorkerInformation                                                   */
/*                                                                      */
/*  Collect worker information from a worker.                           */
/*  This class works as a facade. It's easy then to get the JSON        */
/*  from this object                                                    */
/* ******************************************************************** */
package org.camunda.cherry.admin;

import org.camunda.cherry.definition.AbstractRunner;
import org.camunda.cherry.definition.AbstractWorker;
import org.camunda.cherry.definition.BpmnError;
import org.camunda.cherry.definition.RunnerParameter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RunnerInformation {


    private AbstractRunner runner;

    private boolean active;

    private boolean displayLogo = true;

    /**
     * Keep the runner in the class. This class is a facade
     *
     * @param runner
     * @return
     */
    public static RunnerInformation getRunnerInformation(AbstractRunner runner) {
        RunnerInformation workerInformation = new RunnerInformation();
        workerInformation.runner = runner;
        return workerInformation;
    }

    /**
     * Get all information on runner
     */
    public String getName() {
        return runner.getIdentification();
    }

    public String getLabel() {
        return runner.getLabel();
    }

    public String getDisplayLabel() {
        return runner.getDisplayLabel();
    }

    public String getType() {
        return runner.getType();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<RunnerParameter> getListInput() {
        return runner.getListInput();
    }

    public List<RunnerParameter> getListOutput() {
        return runner.getListOutput();
    }

    public List<BpmnError> getListBpmnErrors() {
        return runner.getListBpmnErrors();
    }

    public String getClassName() {
        return runner.getClass().getName();
    }

    public String getDescription() {
        return runner.getDescription();
    }

    public String getLogo() {
        return displayLogo ? runner.getLogo() : null;
    }

    public TYPE_RUNNER getTypeRunner() {
        return runner instanceof AbstractWorker ? TYPE_RUNNER.WORKER : TYPE_RUNNER.CONNECTOR;
    }

    public void setDisplayLogo(boolean displayLogo) {
        this.displayLogo = displayLogo;
    }

    /**
     * If the runner has definition error, is will not be possible to start it
     *
     * @return the list of errors
     */
    public String getDefinitionErrors() {
        return String.join(", ", runner.getDefinitionErrors());
    }

    public enum TYPE_RUNNER {WORKER, CONNECTOR}

}
