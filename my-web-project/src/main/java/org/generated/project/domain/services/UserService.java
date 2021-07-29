package org.generated.project.domain.services;


import java.util.ArrayList;

import org.generated.project.application.ConsumerData;
import org.generated.project.application.LoginCredential;
import org.generated.project.application.ProducerData;
import org.generated.project.application.UpdatePassword;
import org.generated.project.domain.model.Employee;
import org.generated.project.domain.model.EmployeeId;
import org.seedstack.business.Service;


@Service
public interface UserService {
	Employee addEmployee(Employee emp);
	
	String updateEmployee(Employee emp);
	
	Employee getEmployee(EmployeeId EmpId);
	
	int getRandomKey(String id);
	
	String updatePassword(UpdatePassword updatepswd);
	
	String login(LoginCredential logindata);
	
	String registerEmployee(Employee emp);
	
	
	
	String validate(Employee emp);
	
	String producer(ProducerData data);
	
	String consumer(String topic);
	
	ArrayList<ConsumerData> consumer2(String topic);
	
	//int getRandomKey();
    
	// String mailService() throws MessagingException;
}
