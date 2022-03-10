package com.jointthinker.framework.persistence;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.jointthinker.framework.common.logging.Log4jlogging;
import com.jointthinker.framework.persistence.hibernate.HibernateStrategy;

public class PersistentFactory {

	private static PersistentFactory _singleton;
	
	private SessionFactory sessionFactory = null;
	
	private PersistentFactory(){
		try {
			Configuration conf = new AnnotationConfiguration().configure();
            sessionFactory = conf.buildSessionFactory();
            Log4jlogging.debug("Building SessionFactory Successed.");
        } catch (HibernateException ex) {
            throw new RuntimeException("Exception building SessionFactory: "
                    + ex.getMessage(), ex);
        }
	}
	
	public static PersistentFactory getInstance(){
		if(_singleton==null){
			_singleton = new PersistentFactory();
			//System.out.println("-----PersistentFactory getInstance()()----null---");
		}
		//System.out.println("-----PersistentFactory getInstance()()-------");
		return _singleton;
	}
	
	public IPersistentStrategy getRawPersistentStrategy(){
		//System.out.println("-----getRawPersistentStrategy()-------");
		return new HibernateStrategy(sessionFactory);
	}
}
