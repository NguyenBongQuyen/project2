package jmaster.io.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgScoreByCourse {
	private int courseId;
	private String courseName;
	private double avg;

}
