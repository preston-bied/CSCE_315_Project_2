import java.sql.*;
import java.io.*;
import java.util.Scanner;

/*
CSCE 315
9-27-2021 Lab
 */
public class populateProducts {

  //Commands to run this script
  //This will compile all java files in this directory
  //javac *.java
  //This command tells the file where to find the postgres jar which it needs to execute postgres commands, then executes the code
  //Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  //Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

  //MAKE SURE YOU ARE ON VPN or TAMU WIFI TO ACCESS DATABASE
  public static void main(String args[]) {

    //Building the connection with your credentials
    Connection conn = null;
    String teamNumber = "2";
    String sectionNumber = "950";
    String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
    String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    dbSetup myCredentials = new dbSetup(); 

    //Connecting to the database
    try {
        conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
     } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
     }

     System.out.println("Opened database successfully");

     try{
       //create a statement object
       Statement stmt = conn.createStatement();

       //Running a query
       //TODO: update the sql command here
       File file = new File("testData.csv");
       FileReader fileReader = new FileReader(file);
       BufferedReader bufferedReader = new BufferedReader(fileReader);
       String line = bufferedReader.readLine();
       String delimiter = ",";
       String[] colValues;
       String sqlStatement = "CREATE TABLE IF NOT EXISTS products (productName VARCHAR, productID INT PRIMARY KEY, orderPrice MONEY, sellPrice MONEY, currentStock FLOAT, desiredStock FLOAT, quantitySoldYTD FLOAT)";
       stmt.execute(sqlStatement);
       sqlStatement = "GRANT ALL ON products TO csce315950_preston";
       stmt.execute(sqlStatement);

       while (line != null) {
           colValues = line.split(delimiter);
           sqlStatement = "INSERT INTO products (productName, productID, orderPrice, sellPrice, currentStock, desiredStock, quantitySoldYTD) VALUES(";
           sqlStatement += "'" + colValues[0] + "', '" + colValues[1] + "', '" + colValues[2] + "', '" + colValues[3] + "', '" + colValues[4] + "', '" + colValues[5] + "', '" + colValues[6] + "')";
           stmt.execute(sqlStatement);
           line = bufferedReader.readLine();
       }

       bufferedReader.close();


       //send statement to DBMS
       //This executeQuery command is useful for data retrieval
       //ResultSet result = stmt.executeQuery(sqlStatement);
       //OR
       //This executeUpdate command is useful for updating data
       int result = stmt.executeUpdate(sqlStatement);

       //OUTPUT
       //You will need to output the results differently depeninding on which function you use
       //System.out.println("--------------------Query Results--------------------");
       //while (result.next()) {
       //System.out.println(result.getString("column_name"));
       //}
       //OR
       //System.out.println(result);
   } catch (Exception e){
       e.printStackTrace();
       System.err.println(e.getClass().getName()+": "+e.getMessage());
       System.exit(0);
   }

    //closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch(Exception e) {
      System.out.println("Connection NOT Closed.");
    }//end try catch
  }//end main
}//end Class
