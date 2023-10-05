package com.sarathchandraProjects.hibernateCrudOp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.sarathchandraProjects.hibernateCrudOp.entites.Student;

public class UpdateApp {

	public static void main(String[] args) {
		
		Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(Student.class);
        
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        
        Student std1 = new Student("Sarath", 24, 903234L);
        
        Student std2 = new Student();
        std2.setAge(24);
        std2.setMobileNo(123456L);
        std2.setName("Sasidhar");
        
        Transaction transaction = session.beginTransaction();
        
        session.persist(std1);
        session.persist(std2);
        
        transaction.commit();
        
        
        System.out.println("Is transaction is still Active: "+transaction.isActive());
        System.out.println("Is session still open: "+session.isOpen());
        
        
        Student session_setudent1 = session.get(Student.class, 1); /* Using the same session instance. So after creating the new records,
        															  trying to fetch one of them using the same session instance.. to see what happens in the log.
        															  
        															  Result: No select stmt will execute unlike the regular get(). Simply the hibernate will fetch
        															  the data from the session-cache.
         														   */
        System.out.println("sesson_student1: "+session_setudent1);
        session.close();  
        System.out.println("is Session still open: "+session.isOpen());
        
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();
        
        System.out.println("Is transaction2 is still Active: "+transaction2.isActive());
        
        Student session2_student1 = session2.get(Student.class, 1); /* Now under new session : "session2", checking if the select query will run when
         													-		   fetching the data using the get().	
         													
         																Note that, the object is in the persistent state, as the session is currently holding the object.
         														    */
        System.out.println("Student1 :"+session2_student1);  
        session2_student1.setAge(89);                              /*  Simply update the age using the **setter method.
        
                                                                        This will trigger the UPDATE query, bcz of automatic dirty checking..
                                                                        
                                                                        Also, as we're fetching the record and modifying the one of the field, 
                                                                        during the update only that column's data alone is modified. no null will be inserted for the other columns.
                                                                        
                                                                        note that, technically when the complier executes the transaction2.commit() line 
                                                                        both flush and commit happens in the DB.. 
                                                                        
                                                                        or else if there is an explict session.flush() is written, then the update query will
                                                                        execute which will sync the in-memory state of the instance to the DB. 
                                                                        
                                                                        However,the data becomes permenant only after the transaction.commit() is executed.
         														   */
//#####################################################################################################################//        
        /* Other way of doing the update operation with the help of the complete new instance.
		   Unfortunately, the other column values are ***set to null say mobile number, as its not initialized here.     
           This is difference b/w update by get() vs update() using a new instance   
   */  
        
        
        Student session2_newStudentObj = new Student();
        session2_newStudentObj.setAge(100); //Here, we set the values to those fields which is expected to be changed of a record.
        session2_newStudentObj.setName("Mayank");//Here, we set the values to those fields which is expected to be changed of a record.
        session2_newStudentObj.setId(2); //Here the id field is the primary key, hence we've to set the value of it such that the where clause will be holding this value.
        session2.update(session2_newStudentObj);
        
        transaction2.commit();
	}
}