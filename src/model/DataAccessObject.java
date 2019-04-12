package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import utilities.HibernateUtils;

import java.util.List;

public class DataAccessObject {
    // Session Variables from Hibernate
    SessionFactory mySessionFactory = null;
    Session mySession = null;

    // Instance of DataAccessObject
    private static DataAccessObject singleInstance = null;

    private DataAccessObject() {
        mySessionFactory = HibernateUtils.getSessionFactory();
    }

    // Create a Singleton (Single Instance Only
    public static DataAccessObject getInstance() {
        if (singleInstance == null) {
            singleInstance = new DataAccessObject();
        }

        return singleInstance;
    }
    
    
    // Add a New User to the Database
    public boolean addNewUser(String username, String hashedPassword, String email,
    		String displayName, int userSalt) {
    	boolean isSuccessful = false;
    	int sumIdenticalUsers = 0;

    	try {
    		// Open Session and Begin Transaction
    		mySession = mySessionFactory.openSession();
    		mySession.getTransaction().begin();
    		
    		// Create SQL String
    		String hql = "INSERT INTO users (username, hashedPassword, email, displayName, userSalt) " +
    		"VALUES (:username, :hashedPassword, :email, :displayName, :userSalt)";
    		
    		// Assemble insert statement and tie variables safely
    		Query myQuery = mySession.createSQLQuery(hql);
    		myQuery.setParameter("username",  username);
    		myQuery.setParameter("hashedPassword", hashedPassword);
    		myQuery.setParameter("email",  email);
    		myQuery.setParameter("displayName", displayName);
    		myQuery.setParameter("userSalt",  userSalt);
    		
    		// Execute Query and Commit Transaction
    		myQuery.executeUpdate();
    		mySession.getTransaction().commit();
    		
    		// If reached this point, then the query should be successful
    		isSuccessful = true;
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		mySession.close();
    	}
    	
    	return isSuccessful;
    }
    
    public boolean login(String username, String hashedPassword) {
    	boolean isSuccessful = false;
    	
        try {
            mySession = mySessionFactory.openSession();
            mySession.getTransaction().begin();
            String hql = "SELECT id, username, hashedPassword, email, displayName, userSalt FROM model.UserAccount users WHERE username = :username";
    		Query myQuery = mySession.createSQLQuery(hql);
    		myQuery.setParameter("username",  username);
            UserAccount user = (UserAccount) myQuery.getSingleResult();
            mySession.getTransaction().commit();

            // Test if retrieved user has matching passwords
            if (user.getHashedPassword().contentEquals(hashedPassword)) {
            	isSuccessful = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            mySession.close();
        }
        
        return isSuccessful;
    }
    
    public int getSalt(String username) {
    	int salt = 0;
        try {
            mySession = mySessionFactory.openSession();
            mySession.getTransaction().begin();
            String hql = "SELECT userSalt FROM model.UserAccount users WHERE username = :username";
    		Query myQuery = mySession.createSQLQuery(hql);
    		myQuery.setParameter("username",  username);
            salt = Integer.parseInt((String) myQuery.getSingleResult());
            mySession.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            mySession.close();
        }
        
        return salt;
    }

}