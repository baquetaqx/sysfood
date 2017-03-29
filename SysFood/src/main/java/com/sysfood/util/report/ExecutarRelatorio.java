package com.sysfood.util.report;

import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class ExecutarRelatorio {
	
	public static void executar(Work executor){
		try (Session session = (Session) Persistence.createEntityManagerFactory("SysFoodPU").createEntityManager()) {
			session.doWork(executor);
		}
	}

}
