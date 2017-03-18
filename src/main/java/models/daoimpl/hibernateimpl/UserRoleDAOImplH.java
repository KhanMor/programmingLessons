package models.daoimpl.hibernateimpl;

import common.exceptions.DAOException;
import models.dao.SuperDAO;
import models.entity.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * CRUD с ролями пользователя через JPA
 */
@Repository("userRoleDAO")
public class UserRoleDAOImplH implements SuperDAO<UserRole> {
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAOImplH.class);
    private static final String DELETE_ALL_SQL;
    private static final String REFRESH_INCREMENT_SQL;

    static {
        REFRESH_INCREMENT_SQL = "ALTER TABLE userrole AUTO_INCREMENT = 1;";
        DELETE_ALL_SQL = "delete from userrole";
    }

    private EntityManagerFactory entityManagerFactory;
    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<UserRole> list() throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserRole> criteriaQuery = criteriaBuilder.createQuery(UserRole.class);
            Root<UserRole> root = criteriaQuery.from(UserRole.class);
            criteriaQuery.select(root);
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
    public UserRole get(Integer id) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            return entityManager.find(UserRole.class, id);
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
    public void insert(UserRole userRole) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(userRole);
            entityManager.flush();
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
    public void update(UserRole userRole) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.merge(userRole);
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
    public void delete(Integer id) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            UserRole userRole = entityManager.find(UserRole.class, id);
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(userRole);
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
    public void deleteAll() throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            Query deleteAllQuery = entityManager.createQuery(DELETE_ALL_SQL);
            Query refreshIncrementQuery = entityManager.createQuery(REFRESH_INCREMENT_SQL);

            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            deleteAllQuery.executeUpdate();
            refreshIncrementQuery.executeUpdate();
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
}
