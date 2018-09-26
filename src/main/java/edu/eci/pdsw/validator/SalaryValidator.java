package edu.eci.pdsw.validator;

import java.util.Optional;


import edu.eci.pdsw.model.Employee;
import edu.eci.pdsw.model.SocialSecurityType;

/**
 * Utility class to validate an employee's salary
 */
public class SalaryValidator implements EmployeeValidator {

	/**
	 * {@inheritDoc}}
	 */
	public Optional<ErrorType> validate(Employee employee) {
		
		if (employee.getPersonId() < 1000 || employee.getPersonId() > 100000) {
			return Optional.of(ErrorType.INVALID_ID);
		}
		if (employee.getSalary() < 100 || employee.getSalary() > 50000) {
			return Optional.of(ErrorType.INVALID_SALARY);
		}
		if (employee.getSalary() > 1500) {
			return Optional.of(ErrorType.INVALID_SISBEN_AFFILIATION);
		}
		if (employee.getSalary() <= 1500 && employee.getSalary() >= 10000) {
			return Optional.of(ErrorType.INVALID_EPS_AFFILIATION);
		}else
			return Optional.of(ErrorType.INVALID_PREPAID_AFFILIATION);
	}
		
}
