package in.madhav.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CoursesEntity {

	@Id
	@GeneratedValue
	private Integer courseId;
	private String courseName;
}
