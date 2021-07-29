package org.generated.project.domain.model;

import javax.persistence.Embeddable;

import org.seedstack.business.domain.BaseValueObject;
@Embeddable 
public class EmployeeId extends BaseValueObject {
	
	private  String employeeId;
	
	
	 public EmployeeId() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EmployeeId(String id) {
		// TODO Auto-generated constructor stub
		 this.employeeId=id;
	}

	
	public String getEmployeeId() {
		return employeeId;
	}

}
