package courseplanner.state;

import courseplanner.context.CoursePlanner;
import courseplanner.data.Student;

public class NotGraduated implements CoursePlannerStateI {

	CoursePlanner coursePlanner;
	public NotGraduated(CoursePlanner inCoursePlanner) {
		coursePlanner = inCoursePlanner;
	}


	public void preRequisiteValidation(Student student) {
		student.setSemestersTaken(0);
		student.setIsGraduated(false);
	}

}
