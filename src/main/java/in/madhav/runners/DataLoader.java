package in.madhav.runners;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.madhav.entity.CoursesEntity;
import in.madhav.entity.EnqStatusEntity;
import in.madhav.repo.CourseRepo;
import in.madhav.repo.EnqStatusRepo;
@Component
public class DataLoader implements ApplicationRunner{

	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo enqRepo;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		CoursesEntity c1=new CoursesEntity();
		c1.setCourseName("AWS");
		CoursesEntity c2=new CoursesEntity();
		c2.setCourseName("Devops");
		CoursesEntity c3=new CoursesEntity();
		c3.setCourseName("Java Full Stack");
		CoursesEntity c4=new CoursesEntity();
		c4.setCourseName("Python");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		enqRepo.deleteAll();
		EnqStatusEntity e1=new EnqStatusEntity();
		e1.setStatusName("New");
		EnqStatusEntity e2=new EnqStatusEntity();
		e2.setStatusName("Enrolled");
		EnqStatusEntity e3=new EnqStatusEntity();
		e3.setStatusName("Lost");
		enqRepo.saveAll(Arrays.asList(e1,e2,e3));
		
	}
	
}
