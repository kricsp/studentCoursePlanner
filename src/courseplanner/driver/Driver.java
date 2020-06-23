package courseplanner.driver;

import courseplanner.context.CoursePlanner;
import courseplanner.data.Student;
import courseplanner.util.FileProcessor;
import courseplanner.util.Results;

/**
 * @author Krishan Pal Singh
 *
 */
public class Driver {
	public static void main(String[] args) {

		if (args.length != 2 || args[0].equals("${arg0}") || args[1].equals("${arg1}")) {

			System.err.println("Error: Incorrect number of arguments. Program accepts 2 arguments.");
			System.exit(0);
		}
		
		FileProcessor fileProcessor = new FileProcessor();
		fileProcessor.readFile(args[0]);
		
		Student student=fileProcessor.getStudent();
		
		CoursePlanner coursePlanner=new CoursePlanner();
		
		while(student.getPreferences().size()!=0) {
			if(!student.getIsGraduated()) {
				coursePlanner.preRequisiteValidation(student);
			}else
				break;			
		}
		
		if(coursePlanner.getCoursePlannerState()==coursePlanner.getGraduateState())
			coursePlanner.preRequisiteValidation(student);
		
		if(coursePlanner.getCoursePlannerState()==coursePlanner.getNotGraduateState())
			coursePlanner.preRequisiteValidation(student);
		
		
		Results results = new Results();
		results.writeToFile(args[1], student);
		results.writeToConsole(student);		
	}
}
