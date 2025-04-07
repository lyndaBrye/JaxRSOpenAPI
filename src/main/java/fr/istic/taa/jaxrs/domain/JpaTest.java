package fr.istic.taa.jaxrs.domain;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.*;
import java.time.LocalDate;


import java.time.LocalDateTime;
import java.util.List;

public class JpaTest {

	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory =
				Persistence.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			test.createData();
			/*test.createEmployees();*/

		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();

		/*test.listEmployees();*/

		manager.close();
		System.out.println(".. done");
	}

	private void createData() {
		Concert concert1 = new Concert(LocalDate.now().atStartOfDay(), "Lyon", 1000, 50);
		manager.persist(concert1);
		// Organisateurs
		Organisateur organisateur1 = new Organisateur("John", "Doe", "75000", "john.doe@email.com", "123456789", "password123", 25, Sexe.HOMME);
		manager.persist(organisateur1);
		organisateur1.getConcerts().add(concert1);


		// Artistes
		Artiste artiste1 = new Artiste("Ed Sheeran", "Edward", "Chanteur Britannique");
		manager.persist(artiste1);


		// Concerts et liaisons avec les artistes et organisateurs


	}



	private void listData() {
		List<Concert> concerts = manager.createQuery("SELECT c FROM Concert c", Concert.class).getResultList();
		System.out.println("Nombre de concerts : " + concerts.size());

		List<Artiste> artistes = manager.createQuery("SELECT a FROM Artiste a", Artiste.class).getResultList();
		System.out.println("Nombre d'artistes : " + artistes.size());

		List<User> users = manager.createQuery("SELECT u FROM User u", User.class).getResultList();
		System.out.println("Nombre d'utilisateurs : " + users.size());

		List<Ticket> tickets = manager.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
		System.out.println("Nombre de tickets : " + tickets.size());


	}
}
	/*private void createEmployees() {
		int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
		if (numOfEmployees == 0) {
			Department department = new Department("java");
			manager.persist(department);

			manager.persist(new Employee("Jakab Gipsz",department));
			manager.persist(new Employee("Captain Nemo",department));

		}
	}
	public void createArtiste(){
		int numOfArtiste=manager.createQuery("Select a From Artiste a",Artiste.class).getResultList().size();
		if(numOfArtiste==0){

		}
	}

	private void listEmployees() {
		List<Employee> resultList = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
		System.out.println("num of employess:" + resultList.size());
		for (Employee next : resultList) {
			System.out.println("next employee: " + next);
		}
	}
	}
	 */
