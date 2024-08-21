package org.example.databaseQueryService;

import org.example.connection.Database;
import org.example.databaseQueryService.sqlAnswreClass.FindLongestProject;
import org.example.databaseQueryService.sqlAnswreClass.MaxProjectCountClient;
import org.example.databaseQueryService.sqlAnswreClass.ProjectPrice;
import org.example.databaseQueryService.sqlAnswreClass.YoungestAndEldestWorkers;
import org.example.prefs.Prefs;
import org.example.prefs.SqlFileReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private final Connection connection;
    private String sqlFilePath;

    public DatabaseQueryService() {
        Prefs prefs = new Prefs();
        String dbUrl =  prefs.getString(Prefs.DB_JDBC_CONNECTION_URL);
        String dbUser = prefs.getString(Prefs.DB_JDBC_USERNAME);
        String dbPass = prefs.getString(Prefs.DB_JDBC_PASSWORD);

        connection = Database.getInstance(dbUrl, dbUser, dbPass).getConnection();
        sqlFilePath = prefs.getString(Prefs.INIT_DB_FILE_PATH);
    }

    // Method to execute queries without parameters
    private ResultSet executeQuery(String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    // Method to execute queries with parameters
    private ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    public List<MaxProjectCountClient> maxProjectsClient() {
        List<MaxProjectCountClient> maxProjectCountClients = new ArrayList<>();
        try {
            String sql = SqlFileReader.readSqlFile(sqlFilePath + "\\find_max_projects_client.sql");
            ResultSet resultSet = executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int projectCount = resultSet.getInt("project_count");
                maxProjectCountClients.add(new MaxProjectCountClient(name, projectCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxProjectCountClients;
    }

    public List<FindLongestProject> longestProjects() {
        List<FindLongestProject> findLongestProjects = new ArrayList<>();
        try {
            String sql = SqlFileReader.readSqlFile(sqlFilePath + "\\find_longest_project.sql");
            ResultSet resultSet = executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int monthCount = resultSet.getInt("month_count");
                findLongestProjects.add(new FindLongestProject(name, monthCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findLongestProjects;
    }

    public List<ProjectPrice> projectPrices() {
        List<ProjectPrice> projectPrices = new ArrayList<>();
        try {
            String sql = SqlFileReader.readSqlFile(sqlFilePath + "\\print_project_prices.sql");
            ResultSet resultSet = executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String price = resultSet.getString("price");
                projectPrices.add(new ProjectPrice(name, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectPrices;
    }

    public List<YoungestAndEldestWorkers> youngestAndEldestWorkers() {
        List<YoungestAndEldestWorkers> workers = new ArrayList<>();
        try {
            String sql = SqlFileReader.readSqlFile(sqlFilePath + "\\find_youngest_eldest_workers.sql");
            ResultSet resultSet = executeQuery(sql);

            while (resultSet.next()) {
                String type = resultSet.getString("type");
                String name = resultSet.getString("name");
                String birthday = resultSet.getString("birthday");
                workers.add(new YoungestAndEldestWorkers(type, name, birthday));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workers;
    }

    public static void main(String[] args) {
        DatabaseQueryService service = new DatabaseQueryService();
        List<MaxProjectCountClient> maxProjectCountClients = service.maxProjectsClient();
        List<FindLongestProject> findLongestProjects = service.longestProjects();
        List<ProjectPrice> projectPrices = service.projectPrices();
        List<YoungestAndEldestWorkers> youngestAndEldestWorkers = service.youngestAndEldestWorkers();
    }
}


