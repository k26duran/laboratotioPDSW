package edu.eci.pdsw.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.eci.pdsw.model.Employee;
import edu.eci.pdsw.model.SocialSecurityType;
import edu.eci.pdsw.validator.EmployeeValidator;
import edu.eci.pdsw.validator.ErrorType;
import edu.eci.pdsw.validator.SalaryValidator;

/**
 * Servlet class for employee validation
 */
@WebServlet(urlPatterns = "/validate")
public class ValidateServlet extends HttpServlet {

	/**
	 * Auto generated serial version id
	 */
	private static final long serialVersionUID = -2768174622692970274L;

	/**
	 * The employee validator to use
	 */
	private EmployeeValidator validator;

	public ValidateServlet() {
		this.validator = new SalaryValidator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Writer responseWriter = resp.getWriter();

		// TODO Add the corresponding Content Type, Status, and Response
		resp.setContentType("");
		resp.setStatus(0);
		responseWriter.write(readFile("form.html"));
		responseWriter.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Writer responseWriter = resp.getWriter();
		
		
		// TODO Create and validate employee
		Long salary =(Long) req.getAttribute("salary");
		int personId= (int) req.getAttribute("personID");
		SocialSecurityType tipoSeguridad= (SocialSecurityType) req.getAttribute("SocialSecurity");
		
		Optional<ErrorType> response = validator.validate(new Employee(personId,salary,tipoSeguridad));
		
		

		// TODO Add the Content Type, Status, and Response according to validation response
		
		if(response.equals(ErrorType.INVALID_EPS_AFFILIATION)) {
			resp.setContentType("Registro invalido por EPS");
			resp.setStatus(resp.SC_BAD_REQUEST);
			
		}
		else if (response.equals(ErrorType.INVALID_ID)) {
			resp.setContentType("Registro invalido por Id");
			resp.setStatus(resp.SC_BAD_REQUEST);
		}
		else if(response.equals(ErrorType.INVALID_SALARY)) {
			resp.setContentType("Registro invalido por salario");
			resp.setStatus(resp.SC_BAD_REQUEST);
			
		}
		else if(response.equals(ErrorType.INVALID_SISBEN_AFFILIATION)) {
			resp.setContentType("Registro invalido por SISBEN");
			resp.setStatus(resp.SC_BAD_REQUEST);
		}
		else if(response.equals(ErrorType.INVALID_PREPAID_AFFILIATION)) {
			resp.setContentType("Registro no valido por PREPAID");
			resp.setStatus(resp.SC_BAD_REQUEST);
		}
		else {
			resp.setStatus(resp.SC_OK);
			resp.setContentType("Registro Exitoso");
			
		}
		resp.setContentType("");
		responseWriter.write(String.format(readFile("result.html"), response.map(ErrorType::name).orElse("Success")));
		responseWriter.flush();
	}

	/**
	 * Reads a file from the resources folder
	 * 
	 * @param path The file path
	 * @return the file content
	 * @throws IOException if the file doesn't exist
	 */
	public String readFile(String path) throws IOException {
		StringBuilder html = new StringBuilder();
		try (BufferedReader r = new BufferedReader(
				new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(path)))) {
			r.lines().forEach(line -> html.append(line).append("\n"));
		}
		return html.toString();
	}

}
