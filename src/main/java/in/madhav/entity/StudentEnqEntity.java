package in.madhav.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name="STUDENT_ENQUIREIES")
public class StudentEnqEntity {

	@Id
	@GeneratedValue
	private Integer enqId;
	private String studentName;
	private String studentPhno;
	private String classMode;
	private String courseName;
	private String eqnStatus;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate createdDate;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate updatedDate;
	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private UserDetailsEntity user;

//    @ManyToOne
//    @JoinColumn(name = "user_id") // Adjust the column name based on your database schema
//    private UserDetailsEntity user;
}
