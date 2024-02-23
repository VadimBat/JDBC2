package com.goit.jdbc.storage;


import com.goit.jdbc.dto.Client;
import com.goit.jdbc.dto.Project;
import com.goit.jdbc.dto.ProjectWorker;
import com.goit.jdbc.dto.Worker;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DatabasePopulateService {
    private static Connection connection = Database.getInstance().getConnection();
    private static String insertIntoWorker = "INSERT INTO worker(id,name,birthday,level,salary) VALUES(?, ?, ?, ?, ?);";
    private static String insertIntoClient = "INSERT INTO client (id,name) VALUES(?,?);";
    private static String insertIntoProject = "INSERT INTO project(id,client_id, start_date, finish_date) VALUES(?, ?, ?, ?)";
    private static String insertIntoProjectWorker = "INSERT INTO project_worker(project_id, worker_id) VALUES(?, ?);";


    private static List<Worker> insertWorker = new ArrayList<>();
    private static List<Client> insertClient = new ArrayList<>();
    private static List<Project> insertProject = new ArrayList<>();
    private static List<ProjectWorker> insertProjectWorker = new ArrayList<>();

    static {
        insertWorker.add(new Worker(1, "Dave", LocalDate.of(2001, 9, 7), "Trainee", 150));
        insertWorker.add(new Worker(2, "John", LocalDate.of(1998, 3, 7), "Junior", 500));
        insertWorker.add(new Worker(3, "Mike", LocalDate.of(1990, 5, 17), "Middle", 2500));
        insertWorker.add(new Worker(4, "Alex", LocalDate.of(1992, 11, 15), "Middle", 3200));
        insertWorker.add(new Worker(5, "Robert", LocalDate.of(1989, 6, 21), "Senior", 5500));
        insertWorker.add(new Worker(6, "Oleg", LocalDate.of(2000, 4, 18), "Trainee", 300));
        insertWorker.add(new Worker(7, "Victor", LocalDate.of(1994, 10, 11), "Junior", 700));
        insertWorker.add(new Worker(8, "Bob", LocalDate.of(1996, 6, 7), "Junior", 600));
        insertWorker.add(new Worker(9, "Kevin", LocalDate.of(1991, 11, 23), "Senior", 5700));
        insertWorker.add(new Worker(10, "Stewart", LocalDate.of(1995, 1, 3), "Middle", 1700));


        insertClient.add(new Client(1, "Adrian"));
        insertClient.add(new Client(2, "Yaroslav"));
        insertClient.add(new Client(3, "Domian"));
        insertClient.add(new Client(4, "Francesco"));
        insertClient.add(new Client(5, "Anton"));


        insertProject.add(new Project(1, 1, Date.valueOf("2024-01-30"), Date.valueOf("2024-03-29")));
        insertProject.add(new Project(2, 2, Date.valueOf("2024-02-05"), Date.valueOf("2024-06-15")));
        insertProject.add(new Project(3, 1, Date.valueOf("2024-02-20"), Date.valueOf("2024-08-20")));
        insertProject.add(new Project(4, 2, Date.valueOf("2024-02-01"), Date.valueOf("2024-05-25")));
        insertProject.add(new Project(5, 3, Date.valueOf("2024-03-01"), Date.valueOf("2025-03-01")));
        insertProject.add(new Project(6, 4, Date.valueOf("2024-02-15"), Date.valueOf("2025-04-15")));
        insertProject.add(new Project(7, 3, Date.valueOf("2024-04-25"), Date.valueOf("2026-04-25")));
        insertProject.add(new Project(8, 4, Date.valueOf("2024-01-30"), Date.valueOf("2024-12-01")));
        insertProject.add(new Project(9, 5, Date.valueOf("2024-02-01"), Date.valueOf("2024-09-15")));
        insertProject.add(new Project(10, 1, Date.valueOf("2024-03-30"), Date.valueOf("2024-10-01")));


        insertProjectWorker.add(new ProjectWorker(1, 1));
        insertProjectWorker.add(new ProjectWorker(1, 4));
        insertProjectWorker.add(new ProjectWorker(2, 6));
        insertProjectWorker.add(new ProjectWorker(2, 7));
        insertProjectWorker.add(new ProjectWorker(2, 9));
        insertProjectWorker.add(new ProjectWorker(3, 3));
        insertProjectWorker.add(new ProjectWorker(4, 9));
        insertProjectWorker.add(new ProjectWorker(4, 10));
        insertProjectWorker.add(new ProjectWorker(5, 2));
        insertProjectWorker.add(new ProjectWorker(5, 5));
        insertProjectWorker.add(new ProjectWorker(6, 9));
        insertProjectWorker.add(new ProjectWorker(7, 3));
        insertProjectWorker.add(new ProjectWorker(7, 4));
        insertProjectWorker.add(new ProjectWorker(7, 10));
        insertProjectWorker.add(new ProjectWorker(7, 1));
        insertProjectWorker.add(new ProjectWorker(8, 5));
        insertProjectWorker.add(new ProjectWorker(9, 3));
        insertProjectWorker.add(new ProjectWorker(9, 7));
        insertProjectWorker.add(new ProjectWorker(10, 4));
        insertProjectWorker.add(new ProjectWorker(10, 6));
    }


    private static void executeInsertIntoWorker(List<Worker> workers) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertIntoWorker)) {
            connection.setAutoCommit(false);
            for (Worker worker : workers) {
                preparedStatement.setInt(1, worker.getId());
                preparedStatement.setString(2, worker.getName());
                preparedStatement.setDate(3, Date.valueOf(worker.getBirthday()));
                preparedStatement.setString(4, worker.getLevel());
                preparedStatement.setInt(5, worker.getSalary());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void executeInsertIntoClient(List<Client> clients) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertIntoClient)) {
            connection.setAutoCommit(false);
            for (Client client : clients) {
                preparedStatement.setInt(1, client.getId());
                preparedStatement.setString(2, client.getName());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void executeInsertIntoProject(List<Project> projects) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertIntoProject)) {
            connection.setAutoCommit(false);
            for (Project project : projects) {
                preparedStatement.setInt(1, project.getId());
                preparedStatement.setInt(2, project.getClientId());
                preparedStatement.setDate(3, project.getStartDate());
                preparedStatement.setDate(4, project.getFinishDate());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void executeInsertIntoProjectWorker(List<ProjectWorker> projectWorkers) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertIntoProjectWorker)) {
            connection.setAutoCommit(false);
            for (ProjectWorker projectWorker : projectWorkers) {

                preparedStatement.setInt(1, projectWorker.getProjectID());
                preparedStatement.setInt(2, projectWorker.getWorkerID());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        executeInsertIntoWorker(insertWorker);
        executeInsertIntoClient(insertClient);
        executeInsertIntoProject(insertProject);
        executeInsertIntoProjectWorker(insertProjectWorker);
    }
}