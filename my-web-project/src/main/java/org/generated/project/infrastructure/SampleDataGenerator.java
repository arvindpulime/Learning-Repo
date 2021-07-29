package org.generated.project.infrastructure;



import java.util.Date;

import org.generated.project.domain.model.Employee;
import org.generated.project.domain.model.EmployeeId;
import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.LifecycleListener;
import org.seedstack.seed.transaction.Transactional;

import com.google.inject.Inject;

public class SampleDataGenerator implements LifecycleListener {

	@Inject
	@Jpa
	private Repository<Employee,EmployeeId> employeeRepository;
	@Override
	@Transactional
	@JpaUnit("myUnit")
	
	public void started() {
		employeeRepository.addOrUpdate(create("5065427","Sukumar","sukumarsolo543@gmail.com","123456",new Date(),"61005","PSA","Associate Consultant","Male"));
		employeeRepository.addOrUpdate(create("5065428","Sukumar","sukumarsolo543@gmail.com","123456",new Date(),"61005","PSA","Associate Consultant","Male"));
		employeeRepository.addOrUpdate(create("5065429","Sukumar","sukumarsolo543@gmail.com","123456",new Date(),"61005","PSA","Associate Consultant","Male"));
		employeeRepository.addOrUpdate(create("5065430","Sukumar","sukumarsolo543@gmail.com","123456",new Date(),"61005","Connected Cars","Associate Consultant","Male"));
	}
	
	public Employee create(String id,String name,String email,String password,Date doj,String projectId,String projectName,String jobrole,String gender) {
		Employee emp=new Employee(new EmployeeId(id));
		emp.setName(name);
		emp.setEmail(email);
		emp.setDoj(doj);
		emp.setPassword(password);
		emp.setProjectId(projectId);
		emp.setProjectname(projectName);
		emp.setJobrole(jobrole);
		emp.setGender(gender);
		return emp;
	}
	
}
