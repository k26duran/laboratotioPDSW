package edu.eci.pdsw.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
		resp.setContentType("text/html");
		responseWriter.write(readFile("form.html"));
		
		//Optional <String> optSalary= Optional.ofNullable(req.getParameter("salary"));
		//String salary=optSalary.isPresent() && !optSalary.get().isEmpty() ? optSalary.get() : "";
		//resp.setStatus(404);
		//responseWriter.write("HOLA"+salary);
		//responseWriter.write(readFile("form.html"));
		responseWriter.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Writer responseWriter = resp.getWriter();
		try{
		// TODO Create and validate employee
		Long salary =Long.parseLong(req.getParameter("salary"));
		int personId= Integer.parseInt(req.getParameter("personID"));
		SocialSecurityType tipoSeguridad=SocialSecurityType.valueOf(req.getParameter("SocialSecurity"));
		
		Optional<ErrorType> response = validator.validate(new Employee(personId,salary,tipoSeguridad));
		// TODO Add the Content Type, Status, and Response according to validation response
		if(response.equals(ErrorType.INVALID_EPS_AFFILIATION)) {
			responseWriter.write("La afiliacion EPS es invalida");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			responseWriter.write(String.format(readFile("result.hmtl"), response.map(ErrorType::name).orElse("Success")));
			responseWriter.flush();
		}
		if (response.equals(ErrorType.INVALID_ID)) {
			responseWriter.write("ID invalido");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			responseWriter.write(String.format(readFile("result.hmtl"), response.map(ErrorType::name).orElse("Success")));
			responseWriter.flush();
		}
		if(response.equals(ErrorType.INVALID_SALARY)) {
			responseWriter.write("Salario invalido");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			responseWriter.write(String.format(readFile("result.hmtl"), response.map(ErrorType::name).orElse("Success")));
			responseWriter.flush();
		}
		if(response.equals(ErrorType.INVALID_SISBEN_AFFILIATION)) {
			responseWriter.write("La afiliacion SISBEN es invalida");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			responseWriter.write(String.format(readFile("result.hmtl"), response.map(ErrorType::name).orElse("Success")));
			responseWriter.flush();
			
		}
		if(response.equals(ErrorType.INVALID_PREPAID_AFFILIATION)) {
			responseWriter.write("La afiliacion PREPAID es invalida");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			responseWriter.write(String.format(readFile("result.hmtl"), response.map(ErrorType::name).orElse("Success")));
			responseWriter.flush();
		}
		
		if(response.equals(Optional.empty())) {
			responseWriter.write("Validacion correcta");
			resp.setStatus(200);
			responseWriter.write(String.format(readFile("result.html"), response.map(ErrorType::name).orElse("Success")));
			responseWriter.flush();
		}}
		catch (Exception i){
			responseWriter.write("Parametro valido");
			resp.setStatus(500);
			responseWriter.flush();
		}

		//responseWriter.write("Todos los parametros son validos");
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
