# CodeQuest – Programming Learning Game

A small college project using HTML, CSS, JSP scriptlets, Java Servlets, JDBC, MySQL, JDK 17 and Tomcat 10.1. It does not use JSTL or JSP Expression Language.

## Requirements
- JDK 17
- Eclipse IDE for Enterprise Java and Web Developers (or Eclipse with web tools)
- Apache Tomcat 10.1
- MySQL 8.x
- Maven support in Eclipse

## 1. Create the database
1. Open MySQL Workbench.
2. Run the full script in `database/codequest_db.sql`.
3. The script creates `codequest_db`, the tables, a demo admin and three sample missions.
4. Demo admin login: **admin** / **admin123**.

The demo admin password is stored as a SHA-256 hash in the database. This is suitable only for a small classroom demo; use a dedicated password-hashing library for a real production app.

## 2. Configure the database connection
Open `src/main/java/com/codequest/DBConnection.java` and update `DB_USER` and `DB_PASSWORD` to match your local MySQL account. The defaults are `root` and an empty password.

If your MySQL server uses a different port, update the JDBC URL there too.

## 3. Import into Eclipse
1. Extract `CodeQuest.zip`.
2. In Eclipse select **File → Import → Maven → Existing Maven Projects**.
3. Select the extracted `CodeQuest` folder and finish.
4. Set the project JRE to JDK 17: **Project → Properties → Java Build Path**.
5. Add Apache Tomcat 10.1: **Window → Show View → Servers → New → Server**.
6. Right-click the project → **Maven → Update Project**.
7. Right-click the project → **Run As → Run on Server** and select Tomcat 10.1.

If Eclipse asks to convert it to a faceted project, select Dynamic Web Module 6.0 and Java 17.

## 4. Open the app
Visit `http://localhost:8080/CodeQuest/`

## Student flow
Register → Login → Dashboard → Missions → answer a question → earn XP → Leaderboard.

## Admin flow
Admin Login → Add Question → View Questions.

## Notes
- Servlets use `jakarta.servlet.*` because Tomcat 10.1 uses Jakarta APIs.
- All SQL statements use `PreparedStatement`.
- A student can submit each mission only once. The database also enforces this rule.
- Correct answers are read by the submission servlet only; the missions page does not display them.
- XP and submission updates happen in a transaction.
- Student and admin authentication use separate session attributes.
- The app is for learning and classroom demonstration, not production deployment.
