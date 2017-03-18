package models.daoimpl.hibernateimpl;

import common.exceptions.DAOException;
import models.dao.CourseLessonsDAO;
import models.entity.Lesson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Mordr on 10.03.2017.
 * Получение спика уроков конкретного курса
 */
@Repository("courseLessonsDAO")
public class CourseLessonsDAOImplH implements CourseLessonsDAO {
    private static final Logger logger = Logger.getLogger(CourseLessonsDAOImplH.class);

    private EntityManagerFactory entityManagerFactory;
    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Lesson> courseLessonsList(Integer course_id) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Lesson> criteriaQuery = criteriaBuilder.createQuery(Lesson.class);
            Root<Lesson> root = criteriaQuery.from(Lesson.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("course").get("id"), course_id)));
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
/*
    private String formatLesson(Lesson lesson) {
        return lesson.getOrderNum() + ". " + lesson.getTheme() + " - " + lesson.getDuration();
    }

    @Override
    public List<MiniPOJO> courseLessonsListMini(Integer course_id) throws DAOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Lesson> criteriaQuery = criteriaBuilder.createQuery(Lesson.class);
            Root<Lesson> root = criteriaQuery.from(Lesson.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("course").get("id"), course_id)));
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("orderNum")));
            List<Lesson> lessons = entityManager.createQuery(criteriaQuery).getResultList();
            List<MiniPOJO> lessonsMini = new ArrayList<>();
            for(Lesson lesson:lessons) {
                MiniPOJO lessonMini = new MiniPOJO();
                lessonMini.setId(lesson.getId());
                lessonMini.setName(formatLesson(lesson));
                lessonsMini.add(lessonMini);
            }
            return lessonsMini;
        }catch (Exception e) {
            logger.error(e);
            throw new DAOException(e);
        }finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }*/
}
