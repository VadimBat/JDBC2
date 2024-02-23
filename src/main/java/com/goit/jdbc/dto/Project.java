package com.goit.jdbc.dto;

import java.sql.Date;

public class Project {
    private final int id;
    private final int clientId;
    private final Date startDate;
    private final Date finishDate;

    public Project(int id, int clientId, Date startDate, Date finishDate) {
        this.id = id;
        this.clientId = clientId;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}
