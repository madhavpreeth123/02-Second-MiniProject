package in.madhav.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class EnqStatusEntity {
	@Id
	@GeneratedValue
	private Integer statusId;
	private String statusName;
	
}
