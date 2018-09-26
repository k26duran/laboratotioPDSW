package generators;
import static org.quicktheories.generators.SourceDSL.integers;
import static org.quicktheories.generators.SourceDSL.longs;

import org.quicktheories.core.Gen;
import org.quicktheories.generators.Generate;

import edu.eci.pdsw.model.Employee;
import edu.eci.pdsw.model.SocialSecurityType;

public class EmployeeGenerator {
	
	public static Gen<Employee> generadorEmpleados(){
		return generadorId().zip(generadorSalarios(), generadorSeguridadSocial(), (personId,salary,socialSecurity)->new Employee(personId,salary,socialSecurity));
		
		
	}
	public static Gen<Integer> generadorId(){
		return Generate.range(0, Integer.MAX_VALUE);
		
	}
	public static Gen<Long> generadorSalarios(){
		return Generate.longRange(100, 50000);
	}
	
	public static Gen<SocialSecurityType> generadorSeguridadSocial(){
		return  Generate.enumValues(SocialSecurityType.class);
	}
}
