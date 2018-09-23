package edu.eci.pdsw.validator;

import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.*;

import java.util.Optional;

import org.junit.Test;

import edu.eci.pdsw.model.SocialSecurityType;

/**
 * Test class for {@linkplain SalaryValidator} class
 */
public class SalaryValidatorTest {

	/**
	 * The class under test.
	 */
	private SalaryValidator validator = new SalaryValidator();

	/**
	 * {@inheritDoc}}
	 */
	@Test
	public void validateTest() {
		qt()
		.forAll(validator.generadorEmpleados())
		.check(
				(employee)-> {
					if ((employee.getPersonId()>=1000) && (employee.getPersonId()<=100000)) {
						
						if(employee.getSalary()>=100 && employee.getSalary()<=50000) {
							if(employee.getSocialSecurityType().EPS==SocialSecurityType.EPS) {
								if (employee.getSalary()>=10000 || employee.getSalary()<1500) {
										return Optional.of(ErrorType.INVALID_EPS_AFFILIATION).equals(validator.validate(employee));
									
								}
							}
							if(employee.getSocialSecurityType().SISBEN == SocialSecurityType.SISBEN ) {
								
								if (employee.getSalary()>=1500) {
									return Optional.of(ErrorType.INVALID_SISBEN_AFFILIATION).equals(validator.validate(employee));
									
								}
							}
							if(employee.getSocialSecurityType().PREPAID== SocialSecurityType.PREPAID) {
								if (employee.getSalary()<10000) {
									return Optional.of(ErrorType.INVALID_PREPAID_AFFILIATION).equals(validator.validate(employee));
									
								}
							}
						}
						else { return Optional.of(ErrorType.INVALID_SALARY).equals(validator.validate(employee));}
						
					
					}
					else { return Optional.of(ErrorType.INVALID_ID).equals(validator.validate(employee));}
					return Optional.empty().equals(validator.validate(employee));
				
					
		}
				
		);
		
		//validator.validate(null);
	}
}
