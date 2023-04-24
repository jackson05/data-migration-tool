package launcher;
import java.sql.*;

public class SQLServerToMySQLMigration {
    
    public static void main(String[] args) {
        //String sqlServerUrl = "jdbc:sqlserver://localhost:1433;databaseName=mydb;sendStringParametersAsUnicode=false";
    	String sqlServerUrl="jdbc:mysql://localhost:3306/ocadmin_dbo?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    	String sqlServerUser = "openclinic";
        String sqlServerPassword = "0pen";
        
        String mySqlUrl = "jdbc:mysql://localhost:3306/ocadmin_dbo?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String mySqlUser = "openclinic";
        String mySqlPassword = "0pen";
        
        try (Connection sqlServerConnection = DriverManager.getConnection(sqlServerUrl, sqlServerUser, sqlServerPassword);
             Connection mySqlConnection = DriverManager.getConnection(mySqlUrl, mySqlUser, mySqlPassword)) 
        {
        	
        	mySqlConnection.setAutoCommit(false);
            // Récupérer les noms de toutes les tables SQL Server
            DatabaseMetaData metaData = sqlServerConnection.getMetaData();
            ResultSet tablesResultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            
            while (tablesResultSet.next()) {
            	
                String tableName = tablesResultSet.getString("TABLE_NAME");
                
                System.out.println("======> working on "+tableName);
                //System.out.println("Traitement de la table : " + tableName);
                
                /* Vérifier si la table existe dans MySQL
                boolean tableExists = false;
                ResultSet mySqlTablesResultSet = mySqlConnection.getMetaData().getTables(null, null, tableName, null);
                while (mySqlTablesResultSet.next()) {
                    tableExists = true;
                }
                /*
                
                 if (tableExists) {
                    // Créer la table dans MySQL en utilisant les informations de la table SQL Server
                    ResultSet columnsResultSet = metaData.getColumns(null, null, tableName, "%");
                    StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + tableName + " (");
                    boolean isFirstColumn = true;
                    
                    System.out.print("|");
                    while (columnsResultSet.next()) {
                        if (!isFirstColumn) {
                            createTableQuery.append(", ");
                        }
                        createTableQuery.append(columnsResultSet.getString("COLUMN_NAME"))
                                       .append(" ")
                                       .append(columnsResultSet.getString("TYPE_NAME"))
                                       .append(("SIZE ()"));
                        isFirstColumn = false;
                        
                        System.out.print(columnsResultSet.getString("COLUMN_NAME")+"("+columnsResultSet.getString("TYPE_NAME")+") SIZE ("
                        		+ columnsResultSet.getFetchSize()+")|");
                    }
                    createTableQuery.append(")");
                    System.out.println("Création de la table : " + createTableQuery.toString());
                   // PreparedStatement createTableStatement = mySqlConnection.prepareStatement(createTableQuery.toString());
                    //createTableStatement.executeUpdate();
                }
                
                */
                
                // Copier les données de la table SQL Server vers MySQL
                PreparedStatement selectStatement = sqlServerConnection.prepareStatement("SELECT * FROM " + tableName);
                ResultSet dataResultSet = selectStatement.executeQuery();
                ResultSetMetaData dataResultSetMetaData = dataResultSet.getMetaData();
                StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
                for (int i = 1; i <= dataResultSetMetaData.getColumnCount(); i++) {
                    if (i > 1) {
                        insertQuery.append(", ");
                    }
                    insertQuery.append("?");
                    
                }
                insertQuery.append(")");
               // System.out.println("Debut d'Insertion des données : " + insertQuery.toString());
                PreparedStatement insertStatement = mySqlConnection.prepareStatement(insertQuery.toString());
                
                while (dataResultSet.next()) {
                    for (int i = 1; i <= dataResultSetMetaData.getColumnCount(); i++) {
                        insertStatement.setObject(i, dataResultSet.getObject(i));
                    
                    }
                    
               //   insertStatement.executeUpdate();
                    mySqlConnection.commit();
                } 
            } 
            
         
        } catch (SQLException e) {
        	
            e.printStackTrace();
        }
    }
}
