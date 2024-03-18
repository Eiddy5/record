package org.record.mode.chain.checker;

import org.record.mode.chain.model.TaskEntry;

public abstract class SimpleTaskChecker implements TaskChecker {
    private TaskChecker nextTaskChecker;

    public TaskChecker setNextChecker(TaskChecker taskChecker) {
        this.nextTaskChecker = taskChecker;
        return nextTaskChecker;
    }

    @Override
    public void doCheck(TaskEntry taskEntry) {
        check(taskEntry);
        if (nextTaskChecker != null) {
            nextTaskChecker.doCheck(taskEntry);
        }
    }

    public abstract void check(TaskEntry taskEntry);
}
