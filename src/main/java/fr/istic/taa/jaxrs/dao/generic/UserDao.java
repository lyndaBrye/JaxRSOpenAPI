package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.User;

public class UserDao extends  AbstractJpaDao<Long, User> {
    public UserDao() {
        super();
        setClazz(User.class);
    }

}
