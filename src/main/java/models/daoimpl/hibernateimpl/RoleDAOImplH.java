package models.daoimpl.hibernateimpl;

import common.exceptions.DAOException;
import models.dao.SuperDAO;
import models.pojo.Role;
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
 * CRUD с ролями через JPA
 */
@Repository("roleDAO")
public class RoleDAOImplH implements SuperDAO<Role> {
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAOImplH.class);
    private static final String DELETE_ALL_SQL;
    private static final String REFRESH_INCREMENT_SQL;

    static {
        REFRESH_INCREMENT_SQL = "ALTER TABLE Role AUTO_INCREMENT = 1;";
        DELETE_ALL_SQL = "delete from Role";
    }

    private EntityManagerFactory entityManagerFactory;
    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Role> list() throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
            Root<Role> root = criteriaQuery.from(Role.class);
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
    public Role get(Integer id) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            return entityManager.find(Role.class, id);
        }catch (Exception e) {
            throw new DAOException(e);
        }finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void insert(Role Role) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(Role);
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
    public void update(Role Role) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.merge(Role);
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
            Role Role = entityManager.find(Role.class, id);
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(Role);
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