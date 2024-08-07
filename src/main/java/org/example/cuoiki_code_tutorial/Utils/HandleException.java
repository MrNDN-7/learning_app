package org.example.cuoiki_code_tutorial.Utils;

import java.sql.SQLException;


/**
 * Servlet implementation class HandleException
 */
@SuppressWarnings("serial")

public class HandleException{
	 @SuppressWarnings("unused")
	
	public static void printSQLException(SQLException e) {
		// TODO Auto-generated method stub
		for (Throwable e1: e) {
            if (e1 instanceof SQLException) {
                e1.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e1).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e1).getErrorCode());
                System.err.println("Message: " + e1.getMessage());
                Throwable t = e1.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
		
	}

}
