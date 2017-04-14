package com.itechart.javalab.webservice.rest.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static HibernateUtil instance;

    private SessionFactory sf;

    public static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    private HibernateUtil() {
        this.sf = new Configuration().configure().buildSessionFactory();
    }

    public Session openSession() {
        Session session = sf.openSession();
        session.beginTransaction();
        return session;
    }

    public void commitAndClose(Session session) {
        session.getTransaction().commit();
        session.close();
    }
}
