// Author: Bhagyashri Sarbhukan
// E-mail: bhagyashri.sarbhukan@gmail.com
package com.bhagyashri.gurukul.tests.utils;

public class Branch {
	private String name;
	private String code;
	
	public Branch(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Branch))return false;
	    
	    Branch otherMyClass = (Branch)other;
	    if(!otherMyClass.name.equals(name)) return false;
	    if(!otherMyClass.code.equals(code)) return false;
	    
	    return true;
	}
	
	
}
