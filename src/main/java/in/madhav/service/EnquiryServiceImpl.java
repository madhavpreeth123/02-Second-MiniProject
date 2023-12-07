package in.madhav.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.madhav.binding.DashBoardResponse;
import in.madhav.binding.EnquiryForm;
import in.madhav.binding.EnquiryFormSearchCritteria;
import in.madhav.entity.CoursesEntity;
import in.madhav.entity.EnqStatusEntity;
import in.madhav.entity.StudentEnqEntity;
import in.madhav.entity.UserDetailsEntity;
import in.madhav.repo.CourseRepo;
import in.madhav.repo.EnqStatusRepo;
import in.madhav.repo.StudentEnqRepo;
import in.madhav.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private StudentEnqRepo enqRepo;
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo enqStatusRepo;
	@Autowired
	private HttpSession sesssion;
	@Autowired
	private UserDtlsRepo userRepo;

	@Override
	public DashBoardResponse getDashBoardData(Integer userId) {
		DashBoardResponse response = new DashBoardResponse();
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDetailsEntity.getEnquiries();
			int size = enquiries.size();

			int enrolledEnqs = enquiries.stream().filter(e -> e.getEqnStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			int lostEnqs = enquiries.stream().filter(e -> e.getEqnStatus().equals("Lost")).collect(Collectors.toList())
					.size();
			response.setTotalEnquires(size);
			response.setEnrollEnqCnt(enrolledEnqs);
			response.setLostEnqCnt(lostEnqs);
		}

		return response;
	}

	@Override
	public List<String> getCourses() {
		List<CoursesEntity> courses = courseRepo.findAll();
		List<String> names = new ArrayList<>();
		for (CoursesEntity name : courses) {
			names.add(name.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnqStatusEntity> listEnqStatus = enqStatusRepo.findAll();
		List<String> listStatus = new ArrayList<>();
		for (EnqStatusEntity status : listEnqStatus) {
			listStatus.add(status.getStatusName());
		}
		return listStatus;
	}

	@Override
	public boolean addEnquiry(EnquiryForm enquiryForm) {
		StudentEnqEntity entity = new StudentEnqEntity();
		BeanUtils.copyProperties(enquiryForm, entity);
		Integer userId = (int) sesssion.getAttribute("userId");
		UserDetailsEntity userDetailsEntity = userRepo.findById(userId).get();
		entity.setUser(userDetailsEntity);
		enqRepo.save(entity);

		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries() {
		Integer userId = (int) sesssion.getAttribute("userId");
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDetailsEntity.getEnquiries();
			return enquiries;
		}
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilteredData(EnquiryFormSearchCritteria search, Integer userId) {
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDetailsEntity.getEnquiries();

			if (null != search.getCourseName() && "" != search.getCourseName()) {
				enquiries = enquiries.stream().filter(e -> e.getCourseName().equals(search.getCourseName()))
						.collect(Collectors.toList());
			}
			if (null != search.getEqnStatus() && "" != search.getEqnStatus()) {

				enquiries = enquiries.stream().filter(e -> e.getEqnStatus().equals(search.getEqnStatus()))
						.collect(Collectors.toList());
			}
			if (null != search.getClassMode() && "" != search.getClassMode()) {
				enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(search.getClassMode()))
						.collect(Collectors.toList());
			}

			return enquiries;
		}
		return null;
	}

	@Override
	public EnquiryForm getEnquiryById(Integer enqId) {
	    Integer userId = (int) sesssion.getAttribute("userId");
	    Optional<UserDetailsEntity> findById = userRepo.findById(userId);
	    
	    if (findById.isPresent()) {
	        UserDetailsEntity userDetailsEntity = findById.get();
	        List<StudentEnqEntity> enquiries = userDetailsEntity.getEnquiries();
	        
	        Optional<StudentEnqEntity> enquiryOptional = enquiries.stream()
	                .filter(e -> e.getEnqId().equals(enqId))
	                .findFirst();
	        
	        if (enquiryOptional.isPresent()) {
	            EnquiryForm enquiryForm = new EnquiryForm();
	            BeanUtils.copyProperties(enquiryOptional.get(), enquiryForm);
	            return enquiryForm;
	        }
	    }
	    return null;
	}
	@Override
	public boolean updateEnquiries(EnquiryForm enquiryForm) {
		StudentEnqEntity entity=new StudentEnqEntity();
		BeanUtils.copyProperties(enquiryForm, entity);
		Integer userId = (int)sesssion.getAttribute("userId");
		 UserDetailsEntity userDetailsEntity = userRepo.findById(userId).get();
		 entity.setUser(userDetailsEntity);
		 enqRepo.save(entity);
		
		
		return true;
	}

	}


