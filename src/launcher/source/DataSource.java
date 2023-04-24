package launcher.source;


public class DataSource {

	private String tableName;
	private String [] columns;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String[] getColumns() {
		return columns;
	}
	
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
}
