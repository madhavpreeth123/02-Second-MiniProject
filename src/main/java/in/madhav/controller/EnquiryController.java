package in.madhav.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.madhav.binding.DashBoardResponse;
import in.madhav.binding.EnquiryForm;
import in.madhav.binding.EnquiryFormSearchCritteria;
import in.madhav.entity.StudentEnqEntity;
import in.madhav.service.EnquiryService;

@Controller
public class EnquiryController {
	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userId = (int) session.getAttribute("userId");
		DashBoardResponse dashBoardData = enqService.getDashBoardData(userId);
		model.addAttribute("dashboard", dashBoardData);
		return "dashboard";
	}

	@PostMapping("/addEnquiry")
	public String addEnquiry(@ModelAttribute("enqform") EnquiryForm enquiryForm,Model model) {
		boolean status = enqService.addEnquiry(enquiryForm);
		if(status) {
			model.addAttribute("succMsg", "Student data saved..");
		}else {
			model.addAttribute("errMsg", "Student data not saved..");

		}
		return "add-enquiry";
	}

	@GetMapping("/addEnquiry")
	public String addEnquiryPage(Model model) {
		// get course data
		List<String> courses = enqService.getCourses();
		model.addAttribute("courses", courses);
		// get enq status
		List<String> enqStatus = enqService.getEnqStatus();
		// create binding class object
		EnquiryForm enquiryForm = new EnquiryForm();

		model.addAttribute("enqStatus", enqStatus);
		
		
		model.addAttribute("enqform", enquiryForm);
		return "add-enquiry";
	}

	

	@GetMapping("/viewEnquiry")
	public String viewEnquiryPage(Model model) {
		List<String> courses = enqService.getCourses();
		List<String> enqStatus = enqService.getEnqStatus();
		model.addAttribute("courses", courses);
		model.addAttribute("enqStatus", enqStatus);
		List<StudentEnqEntity> enquiries = enqService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "view-enquiries";
	}
	
	@GetMapping("/getEnquiries")
	public String getEnquiryData(@RequestParam String cname,
			@RequestParam String status,
			@RequestParam String mode,
			Model model) {
		
		EnquiryFormSearchCritteria search=new EnquiryFormSearchCritteria();
		search.setCourseName(cname);
		search.setEqnStatus(status);
		search.setClassMode(mode);
		Integer userId = (int) session.getAttribute("userId");
		List<StudentEnqEntity> filteredData = 
				enqService.getFilteredData(search, userId);
		model.addAttribute("enquiries", filteredData);
		return "filtered-enquires";
	}
	
	@GetMapping("/edit")
	public String loadAddEnqForm(@RequestParam("enqId") Integer enqId, Model model) {
		
		
		EnquiryForm enquiryForm = enqService.getEnquiryById(enqId);

        // Populate the model with necessary data for the form
        List<String> courses = enqService.getCourses();
        List<String> enqStatus = enqService.getEnqStatus();

        model.addAttribute("courses", courses);
        model.addAttribute("enqStatus", enqStatus);
        model.addAttribute("enqform", enquiryForm);//sendig to the form resposible to pre-populate

        return "demo"; // Assuming you have a separate
	}
	@PostMapping("/editUpdate")
	public String updateEnqForm(@ModelAttribute("enqform") EnquiryForm form,Model model) {
		
		boolean status = enqService.updateEnquiries(form);
		if(status) {
			model.addAttribute("succMsg", "Data Updated...");
		}
		else {
			model.addAttribute("errMsg", "Data Not Updated...");
		}
	
		return "demo";
	}
	
}
