package in.madhav.service;

import org.springframework.stereotype.Service;

import in.madhav.binding.LoginForm;
import in.madhav.binding.SignUpForm;
import in.madhav.binding.UnlockForm;
@Service
public interface UserInterface {

	public String login(LoginForm form);
	public boolean signUp(SignUpForm form);
	public boolean unclock(UnlockForm form);
	public String forgotPwd(String email);
}
