package courseplanner.state;

import courseplanner.context.CoursePlanner;
import courseplanner.data.Student;

public class DataStructuresAndAlgorithms implements CoursePlannerStateI {

	CoursePlanner coursePlanner;
	private final String group2="EFGH";
	int count=0;

	public DataStructuresAndAlgorithms(CoursePlanner inCoursePlanner) {
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
				if(group2.contains(course.toString())){
					if(course=='E') {
						student.getCompleted().add(course);
						isAdded=true;
						count++;
						student.getPreferences().remove(course);
						iterator--;
						if(iterator==student.getPreferences().size()-1)
							coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
					}else{
						if(student.getCompleted().size()%3==0) {

							if(student.getCompleted().contains(group2.charAt(group2.indexOf(course)-1))) {
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
								if(!group2.contains(student.getCompleted().get(student.getCompleted().size()-1).toString())) {
									if(student.getCompleted().contains(group2.charAt(group2.indexOf(course)-1))) {
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
								if(!group2.contains(student.getCompleted().get(student.getCompleted().size()-1).toString()) && !group2.contains(student.getCompleted().get(student.getCompleted().size()-2).toString())) {
									if(student.getCompleted().contains(group2.charAt(group2.indexOf(course)-1))) {
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
					coursePlanner.setCoursePlannerState(coursePlanner.getState3());
					break;
				}
			}

		}else {
			coursePlanner.setCoursePlannerState(coursePlanner.getGraduateState());
		}
		student.setSemestersTaken(student.getCompleted().size()/3+1);

		if(count>student.getStateCount()) {
			student.setStateCount(count);
			if(student.getGroup()!=group2)
				student.setStateChanges(student.getStateChanges()+1);
			student.setGroup(group2);
		}
	}
}
