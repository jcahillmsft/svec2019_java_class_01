import java.util.Scanner;

public class ClassRosterProgram {
    public static void main(String[] args) {
        boolean done = false;
        Scanner scanner = new Scanner(System.in);
        Roster roster = new Roster();

        do {
            System.out.println("Class Roster");
            System.out.println("============");
            System.out.println("L - List all students");
            System.out.println("N - New student");
            System.out.println("U - Update an existing student by name");
            System.out.println("X - Exit the program");
            System.out.println();
            String userInput = scanner.nextLine().trim().toUpperCase();

            if (userInput.length() >= 1) {
                char command = userInput.charAt(0);
                if (command == 'L') {
                    roster.print();
                }
                else if (command == 'N') {
                    Student newStudent = inputStudent(scanner);
                    roster.addStudent(newStudent);
                }
                else if (command == 'U') {
                    String fullName = inputFullName(scanner);
                    int studentIndexToUpdate = roster.findIndexByFullName(fullName);
                    if (studentIndexToUpdate >= 0) {
                        Student updatedStudent = inputStudent(scanner);
                        roster.updateStudent(studentIndexToUpdate, updatedStudent.firstName, updatedStudent.lastName, updatedStudent.gradeAverage, updatedStudent.birthday);
                    }
                    else {
                        System.out.println("Student not found!");
                    }
                }
                else if (command == 'X') {
                    done = true;
                }
            }
        } while (!done);
        scanner.close();
    }

    public static String inputFullName(Scanner scanner) {
        System.out.println("Enter first and last name of student to update: ");
        String fullName = scanner.nextLine().trim();
        return fullName;
    }

    public static Student inputStudent(Scanner scanner) {
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.println("Enter GPA: ");
        double gpa = Double.parseDouble(scanner.nextLine().trim());
        System.out.println("Enter birthday: ");
        String birthday = scanner.nextLine().trim();

        return new Student(firstName, lastName, gpa, birthday);
    }

    public static class Student {
        public String firstName;
        public String lastName;
        public double gradeAverage;
        public String birthday;

        public Student(String firstName, String lastName, double gradeAverage, String birthday) {
            update(firstName, lastName, gradeAverage, birthday);
        }

        public void printFullName() {
            System.out.println(String.format("Name: %s %s", firstName, lastName));
        }

        public void printAll() {
            printFullName();
            System.out.println(String.format("GPA: %1.2f", gradeAverage));
            System.out.println(String.format("Birthday: %s", birthday));
        }

        public void update(String firstName, String lastName, double gradeAverage, String birthday) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.gradeAverage = gradeAverage;
            this.birthday = birthday;
        }
    }

    public static class Roster {
        public static final int MAX_STUDENTS = 50;

        public Student[] students;
        public int studentCount;

        public Roster() {
            students = new Student[MAX_STUDENTS];
            studentCount = 0;
        }

        public void addStudent(Student student) {
            if (studentCount < MAX_STUDENTS) {
                students[studentCount] = student;
                studentCount += 1;
            }
            else {
                System.out.println("Too many students added to the roster.");
            }
        }

        public void print() {
            for (int i = 0; i < studentCount; ++i) {
                System.out.print(String.format("%d) ", i + 1));
                students[i].printFullName();
            }
        }

        public int findIndexByFullName(String fullName) {
            for (int i = 0; i < studentCount; ++i) {
                String studentFullName = String.format("%s %s", students[i].firstName, students[i].lastName);
                if (studentFullName.equalsIgnoreCase(fullName)) {
                    return i;
                }
            }

            return -1;
        }

        public void updateStudent(int index, String firstName, String lastName, double gradeAverage, String birthday) {
            if (index >= 0 && index < studentCount) {
                Student student = students[index];
                student.update(firstName, lastName, gradeAverage, birthday);
            }
            else {
                System.out.println("Invalid student index specified.");
            }
        }
    }
}
