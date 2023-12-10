import java.util.*;

class User {
    String username;
    String password;
    Map<String, Integer> progress;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.progress = new HashMap<>();
    }
}

class QuizQuestion {
    String question;
    String answer;
    String category;
    String level;

    public QuizQuestion(String question, String answer, String category, String level) {
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.level = level;
    }
}

class QuizGame {
    List<User> users;
    List<QuizQuestion> questions;

    public QuizGame() {
        this.users = new ArrayList<>();
        this.questions = new ArrayList<>();
    }
    //product backlog: user account creation
    public void addUser(String username, String password) {
        users.add(new User(username, password));
    }
    //product backlog: quiz category selection
    public void addQuestion(String question, String answer, String category, String level) {
        questions.add(new QuizQuestion(question, answer, category, level));
    }

    public boolean authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void startGame(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            //product backlog: quiz category selection
            System.out.println("Choose a category: (Python, Java, C, C++)");
            String category = scanner.nextLine();

            //quiz level selection
            System.out.println("Choose a level: (Beginner, Intermediate, Advanced)");
            String level = scanner.nextLine();

            List<QuizQuestion> availableQuestions = getAvailableQuestions(category, level);


            System.out.println("Let's start you Coding Challenge!");

            int score = 0;

            for (QuizQuestion question : availableQuestions) {
                System.out.println(question.question);
                String userAnswer = scanner.nextLine();

                if (userAnswer.equalsIgnoreCase(question.answer)) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer is: " + question.answer);
                }
            }

            System.out.println("Quiz completed. Your score: " + score);

            //update user progress
            user.progress.put(category, user.progress.getOrDefault(category, 0) + score);

            System.out.println("Do you want to play again? (yes/no)");
            String playAgain = scanner.nextLine();

            if (!playAgain.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }

    private List<QuizQuestion> getAvailableQuestions(String category, String level) {
        List<QuizQuestion> availableQuestions = new ArrayList<>();

        for (QuizQuestion question : questions) {
            if (question.category.equalsIgnoreCase(category) && question.level.equalsIgnoreCase(level)) {
                availableQuestions.add(question);
            }
        }

        return availableQuestions;
    }
}

public class Main {
    public static void main(String[] args) {
        QuizGame quizGame = new QuizGame();

        //add users
        quizGame.addUser("user1", "password1");
        quizGame.addUser("user2", "password2");
        quizGame.addUser("user3", "password3");
        quizGame.addUser("user4", "password4");
        quizGame.addUser("user5", "password5");


        //add example quiz questions
        quizGame.addQuestion("Write a Java program to print 'Hello, World!'", "System.out.println(\"Hello, World!\");", "Java", "Beginner");
        quizGame.addQuestion("Write a C program to calculate the factorial of a number", "/* your code here */", "C", "Beginner");
        quizGame.addQuestion("What is the purpose of 'cout' in C++?", "To print output to the console", "C++", "Beginner");

        Scanner scanner = new Scanner(System.in);

        //user authentication
        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (quizGame.authenticateUser(username, password)) {
            System.out.println("Authentication successful. Welcome, " + username + "!");
            User currentUser = quizGame.users.stream().filter(u -> u.username.equals(username)).findFirst().orElse(null);
            quizGame.startGame(currentUser);
        } else {
            System.out.println("Authentication failed. Invalid username or password.");
        }
    }
}
