package com.goit.jdbc.storage;

import com.goit.jdbc.dto.*;
import com.goit.jdbc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private final Database database;
    private static final Logger log = LoggerFactory.getLogger(DatabaseQueryService.class);

    public DatabaseQueryService(Database database) {
        this.database = database;
    }

    List<LongestProject> findLongestProject() {
        String longestProjectFilename = new Util().getString(Util.FIND_LONGEST_PROJECT_FILEPATH);
        String sql = readSqlFile(longestProjectFilename);
        List<LongestProject> list = new ArrayList<>();
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()
        ) {
            while (rs.next()) {
                int projectID = rs.getInt("id");
                int duration = rs.getInt("duration");
                LongestProject longestProject = new LongestProject(projectID, duration);
                list.add(longestProject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    List<MaxProjectCountClient> findMaxProjectsClient() {
        String maxProjectsFilename = new Util().getString(Util.FIND_MAX_PROJECTS_CLIENT_FILEPATH);
        String sql = readSqlFile(maxProjectsFilename);
        List<MaxProjectCountClient> list = new ArrayList<>();
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()
        ) {
            while (rs.next()) {
                String clientName = rs.getString("name");
                int numProjects = rs.getInt("project_count");
                MaxProjectCountClient maxProjectCountClient = new MaxProjectCountClient(clientName, numProjects);
                list.add(maxProjectCountClient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    List<MaxSalaryWorker> findMaxSalaryWorker() {
        String maxSalaryWorkerFilename = new Util().getString(Util.FIND_MAX_SALARY_WORKER_FILEPATH);
        String sql = readSqlFile(maxSalaryWorkerFilename);
        List<MaxSalaryWorker> list = new ArrayList<>();
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()
        ) {
            while (rs.next()) {
                String workerName = rs.getString("name");
                int salary = rs.getInt("salary");
                MaxSalaryWorker maxSalaryWorker = new MaxSalaryWorker(workerName, salary);
                list.add(maxSalaryWorker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    List<YoungestEldestWorker> findYoungestEldestWorker() {
        String youngestEldestWorkerFilename = new Util().getString(Util.FIND_YOUNGEST_ELDEST_WORKERS_FILEPATH);
        String sql = readSqlFile(youngestEldestWorkerFilename);
        List<YoungestEldestWorker> list = new ArrayList<>();
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()
        ) {
            while (rs.next()) {
                String workerName = rs.getString("name");
                Date birthday = rs.getDate("birthday");
                YoungestEldestWorker youngestEldestWorker = new YoungestEldestWorker(workerName, birthday);
                list.add(youngestEldestWorker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    List<PrintPrices> printProjectPrices() {
        String printProjectPricesFilename = new Util().getString(Util.PRINT_PROJECT_PRICES_FILEPATH);
        List<PrintPrices> list = new ArrayList<>();
        String sql = readSqlFile(printProjectPricesFilename);
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()
        ) {
            while (rs.next()) {
                int projectID = rs.getInt("ProjectID");
                int cost = rs.getInt("ProjectCost");
                PrintPrices printPrices = new PrintPrices(projectID, cost);
                list.add(printPrices);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String readSqlFile(String fileName) {

        try {
            return String.join("\n",
                    Files.readAllLines(Paths.get(fileName))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Empty data";
    }

    public static void main(String[] args) {

        DatabaseQueryService dqs = new DatabaseQueryService(Database.getInstance());

        for (LongestProject project1 : dqs.findLongestProject()) {
            log.info("ProjectId {} - {} months", project1.getID(), project1.getCountOfMonths());
        }
        for (MaxProjectCountClient project : dqs.findMaxProjectsClient()) {
            log.info("{} = {} projects", project.getName(), project.getProjectCount());
        }
        for (MaxSalaryWorker worker : dqs.findMaxSalaryWorker()) {
            log.info("{} = {}$", worker.getName(), worker.getSalary());
        }
        for (YoungestEldestWorker worker : dqs.findYoungestEldestWorker()) {
            log.info("{} = {}", worker.getName(), worker.getBirthday().toLocalDate());
        }
        for (PrintPrices price : dqs.printProjectPrices()) {
            log.info("ProjectId {} = {}$", price.getProjectID(), price.getProjectCost());
        }
    }
}
