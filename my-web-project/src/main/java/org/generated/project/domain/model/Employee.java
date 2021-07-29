package org.generated.project.domain.model;




import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.seedstack.business.domain.BaseAggregateRoot;
@Entity
public class Employee extends BaseAggregateRoot<EmployeeId>{
	@EmbeddedId
	private  EmployeeId id;
	private String name;
	private String email;
	private Date doj;
	private String password;
	private String projectId;
	private String projectname;
	private String jobrole;
    private String gender;
	
	public Employee() {
		
		
		// TODO Auto-generated constructor stub
	}

	public Employee(EmployeeId id) {
		this.id=id;
	}
	
	

	public Employee(EmployeeId id, String name, String email, Date doj, String password, String projectId,
			String projectname,String jobrole,String gender) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.doj = doj;
		this.password = password;
		this.projectId = projectId;
		this.projectname = projectname;
		this.jobrole=jobrole;
		this.gender=gender;
	}

	@Override
	public EmployeeId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	

	public void setId(EmployeeId id) {
		this.id = id;
	}

	public String getJobrole() {
		return jobrole;
	}

	public void setJobrole(String jobrole) {
		this.jobrole = jobrole;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", doj=" + doj + ", password=" + password
				+ ", projectId=" + projectId + ", projectname=" + projectname +", jobrole="+jobrole+", gender="+gender+"]";
	}
	
	
	

}
