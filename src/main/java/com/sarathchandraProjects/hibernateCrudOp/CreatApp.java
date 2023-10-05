package com.sarathchandraProjects.hibernateCrudOp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.sarathchandraProjects.hibernateCrudOp.entites.Student;

/**
 * Create operation
 *
 */
public class CreatApp 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(Student.class); //Informing hibernate in Java style about the entities
        
        SessionFactory sessionFactory = configuration.buildSessionFactory(); /* Building the sessionFactory instance. note that sessionFactory instance is immutable
                                                                                So what ever configuration need to be done should be done before building the sessionFactory instance itself.
                                                                                Its a heavy weight object, so only 1 instance per Datasource
                                                                             */
                                                                                
        
        
        Session session = sessionFactory.openSession();  /* Session instance creation from SessionFactory.. A factory design pattern.
                                                            note that, session is a mutable object also.. can create multiple instances of session.
                                                            not a heavy weight object
                                                         */
        
        
        // Below starts the actual crud opearation
        
        Student std1 = new Student("Sarath", 24, 903234L); // Transient object
        
        Student std2 = new Student(); // Another Transient object
        std2.setAge(24);
        std2.setMobileNo(123456L);
        std2.setName("Sasidhar");
        
        Transaction transaction = session.beginTransaction(); /* beginTransaction() returns the transaction instance + starts the transaction.
                                                                 Note that, any DML operation to be performed on the DB requires the transaction
                                                                to be initiated compulsory.
                                                              */
                                                              
         // Saving the data into DB                                                       
        session.persist(std1); // std1 now moves from Transient state --> Persistent state
        session.persist(std2); // std2 now moves from Transient state --> Persistent state
        
        transaction.commit(); // transaction.commit() = flush() + commit() 
        
        
        System.out.println("Is transaction is still Active: "+transaction.isActive()); /* As soon as the commit() executes, the above transaction is done.
                                                                                          So, its no more active -- returns false
                                                                                       */
        
        
        System.out.println("Is session still open: "+session.isOpen()); /* note that the session is still active.. the session is closed only close() is triggered.
                                                                        */

    }
}
