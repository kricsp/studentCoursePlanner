package courseplanner.state;

import courseplanner.context.CoursePlanner;
import courseplanner.data.Student;

public class Electives implements CoursePlannerStateI {

	CoursePlanner coursePlanner;
	private final String group5="QRSTUVWXYZ";
	int count=0;

	public Electives(CoursePlanner inCoursePlanner) {
		coursePlanner = inCoursePlanner;
	}

	public void preRequisiteValidation(Student student) {
		Boolean isAdded=false;
		if(student.getPreferences().size()!=0) {
			for(int iterator=student.getPointer();iterator<student.getPreferences().size();iterator++) {
				if(isAdded) {
					coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
					break;
				}
				Character course=student.getPreferences().get(iterator);
				if(group5.contains(course.toString())){
					student.getCompleted().add(course);
					isAdded=true;
					count++;
					student.getPreferences().remove(course);
					iterator--;
					if(iterator==-1)
						coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
					if(iterator==student.getPreferences().size()-1)
						coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
				}else {
					student.setPointer(iterator);
					coursePlanner.setCoursePlannerState(coursePlanner.getState1());
					break;
				}
			}
		}else {
			coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
		}

		if(count>student.getStateCount()) {
			student.setStateCount(count);
			if(student.getGroup()!=group5)
				student.setStateChanges(student.getStateChanges()+1);
			student.setGroup(group5);
		}

	}

}
