public class AssignmentOverview {

    private Student[] students; // Array to be created in constructor.

    private int numbStudents = 0; // Increase 1 for each new student.

    public AssignmentOverview(Student[] students) {
        this.students = students;
    }

    public AssignmentOverview(){
        students = new Student[0];
    }


    /**
     * Add student, i.e. register new student.
     * @param name of new student
     */
    public void addStudent(String name){
        numbStudents++;
        Student[] tempStudents = new Student[numbStudents];
        for(int i = 0; i < students.length; i++){
            tempStudents[i] = students[i];
        }
        int n = tempStudents.length - 1;
        tempStudents[n] = new Student(name);
        this.students = tempStudents;
    }

    /**
     * Show/get number of registered students.
     * @return Number of registered students
     */
    public int getNumbStudents() {
        return numbStudents;
    }
    /*
    public int numbRegStud(){
        return students.length;
    }
    */

    /**
     * Find total number of solved assignments for a given student X.
     * @param name
     * @return Number of solved assignments for student X
     */

    public int numbAssSolvedForStudentX(String name){
        for(int i = 0; i < students.length; i++){
            if(this.students[i].getName().equals(name)) {
                return students[i].getNumbAss();
            }
        }
        return 0;
    }

    /**
     * Search for student by name.
     * Iterates over the student array, until input is equal to a name in the array.
     * @param name
     * @return Student name
     */
    public String findStudent(String name){
        for(int i = 0; i < students.length; i++){
            if(this.students[i].getName().equals(name)) {
                return students[i].getName();
            }
        }
        return "Student not found. Please try again";
    }

    /**
     * Add to the total of solved assignments.
     * The input number(int) will be *added* to the total.
     * @param name student name
     * @param increase int adds to total
     * @return Number of completed assignments
     * TO-DO: Error when adding assignments to student that doesn't exist!
     */
    public int incrNumbApprovedAss(String name, int increase){
        for(int i = 0; i < students.length; i++){
            if(this.students[i].getName().equals(name)) {
                students[i].increaseNumbAss(increase);
                return students[i].getNumbAss();
            }
        }
        return 0;
    }

    public void printAllStudents() {
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        } // Burde returnere students, og printe i Main
    }
}
