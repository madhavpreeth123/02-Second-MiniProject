package in.madhav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.madhav.binding.LoginForm;
import in.madhav.binding.SignUpForm;
import in.madhav.binding.UnlockForm;
import in.madhav.entity.UserDetailsEntity;
import in.madhav.repo.UserDtlsRepo;
import in.madhav.service.UserInterface;

@Controller
public class UserController {
	@Autowired
	private UserInterface userService;
	

	@PostMapping("/signup")
	public String signup(@ModelAttribute("user")  SignUpForm form,Model model) {
		boolean status = userService.signUp(form);
		if(status) {
			model.addAttribute("succMsg","Account Created Check your email");
		}
		else {
			model.addAttribute("errMsg","Email alreday exists!! can't a create account");
		}
		return "signup";
	}
	
	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}
	
	
	
	@PostMapping("/unlock")
	public String toUnlcokAccount(@ModelAttribute("unlock")  UnlockForm unlock,Model model) {
		if(unlock.getNewPwd().equals(unlock.getConfPwd())) {
			boolean status = userService.unclock(unlock);
			if(status) {
				model.addAttribute("succMsg","Your Account Unlocked Successfully!!");
			}
			else {
				model.addAttribute("errMsg", "temp pwd not matched!! Check your email once.");
			}
		}
		else {
			model.addAttribute("errMsg", "password miss macth");
		}
		return "unlock";
	}
	
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email,Model model) {
		UnlockForm unlock=new UnlockForm();
		unlock.setEmail(email);
		model.addAttribute("unlock", unlock);
		return "unlock";
	}
	
	@PostMapping("/login")
	public String loginFunc(@ModelAttribute("login") LoginForm form,Model model) {
		
		String status = userService.login(form);
		if(status.equals("success")) {
			
			return "redirect:/dashboard";
		}
		else {
			model.addAttribute("errMsg", status);
			return "login";
		}
		
		
		
		/*UserDetailsEntity isPresent = userDtlsRepo.findByEmail(form.getEmail());
		if(isPresent!=null) {
		if(isPresent.getAccStatus().equals("LOCKED")) {
			model.addAttribute("errMsg", "Please Unlock your Account!! check your email to unlock account");
			return "login";
		}
			boolean login = userService.login(form);
			if(login) {
				return "dashboard";
			}
			else {
				model.addAttribute("errMsg", "Invalid Credentials");
				return "login";
			}
			
		}
		else {
			model.addAttribute("errMsg", form.getEmail()+" is not exist!!");
			return "login";
		}
			*/
		
			
		}	
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("login", new LoginForm());
		return "login";
	}
	
	
	@PostMapping("/forgot")
	public String forgot(@RequestParam("email") String email,Model model) {
		String forgotPwd = userService.forgotPwd(email);
		if(forgotPwd.equals("success")) {
			model.addAttribute("succMsg", "Password sent to your email successfully ");
		}
		else {
			model.addAttribute("errMsg", forgotPwd);
			
		}
		return "forgotPwd";
	}
	
	@GetMapping("/forgot")
	public String forgotPage() {
		
		return "forgotPwd";
	}
}
