package hbm;

import java.util.HashMap;
import java.util.Map;

public class DaoNode {

	private String className;
	private Map<String, String> daoForeignKeys = new HashMap<String, String>();
	private Map<String, String> dbForeignKeys = new HashMap<String, String>();
	private String isCollection;
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public void setDaoForeignKey(String clazz, String alias) {
		this.daoForeignKeys.put(clazz, alias);
	}
	
	public Map<String, String> getDaoForeignKeys() {
		return this.daoForeignKeys;
	}
	
	public void setDbForeignKeys(String tableName, String fieldName) {
		this.dbForeignKeys.put(tableName, fieldName);
	}
	
	public Map<String, String> getDbForeignKeys() {
		return this.dbForeignKeys;
	}
	
	public String getIsCollection() {
		return isCollection;
	}
	
	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((daoForeignKeys == null) ? 0 : daoForeignKeys.hashCode());
		result = prime * result
				+ ((isCollection == null) ? 0 : isCollection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DaoNode other = (DaoNode) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (daoForeignKeys == null) {
			if (other.daoForeignKeys != null)
				return false;
		} else if (!daoForeignKeys.equals(other.daoForeignKeys))
			return false;
		if (isCollection == null) {
			if (other.isCollection != null)
				return false;
		} else if (!isCollection.equals(other.isCollection))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.className;
	}
}
