package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Properties;

@WebServlet("/ExportCSVServlet")
public class ExportCSVServlet extends HttpServlet {
 private static String JDBC_URL;
    private static String JDBC_USER;
    private static String JDBC_PASSWORD;
    private static final String url_Class = "com.mysql.cj.jdbc.Driver";

    @Override
    public void init() throws ServletException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new ServletException("Unable to find config.properties");
            }
            Properties props = new Properties();
            props.load(input);
            JDBC_URL = props.getProperty("JDBC_URL");
            JDBC_USER = props.getProperty("JDBC_USER");
            JDBC_PASSWORD = props.getProperty("JDBC_PASSWORD");
        } catch (IOException e) {
            throw new ServletException("Failed to load database configuration", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Set response to CSV
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=applications.csv");

        try (PrintWriter out = response.getWriter()) {
            // CSV Header
            out.println(""
                + "\"ID\","
                + "\"App Name\","
                + "\"Version Number\","
                + "\"Vendor\","
                + "\"Deployment Date\","
                + "\"Owner\","
                + "\"Database Type\","
                + "\"Operating System\","
                + "\"Hosting Environment\","
                + "\"Purpose\","
                + "\"Critical Rating\","
                + "\"User Base\","
                + "\"Integrated Apps\","
                + "\"Authentication Method\","
                + "\"Data Types\","
                + "\"Transaction Volume\","
                + "\"Users\","
                + "\"Created At\""
            );

            // Load JDBC driver and connect
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM applications")) {

                // Date formatter for both deployment_date and created_at
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

                while (rs.next()) {
                    // Retrieve each field. 
                    // If your columns are actually DATETIME/TIMESTAMP in MySQL, use rs.getTimestamp() for date fields
                    int id              = rs.getInt("id");
                    String appName      = rs.getString("app_name");
                    String versionNum   = rs.getString("version_number");
                    String vendor       = rs.getString("vendor");

                    // Format deployment_date if it's a datetime/timestamp in DB
                    Timestamp deployTs  = rs.getTimestamp("deployment_date");
                    String deploymentDate = (deployTs != null) ? df.format(deployTs) : "";

                    String owner        = rs.getString("owner");
                    String dbType       = rs.getString("database_type");
                    String os           = rs.getString("operating_system");
                    String hostingEnv   = rs.getString("hosting_environment");
                    String purpose      = rs.getString("purpose");
                    String critRating   = rs.getString("critical_rating");
                    String userBase     = rs.getString("user_base");
                    String integrated   = rs.getString("integrated_apps");
                    String authMethod   = rs.getString("authentication_method");
                    String dataTypes    = rs.getString("data_types");
                    String transVol     = rs.getString("transaction_volume");
                    String users        = rs.getString("users");

                    // Format created_at if it's a datetime/timestamp
                    Timestamp createdTs = rs.getTimestamp("created_at");
                    String createdAt    = (createdTs != null) ? df.format(createdTs) : "";

                    // Write row with quoted fields
                    out.println("\"" + id + "\","
                              + "\"" + (appName      == null ? "" : appName)      + "\","
                              + "\"" + (versionNum   == null ? "" : versionNum)   + "\","
                              + "\"" + (vendor       == null ? "" : vendor)       + "\","
                              + "\"" + deploymentDate                                + "\","
                              + "\"" + (owner        == null ? "" : owner)        + "\","
                              + "\"" + (dbType       == null ? "" : dbType)       + "\","
                              + "\"" + (os           == null ? "" : os)           + "\","
                              + "\"" + (hostingEnv   == null ? "" : hostingEnv)   + "\","
                              + "\"" + (purpose      == null ? "" : purpose)      + "\","
                              + "\"" + (critRating   == null ? "" : critRating)   + "\","
                              + "\"" + (userBase     == null ? "" : userBase)     + "\","
                              + "\"" + (integrated   == null ? "" : integrated)   + "\","
                              + "\"" + (authMethod   == null ? "" : authMethod)   + "\","
                              + "\"" + (dataTypes    == null ? "" : dataTypes)    + "\","
                              + "\"" + (transVol     == null ? "" : transVol)     + "\","
                              + "\"" + (users        == null ? "" : users)        + "\","
                              + "\"" + createdAt                                  + "\""
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error generating CSV: " + e.getMessage());
        }
    }
}
