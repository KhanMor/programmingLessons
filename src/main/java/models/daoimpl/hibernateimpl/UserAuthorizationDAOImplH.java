package models.daoimpl.hibernateimpl;

import common.exceptions.DAOException;
import models.dao.UserAuthorizationDAO;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Mordr on 02.03.2017.
 * Авторизация пользователя через JPA
 */
@Repository("userAuthorizationDAO")
public class UserAuthorizationDAOImplH implements UserAuthorizationDAO {
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAOImplH.class);
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("email"), email),
                            criteriaBuilder.equal(root.get("password"), password)
                    )
            );
            List<User> users = entityManager.createQuery(criteriaQuery).getResultList();
            if (users.isEmpty()) {
                return null;
            }
            return users.get(0);
        }catch (Exception e) {
            logger.error(e);
            throw new DAOException(e);
        }finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Role findRoleByName(String roleName) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
            Root<Role> root = criteriaQuery.from(Role.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("role"), roleName));
            List<Role> roles = entityManager.createQuery(criteriaQuery).getResultList();
            if (roles.isEmpty()) {
                return null;
            }
            return roles.get(0);
        }catch (Exception e) {
            logger.error(e);
            throw new DAOException(e);
        }finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void deleteUserAllRoles(Integer user_id) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaDelete<UserRole> criteriaDelete = criteriaBuilder.createCriteriaDelete(UserRole.class);
            Root<UserRole> root = criteriaDelete.from(UserRole.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("user").get("id"), user_id));
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.createQuery(criteriaDelete).executeUpdate();
            entityTransaction.commit();
        }catch (Exception e) {
            logger.error(e);
            throw new DAOException(e);
        }finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<UserRole> getUserAllRoles(User user) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserRole> criteriaQuery = criteriaBuilder.createQuery(UserRole.class);
            Root<UserRole> root = criteriaQuery.from(UserRole.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("user"), user));
            return entityManager.createQuery(criteriaQuery).getResultList();
        }catch (Exception e) {
            logger.error(e);
            throw new DAOException(e);
        }finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Role findUserRole(User user, Role role) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
            Root<Role> root = criteriaQuery.from(Role.class);
            Join<Role, UserRole> join = root.join("userRoles", JoinType.INNER);
            criteriaQuery.select(root);
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(join.get("user"), user),
                            criteriaBuilder.equal(join.get("role"), role)
                    )
            );
            List<Role> roles = entityManager.createQuery(criteriaQuery).getResultList();
            if (roles.isEmpty()) {
                return null;
            }
            return roles.get(0);
        }catch (Exception e) {
            logger.error(e);
            throw new DAOException(e);
        }finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public User findUserByEmail(String email) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("email"), email));
            List<User> users = entityManager.createQuery(criteriaQuery).getResultList();
            if (users.isEmpty()) {
                return null;
            }
            return users.get(0);
        }catch (Exception e) {
            logger.error(e);
            throw new DAOException(e);
        }finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }
}
