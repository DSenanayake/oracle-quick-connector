package oracledbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;

/**
 *
 * @author D_Senanayake
 */
public class ODBConnection {

    private static Connection conn;

    public static void main(String[] args) {
        JSONArray JSONArray = new JSONArray();
        String status = "incomplete";
        String message = "Process not completed!";
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        int records = 0;

        try {
            //check for args[]
            if (args.length < 6) {
                throw new Exception("Specific arguments required to run this program.[host,port,service,username,password,query]");
            }
            //getting parameters
            String host = args[0];
            String port = args[1];
            String service = args[2];
            String username = args[3];
            String password = args[4];
            String query = args[5];

            Class.forName("oracle.jdbc.driver.OracleDriver");
            if (conn == null) {
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + service,
                        username,
                        password);
            }

            Statement st = conn.createStatement();

            ResultSet result = st.executeQuery(query);

            ResultSetMetaData metaData = result.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.add(metaData.getColumnName(i));
            }

            while (result.next()) {
                HashMap<String, String> rows = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    rows.put(metaData.getColumnName(i), result.getString(i));
                }
                data.add(rows);
            }
            status = "success";
            message = "Process completed successfully!";
            records = data.size();

        } catch (Exception e) {
            status = "error";
            message = e.getMessage();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
            //map data to json string
            HashMap<String, Object> json = new HashMap<>();

            json.put("status", status);
            json.put("message", message);
            json.put("columns", columns);
            json.put("data", data);
            json.put("records", records);

            JSONArray.add(json);

            System.out.println(JSONArray.toJSONString());
            System.gc();
        }
    }
}
