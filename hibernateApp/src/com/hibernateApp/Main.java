package com.hibernateApp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class Main {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(City.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();

		try {
		
			session.beginTransaction();
			
			List<City> cities = session.createQuery("from City c where c.countryCode='TUR' AND c.district='Ankara'").getResultList();
			
			for(City city:cities) {
				
				System.out.println(city.getName());
			}

			
			System.out.println("** ORDER BY **");
			
			cities = session.createQuery("from City c where c.countryCode='TUR' ORDER BY c.name DESC").getResultList();
			
			for(City city:cities) {
				
				System.out.println(city.getName());
			}

			
			System.out.println("** GROUP BY **");
			

			List<String> countryCodes = session.createQuery("select c.countryCode from City c Group BY c.countryCode").getResultList();
			
			for(String countryCode:countryCodes) {
				
				System.out.println(countryCode);
			}


			
			System.out.println("** ** INSERT ** **");
			
			City city = new City();
			city.setName("Tekirdag 59");
			city.setCountryCode("TUR");
			city.setDistrict("Marmara");
			city.setPopulation(130000);
			
			session.save(city);
			
			cities = session.createQuery("from City c where c.name like '%59'").getResultList();
			
			for(City citi:cities) {
				
				System.out.println(citi.getName());
			}
			
			

			
			System.out.println("** ** UPDATE ** **");
			
			City c = session.get(City.class, 4086);
			c.setName("Tekfurdag");
			
			session.save(c);
		 
			cities = session.createQuery("from City c where c.name like 'Tek%'").getResultList();
			
			
			for(City citi:cities) {
				
				System.out.println(citi.getName());
			}
		
			
			System.out.println("** ** DELETE ** **");
			
			City delcity = session.get(City.class, 4087);
			
			session.delete(delcity);
			
			System.out.println("4087 Nolu þehir silindi.");		
			
			session.getTransaction().commit();
			
			
			
		} finally {
			factory.close();
		}
		
	}

}
