package edu.eci.pdsw.validator;

import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.*;

import java.util.Optional;

import org.junit.Test;

import edu.eci.pdsw.model.SocialSecurityType;
import generators.EmployeeGenerator;

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
		.forAll(EmployeeGenerator.generadorEmpleados())
		.check(
				(employee)-> {
					Optional optional;
					optional = validator.validate(employee);
					if (employee.getPersonId() < 1000 || employee.getPersonId() > 100000) {
						return optional.equals(Optional.of(ErrorType.INVALID_ID));
					}
					if (employee.getSalary() < 100 || employee.getSalary() > 50000) {
						return optional.equals(Optional.of(ErrorType.INVALID_SALARY));
					}
					if (employee.getSalary() > 1500) {
						return optional.equals(Optional.of(ErrorType.INVALID_SISBEN_AFFILIATION));
					}
					if (employee.getSalary() <= 1500 && employee.getSalary() >= 10000) {
						return optional.equals(Optional.of(ErrorType.INVALID_EPS_AFFILIATION));
					}else
						return optional.equals(Optional.of(ErrorType.INVALID_PREPAID_AFFILIATION));
				});
		
		//validator.validate(null);
	}
}
