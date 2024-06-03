package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.AbcDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AbcModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;
@WebServlet(urlPatterns = { "/ctl/AbcCtl" })
public class AbcCtl extends BaseCtl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		System.out.println("-------------validate started-------------");
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", " name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", " Name must contains alphabets only");
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", " Address"));
			pass = false;
		}
		if (!OP_UPDATE.equalsIgnoreCase(request.getParameter("operation"))) {

			if (DataValidator.isNull(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
				pass = false;
			} else if (!DataValidator.isPassword(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getValue("Enter the valid Password"));
				pass = false;
			}

		}
		if (DataValidator.isNull(request.getParameter("duretion"))) {
			request.setAttribute("duretion", PropertyReader.getValue("error.require", "duretion"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "mobile No"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mFees"))) {
			request.setAttribute("mFees", PropertyReader.getValue("error.require", "membership fees"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("emailId"))) {
			request.setAttribute("emailId", PropertyReader.getValue("error.require", "email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("emailId"))) {
			request.setAttribute("emailId", PropertyReader.getValue("error.email", "Email Id "));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("pan"))) {
			request.setAttribute("pan", PropertyReader.getValue("error.require", "pan"));
			pass = false;
		}else if (!DataValidator.isPan(request.getParameter("pan"))) {
			request.setAttribute("pan", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}
		
		System.out.println("validate end ");
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		AbcDTO	 dto = new AbcDTO();
		
         
 		System.out.println("Populate end " + "................"+request.getParameter("id"));
 		System.out.println("-------------------------------------------"+request.getParameter("password"));
 		System.out.println(request.getParameter("confirmPassword"));
         
          
   
		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("name")));


		dto.setLogin(DataUtility.getString(request.getParameter("emailId")));

		dto.setPassword(DataUtility.getString(request.getParameter("password")));


		dto.setDuretion(DataUtility.getString(request.getParameter("duretion")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		
		dto.setPan(DataUtility.getString(request.getParameter("pan")));
		dto.setAddress(DataUtility.getString(request.getParameter("address")));
		dto.setmFees(DataUtility.getString(request.getParameter("mFees")));
		populateBean(dto,request);
		

		return dto;

	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		

		
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		AbcModelInt model = ModelFactory.getInstance().getAbcModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			AbcDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	
		
		
		
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("-------------------------------------------------------------------------dopost run-------");
		// get model
		AbcModelInt model = ModelFactory.getInstance().getAbcModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
			AbcDTO dto = (AbcDTO) populateDTO(request);
              System.out.println(" in do post method jkjjkjk++++++++"+dto.getId());
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					
					try {
						 model.add(dto);
						 ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
				ServletUtility.setDto(dto, request);
				
			} catch (ApplicationException e) {
				
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			AbcDTO dto = (AbcDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ABC_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ABC_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ABC_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		
	
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ABC_VIEW;
	}

	
	
	
	
	
	
}
