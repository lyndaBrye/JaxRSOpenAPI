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

		// Organisateurs
		Organisateur organisateur1 = new Organisateur("John", "Doe", "75000", "john.doe@email.com", "123456789", "password123", 25, Sexe.HOMME);
		Organisateur organisateur2 = new Organisateur("Emma", "Stone", "69000", "emma.stone@email.com", "987654321", "password789", 30, Sexe.FEMME);
		manager.persist(organisateur1);
		manager.persist(organisateur2);

		// Artistes
		Artiste artiste1 = new Artiste("Ed Sheeran", "Edward", "Chanteur Britannique");
		Artiste artiste2 = new Artiste("Adele", "Adkins", "Chanteuse Britannique");
		Artiste artiste3 = new Artiste("The Weeknd", "Abel", "Chanteur Canadien");
		manager.persist(artiste1);
		manager.persist(artiste2);
		manager.persist(artiste3);

		// Concerts et liaisons avec les artistes et organisateurs
		Concert concert1 = new Concert(LocalDateTime.now(), "Paris", 5000);
		concert1.setArtiste(artiste1);
		concert1.setOrganisateur(organisateur1);
		manager.persist(concert1);

		Concert concert2 = new Concert(LocalDateTime.now().plusDays(1), "Lyon", 3000);
		concert2.setArtiste(artiste2);
		concert2.setOrganisateur(organisateur2);
		manager.persist(concert2);

		Concert concert3 = new Concert(LocalDateTime.now().plusDays(2), "Marseille", 7000);
		concert3.setArtiste(artiste3);
		concert3.setOrganisateur(organisateur1);
		manager.persist(concert3);

		// Utilisateurs
		User user1 = new User("Alice", "Smith", "75000", "alice.smith@email.com", "123456789", "password456", 25, Sexe.FEMME);
		User user2 = new User("Bob", "Brown", "69000", "bob.brown@email.com", "987654321", "password012", 30, Sexe.HOMME);
		User user3 = new User("Charlie", "Green", "13000", "charlie.green@email.com", "567812345", "password345", 28, Sexe.HOMME);
		User user4 = new User("David", "White", "75000", "david.white@email.com", "012345678", "password789", 32, Sexe.HOMME);
		User user5=new User("Emma", "Smith", "75000", "emma.smith@email.com", "123456789", "password456", 25, Sexe.FEMME);
		manager.persist(user1);
		manager.persist(user2);
		manager.persist(user3);
		manager.persist(user4);
		manager.persist(user5);

		// Tickets liés à l’utilisateur et au concert
		Ticket ticket1 = new Ticket(50, concert1, user1); // Concert 1, User 1
		Ticket ticket1bis = new Ticket(50, concert1, user2);
		Ticket ticket2 = new Ticket(80, concert1, user2); // Concert 1, User 2 (VIP)
		Ticket ticket3 = new Ticket(30, concert2, user3); // Concert 2, User 3 (Tarif étudiant)
		Ticket ticket4 = new Ticket(60, concert2, user1); // Concert 2, User 1
		Ticket ticket5 = new Ticket(100, concert3, user2); // Concert 3, User 2 (VIP)
		Ticket ticket6 = new Ticket(40, concert3, user3); // Concert 3, User 3
		Ticket ticket7 = new Ticket(70, null, null); // Concert 3, User 4
		manager.persist(ticket1);
		manager.persist(ticket2);
		manager.persist(ticket3);
		manager.persist(ticket4);
		manager.persist(ticket5);
		manager.persist(ticket6);
		manager.persist(ticket1bis);
		manager.persist(ticket7);
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
