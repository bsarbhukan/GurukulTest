// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests.utils;

public class Staff {
	private String name;
	private String branch;
	
	public Staff(String name, String branch) {
		super();
		this.name = name;
		this.branch = branch;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Staff))return false;
	    
	    Staff otherMyClass = (Staff)other;
	    if(!otherMyClass.name.equals(name)) return false;
	    if(!otherMyClass.branch.equals(branch)) return false;
	    
	    return true;
	}
}
