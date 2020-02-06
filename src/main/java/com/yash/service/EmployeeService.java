package com.yash.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.exception.EmployeeNotFoundException;
import com.yash.model.Employee;
import com.yash.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		employeeRepository.findAll().forEach(employees::add);
		return employees;
	}

	public Employee getEmployee(int id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("No employee exits with id : " + id));
	}

	public void addEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	public void deleteEmployee(int id) {
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("No employee exits with id : " + id));
		employeeRepository.delete(emp);
	}

	public void updateEmployee(Employee updatedEmployee, int id) {
		employeeRepository.findById(id).map(employee -> {
			employee.setName(updatedEmployee.getName());
			employee.setSalary(updatedEmployee.getSalary());
			return employeeRepository.save(employee);
		}).orElseThrow(() -> new EmployeeNotFoundException("No employee exits with id : " + id));
	}

}
