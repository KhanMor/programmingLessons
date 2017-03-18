package models.daoimpl.hibernateimpl;

import common.exceptions.DAOException;
import models.dao.SuperDAO;
import models.entity.Course;
import models.entity.Lesson;
import models.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
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
 * CRUD с уроками через JPA
 */
@Repository("lessonDAO")
public class LessonDAOImplH implements SuperDAO<Lesson> {
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAOImplH.class);
    private static final String DELETE_ALL_SQL;
    private static final String REFRESH_INCREMENT_SQL;

    static {
        REFRESH_INCREMENT_SQL = "ALTER TABLE lesson AUTO_INCREMENT = 1;";
        DELETE_ALL_SQL = "delete from lesson";
    }

    private EntityManagerFactory entityManagerFactory;
    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Lesson> list() throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Lesson> criteriaQuery = criteriaBuilder.createQuery(Lesson.class);
            Root<Lesson> root = criteriaQuery.from(Lesson.class);
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
    public Lesson get(Integer id) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            Lesson lesson = entityManager.find(Lesson.class, id);
            Course course = lesson.getCourse();
            User author = course.getAuthor();
            Hibernate.initialize(course);
            Hibernate.initialize(author);
            return lesson;
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
    public void insert(Lesson lesson) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(lesson);
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
    public void update(Lesson lesson) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.merge(lesson);
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
            Lesson lesson = entityManager.find(Lesson.class, id);
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(lesson);
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
