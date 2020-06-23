package courseplanner.state;

import courseplanner.context.CoursePlanner;
import courseplanner.data.Student;

public class DataAnalytics implements CoursePlannerStateI {

	CoursePlanner coursePlanner;
	private final String group4="MNOP";
	int count=0;

	public DataAnalytics(CoursePlanner inCoursePlanner) {
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
				if(group4.contains(course.toString())){
					if(course=='M') {
						student.getCompleted().add(course);
						isAdded=true;
						count++;
						student.getPreferences().remove(course);
						iterator--;
						if(iterator==student.getPreferences().size()-1)
							coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
					}else{
						if(student.getCompleted().size()%3==0) {
							if(student.getCompleted().contains(group4.charAt(group4.indexOf(course)-1))) {
								student.getCompleted().add(course);
								isAdded=true;
								count++;
								student.getPreferences().remove(course);
								iterator--;
								if(iterator==student.getPreferences().size()-1)
									coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
							}

						}else {
							if(student.getCompleted().size()%3==1) {
								if(!group4.contains(student.getCompleted().get(student.getCompleted().size()-1).toString())) {
									if(student.getCompleted().contains(group4.charAt(group4.indexOf(course)-1))) {
										student.getCompleted().add(course);
										isAdded=true;
										count++;
										student.getPreferences().remove(course);
										iterator--;
										if(iterator==student.getPreferences().size()-1)
											coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
									}
								}
							}else if(student.getCompleted().size()%3==2) {
								if(!group4.contains(student.getCompleted().get(student.getCompleted().size()-1).toString()) && !group4.contains(student.getCompleted().get(student.getCompleted().size()-2).toString())) {
									if(student.getCompleted().contains(group4.charAt(group4.indexOf(course)-1))) {
										student.getCompleted().add(course);
										isAdded=true;
										count++;
										student.getPreferences().remove(course);
										iterator--;
										if(iterator==student.getPreferences().size()-1)
											coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
									}
								}
							}
						}
					}

				}else {
					student.setPointer(iterator);
					coursePlanner.setCoursePlannerState(coursePlanner.getState5());
					break;
				}
			}


		}else {
			coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
		}
		student.setSemestersTaken(student.getCompleted().size()/3+1);

		if(count>student.getStateCount()) {
			student.setStateCount(count);
			if(student.getGroup()!=group4)
				student.setStateChanges(student.getStateChanges()+1);
			student.setGroup(group4);
		}
	}
}
