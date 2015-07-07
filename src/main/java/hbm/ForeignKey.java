package hbm;

public class ForeignKey {
	
	private String tableName;
	private String columnName;
	
	public ForeignKey(String tableName, String columnName) {
		
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
