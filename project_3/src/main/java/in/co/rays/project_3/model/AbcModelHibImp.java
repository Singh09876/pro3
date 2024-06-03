package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AbcDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.EmailBuilder;
import in.co.rays.project_3.util.EmailMessage;
import in.co.rays.project_3.util.EmailUtility;
import in.co.rays.project_3.util.HibDataSource;

public class AbcModelHibImp implements AbcModelInt{

	
	
	


	public long add(AbcDTO dto) throws ApplicationException, DuplicateRecordException {

		System.out.println("in addddddddddddd");
		// TODO Auto-generated method stub
		/* log.debug("usermodel hib start"); */

		AbcDTO existDto = null;
		existDto = findByLogin(dto.getLogin());
		if (existDto != null) {
			throw new DuplicateRecordException("login id already exist");
		}
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();

			
			session.save(dto);
			dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();
  
			}
			throw new ApplicationException("Exception in User Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();

	}

	public void delete(AbcDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(AbcDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		AbcDTO existDto = findByLogin(dto.getLogin());
		// Check if updated LoginId already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("LoginId is already exist");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public AbcDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		AbcDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (AbcDTO) session.get(AbcDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public AbcDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		AbcDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AbcDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (AbcDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting User by Login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AbcDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Users list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(AbcDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(AbcDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub

		System.out.println("hellllo" + pageNo + "....." + pageSize + "........" + dto.getId() + "......" );
		Session session = null;
		ArrayList<AbcDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AbcDTO.class);
			if (dto != null) {
				if (dto.getId() != null) {	
					criteria.add(Restrictions.like("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}

				
				if (dto.getLogin() != null && dto.getLogin().length() > 0) {
					criteria.add(Restrictions.like("login", dto.getLogin() + "%"));
				}
				if (dto.getPassword() != null && dto.getPassword().length() > 0) {
					criteria.add(Restrictions.like("password", dto.getPassword() + "%"));
				}
				if (dto.getDuretion() != null && dto.getDuretion().length() > 0) {
					criteria.add(Restrictions.like("duretion", dto.getDuretion() + "%"));
				}
				
				
				
				  
				 			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<AbcDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}

		return list;
	}

	public AbcDTO authenticate(String login, String password) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println(login + "kkkkk" + password);
		Session session = null;
		AbcDTO dto = null;
		session = HibDataSource.getSession();
		Query q = session.createQuery("from AbcDTO where login=? and password=?");
		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (AbcDTO) list.get(0);
		} else {
			dto = null;

		}
		return dto;
	}

	public List getRoles(AbcDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean changePassword(long id, String newPassword, String oldPassword)
			throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		boolean flag = false;
		AbcDTO dtoExist = null;

		dtoExist = findByPK(id);
		System.out.println("in method password" + dtoExist.getPassword() + "jjjjjjj" + oldPassword);
		if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
			dtoExist.setPassword(newPassword);
			try {
				update(dtoExist);
			} catch (DuplicateRecordException e) {

				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", dtoExist.getLogin());
		map.put("password", dtoExist.getPassword());
		

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dtoExist.getLogin());
		msg.setSubject("Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return flag;

	}
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		AbcDTO userData = findByLogin(login);
		boolean flag = false;
		if (userData == null) {
			throw new RecordNotFoundException("Email Id Does not matched.");

		}

		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("login", userData.getLogin());
		map.put("login", userData.getLogin());

		map.put("password", userData.getPassword());
		map.put("firstName", userData.getName());
	
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;

		return flag;
	}

	public boolean resetPassword(AbcDTO dto) throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		AbcDTO userData = findByPK(dto.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}

	public long registerUser(AbcDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		long pk = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Registration is successful for ORS Project SUNRAYS Technologies");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}


	
	
	
}
