package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * Immutable object that contains a list of Strings
 * @author e1054909
 *
 */
public class Path<T> {
		
	private List<T> path;
	
	public Path() {
		path = new ArrayList<T>();
	}
	
	public Path(List<T> path) {
		this.path = path;
	}
	
	public Path(T... path) {
		this.path = new ArrayList<T>(Arrays.asList(path));
	}

	public Path(Path<T> path) {
		this.path = new ArrayList<T>(path.getPath());
	}
	
	public Path<T> addToPath(T nextVertex) {
		
		List<T> newPath = new ArrayList<T>(this.path);
		newPath.add(nextVertex);
		
		return new Path<T>(newPath);
	}
	
	public List<T> getPath() {
		return new ArrayList<T>(this.path);
	}
	
	public boolean hasDuplicate() {
		
		Set<T> set = new HashSet<T>();
		
		for (T node : this.path) {
			if (!set.add(node)) {
				return true;
			}
		}
		
		return false;
	}
	
	public int size() {
		return path.size();
	}
	
	@Override
	public String toString() {
		
		StringBuffer string = new StringBuffer();
		
		for (T node : path) {
			string.append(node.toString());
		}
		
		return string.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		Path<T> other = (Path<T>) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
}
