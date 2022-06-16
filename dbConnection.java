package application;

public class dbConnection {
    
    /**
     * 
     * @return dbConnectionString is the string used to connect to the database
     */
    public static String connectToDatabase() {
		String teamNumber = "2";
		String sectionNumber = "950";
		String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
		
		String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
		return dbConnectionString;
  	}
}