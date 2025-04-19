public class Student {

    private String name; // "Entydig?"
    private int numbAss = 0; // Number of Assignments turned in and accepted.

    public Student(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public int getNumbAss(){
        return numbAss;
    }

    public void increaseNumbAss(int increase) {
        numbAss += increase;
    }

    public String toString() {
        return "Student name: " + name + "\n" +
        "Number of assignments approved: " + numbAss + "\n" +
        "----------\n";
    }
}
