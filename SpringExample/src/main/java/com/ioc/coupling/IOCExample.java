package com.ioc.coupling;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCExample {
    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationIocLooseCouplingExample.xml");


        UserManager userManagerWithDB = (UserManager) context.getBean("userManagerWithUserDataProvider");
        System.out.println(userManagerWithDB.getUserInfo());


        UserManager userManagerWithWS = (UserManager) context.getBean("userManagerWithWebServiceDataProvider");
        System.out.println(userManagerWithWS.getUserInfo());

/
        UserManager newDatabaseProvider = (UserManager) context.getBean("userManagerWithNewDatabaseProvider") ;
        System.out.println(newDatabaseProvider.getUserInfo());
    }
}
