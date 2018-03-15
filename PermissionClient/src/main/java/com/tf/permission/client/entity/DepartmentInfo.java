package com.tf.permission.client.entity;

public class DepartmentInfo {

	private String id;
	private String name;
	private String higherDepart;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHigherDepart() {
		return higherDepart;
	}
	public void setHigherDepart(String higherDepart) {
		this.higherDepart = higherDepart;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((id == null) ? 0 : id.hashCode());

		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return false;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentInfo other = (DepartmentInfo) obj;
		
		if (id != null) {
			if (other.id != null)
				return id.equals(other.id);
		} 
		return false;
	}
	
	
}
