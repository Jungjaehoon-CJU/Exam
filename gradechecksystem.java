package pkg;
import java.io.*;
import java.util.*;

// 성적 객체
class Grade {
    private String subject;
    private int score;

    public Grade(String subject, int score) {
        this.subject = subject;
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return subject + ": " + score;
    }
}

// 메인 프로그램 클래스
public class gradechecksystem {
    private Map<String, List<Grade>> studentGrades = new HashMap<>();

    // 메인 로직
    public static void main(String[] args) throws IOException {
        gradechecksystem system = new gradechecksystem();
        system.run();
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("성적 확인 프로그램");
        System.out.println("1. 교수 (성적 입력)");
        System.out.println("2. 학생 (성적 확인)");
        System.out.print("선택하세요: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 소비

        if (choice == 1) {
            handleProfessorInput(scanner);
        } else if (choice == 2) {
            handleStudentCheck(scanner);
        } else {
            System.out.println("잘못된 선택입니다.");
        }
    }

    // 교수 기능: 성적 입력 및 저장
    private void handleProfessorInput(Scanner scanner) throws IOException {
        System.out.println("성적을 입력하세요.");
        System.out.print("학번: ");
        String studentId = scanner.nextLine();

        studentGrades.putIfAbsent(studentId, new ArrayList<>());

        System.out.print("과목명: ");
        String subject = scanner.nextLine();

        System.out.print("점수: ");
        int score = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 소비

        studentGrades.get(studentId).add(new Grade(subject, score));

        System.out.println("성적이 입력되었습니다.");
        saveGradesToFile("grades.txt");
    }

    // 학생 기능: 성적 확인
    private void handleStudentCheck(Scanner scanner) throws IOException {
        loadGradesFromFile("grades.txt");

        System.out.print("학번을 입력하세요: ");
        String studentId = scanner.nextLine();

        if (studentGrades.containsKey(studentId)) {
            System.out.println("성적 조회 결과:");
            for (Grade grade : studentGrades.get(studentId)) {
                System.out.println(grade);
            }
        } else {
            System.out.println("해당 학번으로 성적을 찾을 수 없습니다.");
        }
    }

    // 파일에 성적 저장
    private void saveGradesToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (String studentId : studentGrades.keySet()) {
            for (Grade grade : studentGrades.get(studentId)) {
                writer.write(studentId + "|" + grade.getSubject() + "|" + grade.getScore());
                writer.newLine();
            }
        }
        writer.close();
        System.out.println("성적이 파일에 저장되었습니다.");
    }

    // 파일에서 성적 로드
    private void loadGradesFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        studentGrades.clear(); // 기존 데이터 초기화

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            String studentId = parts[0];
            String subject = parts[1];
            int score = Integer.parseInt(parts[2]);

            studentGrades.putIfAbsent(studentId, new ArrayList<>());
            studentGrades.get(studentId).add(new Grade(subject, score));
        }
        reader.close();
    }
}
