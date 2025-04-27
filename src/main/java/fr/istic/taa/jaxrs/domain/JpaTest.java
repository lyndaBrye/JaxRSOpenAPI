package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class JpaTest {

	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();

		try {
			test.createUsers();
			test.listUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}

		tx.commit();
		manager.close();
		System.out.println("✅ Done");
	}

	public void createUsers() {
		User user1 = new User("Alice", "Martin", "75001", "alice@example.com", "0601020304", "pwd123", 28, Sexe.FEMME);
		User user2 = new User("Bob", "Dupont", "69000", "bob@example.com", "0611223344", "secret", 35, Sexe.HOMME);
		User user3 = new User("Claire", "Durand", "33000", "claire@example.com", "0622334455", "azerty", 24, Sexe.FEMME);
		manager.persist(user1);
		manager.persist(user2);
		manager.persist(user3);

		// 👨‍💼 Organisateur
		Organisateur organisateur = new Organisateur("John", "Doe", "75001", "john@example.com", "0612345678", "password", 30, Sexe.HOMME,"Life");
		manager.persist(organisateur);

		// 🎤 Artiste
		Artiste artiste = new Artiste("Metallica", "Metal", "Groupe de metal américain","https://example.com/image.jpg");
		manager.persist(artiste);

		// 🎫 Concert
		Concert concert = new Concert(LocalDateTime.now().plusDays(20), "Parc de la Villette", 1000, 10.0,"https://example.com/image.jpg", "Con ");
		concert.setOrganisateur(organisateur);
		concert.setArtiste(artiste);
		manager.persist(concert);

		System.out.println("✅ Utilisateurs, organisateur, artiste et concert créés !");
	}


	public void listUsers() {
		List<User> users = manager.createQuery("SELECT u FROM User u", User.class).getResultList();
		System.out.println("📋 Utilisateurs présents en base : " + users.size());
		for (User u : users) {
			System.out.println(" - " + u.getPrenom() + " " + u.getNom() + " (" + u.getEmail() + ")");
		}
	}
}
