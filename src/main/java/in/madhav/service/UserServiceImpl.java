package in.madhav.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.madhav.binding.LoginForm;
import in.madhav.binding.SignUpForm;
import in.madhav.binding.UnlockForm;
import in.madhav.entity.UserDetailsEntity;
import in.madhav.repo.UserDtlsRepo;
import in.madhav.utility.EmailUtils;
import in.madhav.utility.PwdUtils;
@Service
public class UserServiceImpl implements UserInterface{
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private HttpSession session;
	@Override
	public String forgotPwd(String email) {
	
		UserDetailsEntity entity = userDtlsRepo.findByEmail(email);
		if(entity==null) {
			return "Invalid email !!! ";
		}
		if(entity.getAccStatus().equals("LOCKED")) {
			return "First Unlock your account, Check your email";
		}
		else {
			emailUtils.generateMail(email,"Recover Pwd", "You Pwd: "+entity.getPwd());
			return "success";
		}
	
	}
	
	

	
	@Override
	public String login(LoginForm form) {
		
		UserDetailsEntity entity = 
				userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		
			if(entity==null) {
				return "Invalid Credentials";
			}
			if(entity.getAccStatus().equals("LOCKED")) {
				return "Unclock Your Account, check your email";
			}
			//create session if login success and store user data in session
			session.setAttribute("userId", entity.getUserId());
			return "success";
		
			/*
			 *UserDetailsEntity entity = userDtlsRepo.findByEmail(form.getEmail());
			 *if(entity.getEmail().equals(form.getEmail()) && entity.getPwd().equals(form.getPwd()) && entity.getAccStatus().equals("UnLocked")) {
				return true;
			}
			else {
				return false;
			}*/
			
		
		
	}
	
	
	
	@Override
	public boolean unclock(UnlockForm form) {
		UserDetailsEntity entity = userDtlsRepo.findByEmail(form.getEmail());
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getConfPwd());
			entity.setAccStatus("UnLocked");
			
			userDtlsRepo.save(entity);
			return true;
		}
		else 
		{
		return false;
		}
	}
	
	@Override
	public boolean signUp(SignUpForm form) {
		
		UserDetailsEntity isPresent = userDtlsRepo.findByEmail(form.getEmail());
		if(isPresent!=null) {
			return false;
		}
	
		//TODO: Copy form object to e object
		UserDetailsEntity entity=new UserDetailsEntity();
		BeanUtils.copyProperties(form, entity);
		//TODO: Generate pwd
		String pwd = PwdUtils.pwd();
		entity.setPwd(pwd);
		//TODO: set account locked
		entity.setAccStatus("LOCKED");
		//TODO: save entity
		userDtlsRepo.save(entity);
		//TODO: send email
		String to=form.getEmail();
		String sub="Unclock | Account";
		StringBuffer body=new StringBuffer();
		body.append("Hi "+form.getName()+",");
		body.append("<br/>");
		body.append("<h2>Use Below Temporary pwd to unclock your account</h2>");
		body.append("Temp pwd: "+pwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">Click here To Unlcok Account");
		
		emailUtils.generateMail(to,sub,body.toString());
		
		
		return true;
	}
}
