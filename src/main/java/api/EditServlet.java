package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.json.JSONObject;

@WebServlet({"/EditServlet"})
public class EditServlet extends HttpServlet {

    private static String JDBC_URL;
    private static String JDBC_USER;
    private static String JDBC_PASSWORD;
    // JDBC driver class
    private static final String url_Class = "com.mysql.cj.jdbc.Driver";

    @Override
    public void init() throws ServletException {
        // Load database configuration from config.properties file
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

    /**
     * Override doPost to support HTTP method override.
     * If the hidden _method parameter equals "PUT", delegate to doPut().
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String methodOverride = request.getParameter("_method");
        if (methodOverride != null && methodOverride.equalsIgnoreCase("PUT")) {
            doPut(request, response);
        } else {
            // If not a PUT override, you can implement POST logic for creation here
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for updating.");
        }
    }

    /**
     * Handles the PUT request to update an existing application.
     */
    @Override
protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Set headers to allow cross-origin requests and specify allowed methods.
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");

    try {
        // Retrieve the "id" parameter as a String first.
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            sendErrorResponse(response, "ID parameter is missing.");
            return;
        }
        int id = Integer.parseInt(idStr);

        // Retrieve the rest of the parameters from the request.
        String appName = request.getParameter("app_name");
        String versionNumber = request.getParameter("version_number");
        String vendor = request.getParameter("vendor");
        String deploymentDate = request.getParameter("deployment_date");
        String owner = request.getParameter("owner");
        String databaseType = request.getParameter("database_type");
        String operatingSystem = request.getParameter("operating_system");
        String hostingEnvironment = request.getParameter("hosting_environment");
        String purpose = request.getParameter("purpose");
        String criticalRating = request.getParameter("critical_rating");
        String userBase = request.getParameter("user_base");
        String integratedApps = request.getParameter("integrated_apps");
        String authenticationMethod = request.getParameter("authentication_method");
        String dataTypes = request.getParameter("data_types");
        String transactionVolume = request.getParameter("transaction_volume");
        String users = request.getParameter("users");

        // Load the JDBC driver.
        Class.forName(url_Class);
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            // Step 1: Retrieve the current application name for the given record ID.
            String selectSql = "SELECT app_name FROM applications WHERE id = ?";
            String currentAppName = null;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, id);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        currentAppName = rs.getString("app_name");
                    } else {
                        sendErrorResponse(response, "Application not found.");
                        return;
                    }
                }
            }

            // Step 2: If the new application name is different from the current one, check for duplicates.
            if (!appName.equals(currentAppName)) {
                String checkSql = "SELECT id FROM applications WHERE app_name = ? AND id != ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, appName);
                    checkStmt.setInt(2, id);
                    try (ResultSet rsCheck = checkStmt.executeQuery()) {
                        if (rsCheck.next()) {
                            // If a duplicate exists, send a 409 Conflict response.
                            sendErrorResponse(response, "Application name already exists.");
                            return;
                        }
                    }
                }
            }

            // Step 3: Prepare the UPDATE query (do not update the id column; use it in the WHERE clause only).
            String updateSql = "UPDATE applications SET app_name = ?, version_number = ?, vendor = ?, " +
                               "deployment_date = ?, owner = ?, database_type = ?, operating_system = ?, " +
                               "hosting_environment = ?, purpose = ?, critical_rating = ?, user_base = ?, " +
                               "integrated_apps = ?, authentication_method = ?, data_types = ?, transaction_volume = ?, users = ? " +
                               "WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                stmt.setString(1, appName);
                stmt.setString(2, versionNumber);
                stmt.setString(3, vendor);
                stmt.setString(4, deploymentDate);
                stmt.setString(5, owner);
                stmt.setString(6, databaseType);
                stmt.setString(7, operatingSystem);
                stmt.setString(8, hostingEnvironment);
                stmt.setString(9, purpose);
                stmt.setString(10, criticalRating);
                stmt.setString(11, userBase);
                stmt.setString(12, integratedApps);
                stmt.setString(13, authenticationMethod);
                stmt.setString(14, dataTypes);
                stmt.setString(15, transactionVolume);
                stmt.setString(16, users);
                stmt.setInt(17, id);
                
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    sendSuccessResponse(response,"Application updated successfully!");
                } else {
                    sendErrorResponse(response, "Application not found.");
                }
            }
        }
    } catch (Exception e) {
        sendErrorResponse(response, "Database error: " + e.getMessage());
    }
}

    private void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
    response.getWriter().println("<html><head><script type=\"text/javascript\">alert('" + message + "');window.location.href = 'crud.html';</script></head><body></body></html>");
  }
  
  private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
    response.getWriter().println("<html><head><script type=\"text/javascript\">alert('Error: " + errorMessage
        
        .replace("'", "\\'") + "');window.location.href = 'crud.html';</script></head><body></body></html>");
  }

}
