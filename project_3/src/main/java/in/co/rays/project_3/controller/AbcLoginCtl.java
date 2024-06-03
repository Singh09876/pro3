package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project_3.dto.AbcDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.AbcModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.ServletUtility;

public class AbcLoginCtl extends BaseCtl {
	
	

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";
	
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		
		

		System.out.println(request.getParameter("login"));

		String op = request.getParameter("operation");

		AbcModelInt model = ModelFactory.getInstance().getAbcModel();

		HttpSession session = request.getSession(true);

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_LOG_OUT.equals(op)) {
			session = request.getSession();
			session.invalidate();
			ServletUtility.setSuccessMessage("User Logged Out Successfully", request);
			ServletUtility.forward(ORSView.ABC_LOGIN_CTL, request, response);
			return;
		}
		if (id > 0) {
			AbcDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);

	
	}
	
	
	
	
	
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		

		String op = request.getParameter("operation");
		System.out.println(";;;" + op);

		HttpSession session = request.getSession(true);

		AbcModelInt model = ModelFactory.getInstance().getAbcModel();


		// long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			AbcDTO dto = (AbcDTO) populateDTO(request);
			try {
				dto = model.authenticate(dto.getLogin(), dto.getPassword());
				if (dto != null) {
					session.setAttribute("user", dto);
					
					String uri = (String) request.getParameter("uri");
					if (uri == null || "null".equalsIgnoreCase(uri)) {
						ServletUtility.redirect(ORSView.ABC_LIST_VIEW, request, response);
						return;
					} else {
						System.out.println();
						if (dto.getId() == 1) {
							ServletUtility.redirect(uri, request, response);
						} else {
							ServletUtility.redirect(ORSView.ABC_CTL, request, response);
						}

						return;
					}

				} else {
					dto = (AbcDTO) populateDTO(request);
					ServletUtility.setDto(dto, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	protected String getView() {
		
		return ORSView.ABC_LOGIN_VIEW;
	}

}
