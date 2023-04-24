/*
package launcher.source.code;
import java.sql.*;

public class SQLServerToMySQLMigration {
    
    public static void main(String[] args) {
        String sqlServerUrl = "jdbc:sqlserver://localhost:1433;databaseName=mydb";
        String sqlServerUser = "username";
        String sqlServerPassword = "password";
        
        String mySqlUrl = "jdbc:mysql://localhost:3306/mydb";
        String mySqlUser = "username";
        String mySqlPassword = "password";
        
        try (Connection sqlServerConnection = DriverManager.getConnection(sqlServerUrl, sqlServerUser, sqlServerPassword);
             Connection mySqlConnection = DriverManager.getConnection(mySqlUrl, mySqlUser, mySqlPassword)) {
            
            // Récupérer les noms des tables dans SQL Server
            DatabaseMetaData metadata = sqlServerConnection.getMetaData();
            ResultSet tables = metadata.getTables(null, null, null, new String[] {"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                long tableSize = getTableSize(sqlServerConnection, tableName);
                
                // Vérifier que la table n'est pas trop grande
                if (tableSize > 50_000_000_000L) {
                    System.out.println("La table " + tableName + " est trop grande pour être traitée.");
                    continue;
                }
                
                // Créer la table dans MySQL si elle n'existe pas déjà
               // createTableIfNotExists(mySqlConnection, tableName, sqlServerConnection, metadata);
                
                // Récupérer les données depuis SQL Server
                PreparedStatement sqlServerStatement = sqlServerConnection.prepareStatement("SELECT * FROM " + tableName);
                ResultSet resultSet = sqlServerStatement.executeQuery();
                
                // Insérer les données dans MySQL
                PreparedStatement mySqlStatement = mySqlConnection.prepareStatement(getInsertQuery(tableName, resultSet.getMetaData()));
                while (resultSet.next()) {
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        mySqlStatement.setObject(i, resultSet.getObject(i));
                    }
                    mySqlStatement.executeUpdate();
                }
                
                System.out.println("Migration de la table " + tableName + " terminée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


  
    private static long getTableSize(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT SUM(DATA_LENGTH) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '" + tableName + "'");
        resultSet.next();
        long size = resultSet.getLong(1);
        statement.close();
        return size;
    }
}
    /*
    
    private static void createTableIfNotExists(Connection connection, String tableName, Connection sourceConnection, DatabaseMetaData metadata) throws SQLException {
        ResultSet columns = metadata.getColumns(null, null, tableName, null);
        if (columns.next()) {
            // La table existe déjà
            return;
        }
        
        // Créer la table en copiant la structure de la table source
        ResultSet sourceColumns = sourceConnection.getMetaData().getColumns(null, null, tableName, null);
        StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + tableName + " (");
        while (sourceColumns.next()) {
            String columnName = sourceColumns.getString("COLUMN_NAME");
            String dataType = sourceColumns.getString("TYPE_NAME");
            int columnSize = sourceColumns.getInt("COLUMN_SIZE");
        */
    
