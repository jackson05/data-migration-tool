/*
package launcher.source;
import java.sql.*;

/**
 * @author thespeedx
 *
 
public class SQLServerToMySQLMigration {
    
    public static void main(String[] args) {
        String sqlServerUrl = "jdbc:sqlserver://localhost:1433;databaseName=mydb;sendStringParametersAsUnicode=false";
        String sqlServerUser = "username";
        String sqlServerPassword = "password";
        
        String mySqlUrl = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String mySqlUser = "username";
        String mySqlPassword = "password";
        
        try (Connection sqlServerConnection = DriverManager.getConnection(sqlServerUrl, sqlServerUser, sqlServerPassword);
             Connection mySqlConnection = DriverManager.getConnection(mySqlUrl, mySqlUser, mySqlPassword)) {
            
            // Récupérer les noms de toutes les tables SQL Server
            DatabaseMetaData metaData = sqlServerConnection.getMetaData();
            ResultSet tablesResultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tablesResultSet.next()) {
                String tableName = tablesResultSet.getString("TABLE_NAME");
                System.out.println("Traitement de la table : " + tableName);
                
                // Vérifier si la table existe dans MySQL
                boolean tableExists = false;
                ResultSet mySqlTablesResultSet = mySqlConnection.getMetaData().getTables(null, null, tableName, null);
                while (mySqlTablesResultSet.next()) {
                    tableExists = true;
                }
                
                if (!tableExists) {
                    // Créer la table dans MySQL en utilisant les informations de la table SQL Server
                    ResultSet columnsResultSet = metaData.getColumns(null, null, tableName, "%");
                    StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + tableName + " (");
                    boolean isFirstColumn = true;
                    while (columnsResultSet.next()) {
                        if (!isFirstColumn) {
                            createTableQuery.append(", ");
                        }
                        createTableQuery.append(columnsResultSet.getString("COLUMN_NAME"))
                                       .append(" ")
                                       .append(columnsResultSet.getString("TYPE_NAME"));
                        isFirstColumn = false;
                    }
                    createTableQuery.append(")");
                    System.out.println("Création de la table : " + createTableQuery.toString());
                    PreparedStatement createTableStatement = mySqlConnection.prepareStatement(createTableQuery.toString());
                    createTableStatement.executeUpdate();
                }
                
                // Récupérer les données depuis SQL Server
                Statement sqlServerStatement = sqlServerConnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                sqlServerStatement.setFetchSize(Integer.MIN_VALUE);
                ResultSet resultSet = sqlServerStatement.executeQuery("SELECT * FROM " + tableName);
                
                // Insérer les données dans MySQL
                PreparedStatement mySqlStatement = mySqlConnection.prepareStatement("INSERT INTO " + tableName + " VALUES (" + getInsertStatementPlaceholders(resultSet) + ")");
                int batchCounter = 0;
                while (resultSet.next()) {
                    setInsertStatementValues(mySqlStatement, resultSet);
                    mySqlStatement.addBatch();
                    batchCounter++;
                    if (batchCounter % 1000 == 0) {
                        mySqlStatement.executeBatch();
                        batchCounter = 0;
                    }
                }
                mySqlStatement.executeBatch();
                
                System.out.println("Migration de la table " + tableName + " terminée.");
            }
            
            System.out.println("Migration terminée.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        private static String getInsertStatementPlaceholders(PreparedStatement preparedStatement,ResultSet resultSet) {
        	return "";
        }
    
}
    */
    
    
