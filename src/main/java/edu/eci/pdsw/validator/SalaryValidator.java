package edu.eci.pdsw.validator;

import java.util.Optional;

import org.quicktheories.core.Gen;
import org.quicktheories.generators.Generate;

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
		
		if (!(employee.getPersonId()>=1000) || !(employee.getPersonId()<=100000)) {
			
			if(employee.getSalary()>=100 && employee.getSalary()<=50000) {
				if(employee.getSocialSecurityType().EPS== SocialSecurityType.EPS) {
					if (employee.getSalary()>=10000 && employee.getSalary()<1500) {
						return Optional.of(ErrorType.INVALID_EPS_AFFILIATION);
						
					}
				}
				if(employee.getSocialSecurityType().SISBEN == SocialSecurityType.SISBEN ) {
					
					if (employee.getSalary()>=1500) {
						return Optional.of(ErrorType.INVALID_SISBEN_AFFILIATION);
						
					}
				}
				if(employee.getSocialSecurityType().PREPAID== SocialSecurityType.PREPAID) {
					if (employee.getSalary()<10000) {
						return Optional.of(ErrorType.INVALID_PREPAID_AFFILIATION);
						
					}
				}
			}
			else {return Optional.of(ErrorType.INVALID_SALARY);}
			
		
		}
		else {return Optional.of(ErrorType.INVALID_ID);}
	
		
		return Optional.empty();
		
	}
	
	public Gen<Employee> generadorEmpleados(){
		return generadorId().zip(generadorSalarios(), generadorSeguridadSocial(), (personId,salary,socialSecurity)->new Employee(personId,salary,socialSecurity));
		
		
	}
	public Gen<Integer> generadorId(){
		return Generate.range(0, Integer.MAX_VALUE);
		
	}
	public Gen<Long> generadorSalarios(){
		return Generate.longRange(100, 50000);
	}
	
	public Gen<SocialSecurityType> generadorSeguridadSocial(){
		return  Generate.enumValues(SocialSecurityType.class);
	}
}
