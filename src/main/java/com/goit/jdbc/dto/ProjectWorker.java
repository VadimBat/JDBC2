package com.goit.jdbc.dto;

public class ProjectWorker {
    private final int projectID;
    private final int workerID;

    public ProjectWorker(int projectID, int workerID) {
        this.projectID = projectID;
        this.workerID = workerID;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getWorkerID() {
        return workerID;
    }
}
