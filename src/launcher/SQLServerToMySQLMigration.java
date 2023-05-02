package launcher;
import java.sql.*;

public class SQLServerToMySQLMigration {
    
    public static void main(String[] args) {
    	
    	Configurations c=new Configurations();
    	
    	for(String schemaName:c.getDataBases()) {
    		//System.out.println(schemaName);
    		/*
    		 * String sqlServerUrl = "jdbc:sqlserver://localhost:1433;databaseName=mydb;sendStringParametersAsUnicode=false";
    		 * */
        	String sqlServerUrl="jdbc:"+c.getSrcRDBMS()+"://"+c.getSrcHost()+":"+c.getSrcPort()+"/"+schemaName+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        	String sqlServerUser = "openclinic";
            String sqlServerPassword = "0pen";
            
            String mySqlUrl = "jdbc:"+c.getDestRDBMS()+"://"+c.getDestHost()+":"+c.getDestPort()+"/"+schemaName+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            String mySqlUser = "openclinic";
            String mySqlPassword = "0pen";
            
            try {
        	   
            	Connection sQLServerConnection = DriverManager.getConnection(sqlServerUrl, sqlServerUser, sqlServerPassword);
                
               // Récupérer les noms de toutes les tables SQL Server
               DatabaseMetaData sQLMetaData = sQLServerConnection.getMetaData();
               ResultSet sQLTablesResultSet = sQLMetaData.getTables(null, null, "%", new String[]{"TABLE"});
               
               while (sQLTablesResultSet.next()) {
               	
                   String tableName = sQLTablesResultSet.getString("TABLE_NAME");
                   
                   System.out.println("======> working on "+tableName);
                   
                   System.out.println("Traitement de la table : " + tableName);
                   
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
        	
                   PreparedStatement sQLPreparedStatement = sQLServerConnection.prepareStatement("SELECT * FROM " + tableName);
                   sQLPreparedStatement.setFetchSize(1000);
                   ResultSet sQLResultSet =sQLPreparedStatement.executeQuery();
                   ResultSetMetaData dataResultSetMetaData = sQLResultSet.getMetaData();
                   StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
                   for (int i = 1; i <= dataResultSetMetaData.getColumnCount(); i++) {
                       if (i > 1) {
                           insertQuery.append(", ");
                       }
                       insertQuery.append("?");
                       
                   }
                   insertQuery.append(")");
                   
                  //creation d'une instance de connection mysql
                   
                   Connection mySQLConnection = DriverManager.getConnection(mySqlUrl, mySqlUser, mySqlPassword);
                  
                   //gestion de transaction des donées
                   mySQLConnection.setAutoCommit(false);
                   PreparedStatement mySQLPreparedStatement = mySQLConnection.prepareStatement(insertQuery.toString());
                   
                   try {
                	   
                       while (sQLResultSet.next()) {
                           for (int i = 1; i <= dataResultSetMetaData.getColumnCount(); i++) {
                        	   mySQLPreparedStatement.setObject(i, sQLResultSet.getObject(i));
                        	//  mySQLPreparedStatement.addBatch();
                           }
                           
                          // mySQLPreparedStatement.executeBatch();
                              
                	   
                   }
                       mySQLConnection.commit();
                       
                   }catch (Exception e) {
                	   mySQLConnection.rollback();
                	   	e.printStackTrace();
    			} finally {
    				mySQLPreparedStatement.close();
    				mySQLConnection.close();
    				sQLResultSet.close();
    				sQLPreparedStatement.close();
    				sQLTablesResultSet.close();
    				sQLServerConnection.close();
    			}
               } 
           }catch (Exception e) {
        	   e.printStackTrace();
    	}
            	
            	
            	
            	
                
             
    	}
        
       
    }
}
