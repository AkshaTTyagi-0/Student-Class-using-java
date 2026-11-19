import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


class Student {

    private final String studentId;         
    private String studentName;
    private String email;
    private String courseName;
    private double programmingScore;
    private double mathematicsScore;
    private List<String> technicalSkills;

    // ---------- Constructors ----------

    public Student(String studentId, String studentName, String email,
                   String courseName, double programmingScore,
                   double mathematicsScore, List<String> technicalSkills) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        this.studentId = studentId.trim();
        setStudentName(studentName);
        setEmail(email);
        setCourseName(courseName);
        setProgrammingScore(programmingScore);
        setMathematicsScore(mathematicsScore);
        setTechnicalSkills(technicalSkills);
    }

    /** Minimal constructor: scores default to 0 and no skills. */
    public Student(String studentId, String studentName, String email, String courseName) {
        this(studentId, studentName, email, courseName, 0, 0, null);
    }

    // ---------- Getters ----------

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getProgrammingScore() {
        return programmingScore;
    }

    public double getMathematicsScore() {
        return mathematicsScore;
    }

    /** Returns a read-only view so callers cannot modify internal state. */
    public List<String> getTechnicalSkills() {
        return Collections.unmodifiableList(technicalSkills);
    }

    // ---------- Setters (with validation) ----------
    // No setter for studentId: it is final.

    public void setStudentName(String studentName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        this.studentName = studentName.trim();
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        this.email = email.trim();
    }

    public void setCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty.");
        }
        this.courseName = courseName.trim();
    }

    public void setProgrammingScore(double programmingScore) {
        validateScore(programmingScore, "Programming");
        this.programmingScore = programmingScore;
    }

    public void setMathematicsScore(double mathematicsScore) {
        validateScore(mathematicsScore, "Mathematics");
        this.mathematicsScore = mathematicsScore;
    }

    public void setTechnicalSkills(List<String> skills) {
        this.technicalSkills = new ArrayList<>();
        if (skills != null) {
            for (String skill : skills) {
                addSkill(skill);
            }
        }
    }

    // ---------- Object methods ----------

    /** Adds a skill if it is non-empty and not already present (case-insensitive). */
    public boolean addSkill(String skill) {
        if (skill == null || skill.trim().isEmpty()) {
            return false;
        }
        for (String s : technicalSkills) {
            if (s.equalsIgnoreCase(skill.trim())) {
                return false;
            }
        }
        technicalSkills.add(skill.trim());
        return true;
    }

    public double calculateAverageScore() {
        return (programmingScore + mathematicsScore) / 2.0;
    }

    @Override
    public String toString() {
        return String.format(
            "Student {%n  ID: %s%n  Name: %s%n  Email: %s%n  Course: %s%n" +
            "  Programming: %.2f%n  Mathematics: %.2f%n  Average: %.2f%n  Skills: %s%n}",
            studentId, studentName, email, courseName,
            programmingScore, mathematicsScore, calculateAverageScore(),
            technicalSkills.isEmpty() ? "None" : String.join(", ", technicalSkills));
    }

    // ---------- Helper ----------

    private void validateScore(double score, String subject) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(subject + " score must be between 0 and 100.");
        }
    }
}

/** Entry point: the file name must match this public class. */
public class StudentApp {
    public static void main(String[] args) {
        // Full constructor
        Student s1 = new Student("S101", "Akshat", "akshat@example.com",
                "B.Tech Computer Science", 88.5, 92,
                Arrays.asList("Java", "React", "Node.js"));

        // Minimal constructor + setters
        Student s2 = new Student("S102", "Riya", "riya@example.com", "B.Tech IT");
        s2.setProgrammingScore(75);
        s2.setMathematicsScore(68);
        s2.addSkill("Python");
        s2.addSkill("python");   // duplicate, ignored

        System.out.println(s1);
        System.out.println();
        System.out.println(s2);

        // Validation demo
        try {
            s2.setEmail("akshat#kiet.edu");
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}