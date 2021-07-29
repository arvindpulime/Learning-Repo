package org.generated.project.domain.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.generated.project.application.ConsumerData;
import org.generated.project.application.LoginCredential;
import org.generated.project.application.ProducerData;
import org.generated.project.application.RandomKeyGenerator;
import org.generated.project.application.UpdatePassword;
import org.generated.project.domain.model.Employee;
import org.generated.project.domain.model.EmployeeId;
import org.seedstack.business.Service;
import org.seedstack.business.domain.AggregateExistsException;
import org.seedstack.business.domain.Repository;
import org.seedstack.javamail.internal.JavaMailPlugin;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Bind;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;


@Bind
public class UserServiceImpl implements UserService{
	
	@Inject
	private RandomKeyGenerator randomKeyGenerator;
	
	@Inject
	@Jpa
	private Repository<Employee,EmployeeId>  empRepo;
	
	
	@Inject
	@Named("producer1")
	Producer<String, String> producer;

	@Inject
	@Named("consumer1")
	Consumer<String, String> consumer;
	
	@Inject
	@Named("consumer2")
	Consumer<String, String> consumer2;
	
	@Logging
	private Logger logger;
	
	@Override
	@Transactional
	@JpaUnit("myUnit")
	public Employee addEmployee(Employee emp) {
		// TODO Auto-generated method stub
		System.out.println("Add service called....");
		return empRepo.addOrUpdate(emp);
	}

	@Override
	@Transactional
	@JpaUnit("myUnit")
	public Employee getEmployee(EmployeeId EmpId) {
		// TODO Auto-generated method stub
		String topic="get-employee";
		String key="fetching employee data";
		ProducerData data=new ProducerData(key,"fetching data for employeeId:"+EmpId.getEmployeeId(),topic);
		producer(data);
		return empRepo.get(EmpId).get();
	}

	@Override
	@Transactional
	@JpaUnit("myUnit")
	public String updateEmployee(Employee emp) {
		// TODO Auto-generated method stub
		String topic="update-employee";
		String key="Updating employee data";
		String status="";
		
		try {
			empRepo.update(emp);
			ProducerData data=new ProducerData(key,"Updated data for employeeId:"+emp.getId().getEmployeeId(),topic);
			producer(data);
			status="Success";
		}
		catch(Exception e) {
			ProducerData data=new ProducerData(key,"Failed Updating data for employeeId:"+emp.getId().getEmployeeId(),topic);
			producer(data);
			status="failed";
		}
		return status;
	}

	@Override
	public int getRandomKey(String id) {
		// TODO Auto-generated method stub
		int key=0;
		String topic="forgotpassword";
		try {
			
			Employee emp=getEmployee(new EmployeeId(id));
			key=randomKeyGenerator.randomKey();
			ProducerData data=new ProducerData("OTP Generation", "OTP for "+id+":"+Integer.toString(key), topic);
			producer(data);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return key;
	}

	@Override
	@Transactional
	@JpaUnit("myUnit")
	public String updatePassword(UpdatePassword updatepswd) {
	String result="";
	String topic="forgotpassword";
		try {
			Employee emp=getEmployee(new EmployeeId(updatepswd.getEmployeeId()));
			emp.setPassword(updatepswd.getPassword());
			updateEmployee(emp);
			ProducerData data=new ProducerData("Updating Password", "password updated for employeeId:"+emp.getId().getEmployeeId(), topic);
			producer(data);
			result="Updated successfully";
		}
		catch(Exception e) {
			e.printStackTrace();
			ProducerData data=new ProducerData("Updating Password", "password updation failed for employeeId:"+updatepswd.getEmployeeId(), topic);
			producer(data);
			result="updation failed";
		}
		return result;
	}

	@Override
	@Transactional
	@JpaUnit("myUnit")
	public String login(LoginCredential logindata) {
		
		String status="";
		String topic="login";
		String key="Login Activity";
		
		
		try {
			Employee emp= empRepo.get(new EmployeeId(logindata.getId())).get();
			if(emp.getPassword().equals(logindata.getPassword()))
			{
			ProducerData data=new ProducerData(key,"Login Successfull", topic);
			producer(data);
		     status="success";
	        }
			else
			{
				
				ProducerData data=new ProducerData(key,"Login unSuccessfull", topic);
				producer(data);
				status="failed";
			}
		}
	catch(Exception e) {
		ProducerData data=new ProducerData(key,"Invalid EmployeeId", topic);
		producer(data);
		status="Invalid EmployeeId";
		}
	
		return status;
		
	}

	@Override
	@Transactional
	@JpaUnit("myUnit")
	public String registerEmployee(Employee emp) {
		// TODO Auto-generated method stub
		String topic="Register-Employee";
		String status="success";
		//empRepo.add(emp);
		
		try {
			Employee employee=getEmployee(emp.getId());
			System.out.println(employee);
			ProducerData data=new ProducerData("Registerting employee", "Employee Details Already Exists For EmployeeId:"+emp.getId().getEmployeeId(), topic);
            producer(data);
			status="failed";
		}
		catch(Exception e) {
			// addEmployee(emp);
			ProducerData data=new ProducerData("Registerting employee", "Employee Details Is Added Successfully For EmployeeId:"+emp.getId().getEmployeeId(), topic);
            producer(data);
			status="success";
		}
		return status;
		
	}



	@Override
	public String validate(Employee emp) {
		// TODO Auto-generated method stub
		
		String status=registerEmployee(emp);
		
		if(status=="success")
			addEmployee(emp);
		
		
		return status;
	}

	
	@Override
	public String producer(ProducerData data) {
		
		ProducerRecord<String, String> record=new ProducerRecord<String, String>(data.getTopic(), data.getKey(), data.getValue());
		producer.send(record);
		logger.info("Data published to topic: "+data.getTopic());
		logger.info("Details:");
		logger.info("Key:{} ,  Value:{}",data.getKey(),data.getValue());
		return null;
	}

	@Override
	public String consumer(String topic) {
		consumer.subscribe(Arrays.asList(topic));
		String status="No latest data available in topic";
		ConsumerRecords<String, String> records=consumer.poll(Duration.ofMillis(100));
		logger.info("Subscribed to topic: {}",topic);
		for(ConsumerRecord<String, String> record:records) {
			logger.info("Details:");
			logger.info("Key:{} , value:{}",record.key(),record.value());
			status="Data Recieved Successfully";
		}
		consumer.close();
		return status;
		
	}

	@Override
	public ArrayList<ConsumerData> consumer2(String topic) {
		
		ArrayList<ConsumerData> dataarray=new ArrayList<>();

		consumer2.subscribe(Arrays.asList(topic));
		
		ConsumerRecords<String, String> records=consumer2.poll(Duration.ofMillis(100));
		logger.info("Subscribed to topic: {}",topic);
		for(ConsumerRecord<String, String> record:records) {
			ConsumerData data=new ConsumerData(topic,record.key(), record.value());
			dataarray.add(data);
			logger.info("Details:");
			logger.info("Key:{} , value:{}",record.key(),record.value());
		}
		consumer2.close();
		return dataarray;
		
	}
	
	
	

	}
