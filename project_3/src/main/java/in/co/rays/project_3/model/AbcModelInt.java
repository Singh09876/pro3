package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AbcDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

public interface AbcModelInt {
	
	
	

public long add(AbcDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(AbcDTO dto)throws ApplicationException;
public void update(AbcDTO dto)throws ApplicationException,DuplicateRecordException;
public AbcDTO findByPK(long pk)throws ApplicationException;
public AbcDTO findByLogin(String login)throws ApplicationException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(AbcDTO dto,int pageNo,int pageSize)throws ApplicationException;
public List search(AbcDTO dto)throws ApplicationException;
public boolean changePassword(long id,String newPassword,String oldPassword)throws ApplicationException, RecordNotFoundException;
public AbcDTO authenticate(String login,String password)throws ApplicationException;
public boolean forgetPassword(String login)throws ApplicationException, RecordNotFoundException;
public boolean resetPassword(AbcDTO dto)throws ApplicationException,RecordNotFoundException;
public long registerUser(AbcDTO dto)throws ApplicationException,DuplicateRecordException;
public List getRoles(AbcDTO dto)throws ApplicationException;


}
