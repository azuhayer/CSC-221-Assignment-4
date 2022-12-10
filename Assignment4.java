import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import java.sql.SQLException;

public class Assignment4 extends Application
{
    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Assignment 4 Output By Zuhayer Alvi");

        double width = 900;     //Width of canvas
        double height = 600;    //Height of canvas

        //radius of pie chart
        double radius = 200;

        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext GC = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();

        //Point declaration
        MyPoint center = new MyPoint(width / 2, height / 2, MyColor.BLACK);

        String server = "jdbc:mysql://localhost:3306/mysql";             //SQL SERVER PATH
        String username = "root";                                        //USERNAME
        String password = "zuzu1234";                                    //PASSWORD

        try
        {
            StudentsDatabase DataBase = new StudentsDatabase(server, username, password);
            String TextFile = "/Users/zuhayer/Desktop/ScheduleSpring2022.txt";

            //Make Schedule Table in SQL
            String sqlTable = StudentsDatabaseInterface.TableSchedule;
            String populateTable = StudentsDatabaseInterface.FillTableSchedule(TextFile);
            StudentsDatabase.Schedule schedule = DataBase.new Schedule(sqlTable, populateTable);

            //Make Courses Table in SQL
            sqlTable = StudentsDatabaseInterface.TableCourses;
            populateTable = StudentsDatabaseInterface.FillTableCourses();
            StudentsDatabase.Courses courses = DataBase.new Courses(sqlTable, populateTable);

            //Make Students Table in SQL
            sqlTable = StudentsDatabaseInterface.TableStudents;
            StudentsDatabase.Students students = DataBase.new Students(sqlTable);

            //Insert to Students Table
            students.insertStudents("45824489", "Zuhayer", "Alvi", "azuhayer@gmail.com", "M");
            students.insertStudents("22221666", "Aki", "Hayakawa", "haki@gmail.com", "M");
            students.insertStudents("26018190", "Kobeni", "Higashiyama", "hkobeni@gmail.com", "F");
            students.insertStudents("30851589", "Mitaka", "Asa", "amitaka@gmail.com", "F");
            students.insertStudents("41984660", "Yoshida", "Hirofumi", "hyoshida@gmail.com", "M");
            students.insertStudents("29783826", "Eren", "Yaeger", "yeren@gmail.com", "M");
            students.insertStudents("94476196", "Mikasa", "Ackerman", "amikasa@gmail.com", "F");
            students.insertStudents("80697167", "Levi", "Ackerman", "alevi@gmail.com", "M");
            students.insertStudents("54325983", "Sasha", "Blouse", "bsasha@gmail.com", "F");
            students.insertStudents("44346492", "Reiner", "Braun", "breiner@gmail.com", "M");
            students.insertStudents("71130309", "Piek", "Finger", "fpiek@gmail.com", "F");
            students.insertStudents("63608470", "Annie", "Leonhart", "lannie@gmail.com", "F");
            students.insertStudents("52933116", "Historia", "Reiss", "rhistoria@gmail.com", "F");
            students.insertStudents("46829091", "Hange", "Zoe", "zhange@gmail.com", "F");
            students.insertStudents("63902835", "Armin", "Arlert", "aarmin@gmail.com", "M");
            students.insertStudents("84424620", "Conny", "Springer", "sconny@gmail.com", "M");
            students.insertStudents("58741031", "Erwin", "Smith", "serwin@gmail.com", "M");
            students.insertStudents("31272500", "Jean", "Kirschtein", "kjean@gmail.com", "M");
            students.insertStudents("83555296", "Gabi", "Braun", "bgabi@gmail.com", "F");
            students.insertStudents("95429004", "Lara", "Tybur", "tlara@gmail.com", "F");
            students.insertStudents("10668094", "Willy", "Tybur", "twilly@gmail.com", "M");
            students.insertStudents("90437525", "Ymir", "Fritz", "fymir@gmail.com", "F");
            students.insertStudents("53992629", "King", "Fritz", "fking@gmail.com", "M");
            students.insertStudents("84904275", "Sina", "Fritz", "fsina@gmail.com", "F");
            students.insertStudents("16066698", "Rose", "Fritz", "frose@gmail.com", "F");

            //Make Classes Table in SQL
            sqlTable = StudentsDatabaseInterface.TableClasses;
            StudentsDatabase.Classes classes = DataBase.new Classes(sqlTable);

            //Insert to Classes Table
            classes.insertClasses("10000 PP", "45824489", "34143", "2021", "Spring", "A");
            classes.insertClasses("10200 CC1", "22221666", "32118", "2021", "Spring", "B");
            classes.insertClasses("10200 CC2", "26018190", "32119", "2021", "Spring", "B");
            classes.insertClasses("10200 CC3", "30851589", "32139", "2021", "Spring", "C");
            classes.insertClasses("10200 MM1", "41984660", "32140", "2021", "Spring", "F");
            classes.insertClasses("10200 MM2", "29783826", "32141", "2021", "Spring", "A");
            classes.insertClasses("10200 MM3", "94476196", "32155", "2021", "Spring", "C");
            classes.insertClasses("10300 CC1", "80697167", "32120", "2021", "Spring", "D");
            classes.insertClasses("10300 CC2", "54325983", "32121", "2021", "Spring", "W");
            classes.insertClasses("10300 MM1", "44346492", "32122", "2021", "Spring", "A");
            classes.insertClasses("10300 MM2", "71130309", "32123", "2021", "Spring", "C");
            classes.insertClasses("10400 EF1", "63608470", "32124", "2021", "Spring", "B");
            classes.insertClasses("10400 PR1", "52933116", "32125", "2021", "Spring", "B");
            classes.insertClasses("11300 2L", "46829091", "32142", "2021", "Spring", "F");
            classes.insertClasses("11300 2N", "63902835", "32126", "2021", "Spring", "D");

            //Make AggregateGrades Table in SQL
            sqlTable = StudentsDatabaseInterface.TableAggregateGrades;
            populateTable = StudentsDatabaseInterface.FillTableAggregateGrades();
            StudentsDatabase.AggregateGrades aggregateGrades = DataBase.new AggregateGrades(sqlTable, populateTable);

            //Declare PieChart for AggregateGrades and draw onto canvas
            String sqlQuery = "SELECT * FROM AggregateGrades";
            HistogramAlphabet histogram = new HistogramAlphabet(aggregateGrades.getAggregateGrades(sqlQuery));
            HistogramAlphabet.MyPieChart pieChart = histogram.new MyPieChart(6, center, radius, 90);
            pieChart.draw(GC);
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        launch();
    }
}
