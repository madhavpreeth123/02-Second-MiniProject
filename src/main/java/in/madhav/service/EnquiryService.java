package in.madhav.service;

import java.util.List;

import in.madhav.binding.DashBoardResponse;
import in.madhav.binding.EnquiryForm;
import in.madhav.binding.EnquiryFormSearchCritteria;
import in.madhav.entity.StudentEnqEntity;

public interface EnquiryService {

	public DashBoardResponse getDashBoardData(Integer userId);
	
	public List<String> getCourses();
	public List<String> getEnqStatus();
	public boolean addEnquiry(EnquiryForm enquiryForm);
	public List<StudentEnqEntity> getEnquiries();
	public List<StudentEnqEntity> getFilteredData(EnquiryFormSearchCritteria search,Integer userId);

	public EnquiryForm getEnquiryById(Integer enqId);
	
	public boolean updateEnquiries(EnquiryForm enquiryForm);
	
}
