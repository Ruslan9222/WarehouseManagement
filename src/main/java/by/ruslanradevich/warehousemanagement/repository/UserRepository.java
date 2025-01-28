package by.ruslanradevich.warehousemanagement.repository;

import by.ruslanradevich.warehousemanagement.entity.User;
import by.ruslanradevich.warehousemanagement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class UserRepository implements IBaseRepository<User, Long> {

    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());

    @Override
    public void save(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.severe("Error saving user: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.severe("Error updating user: " + e.getMessage());
            throw e;
        }
    }

    public void updateUser(String username, String newPassword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            User user = query.uniqueResult();

            if (user != null) {
                user.setPassword(newPassword);
                session.update(user);
                session.getTransaction().commit();
            } else {
                logger.warning("User not found with username: " + username);
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            logger.severe("Error updating user password: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.severe("Error deleting user: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public User findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            logger.severe("Error finding user by id: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            logger.severe("Error finding all users: " + e.getMessage());
            throw e;
        }
    }

    public Optional<User> findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            logger.severe("Error finding user by username: " + e.getMessage());
            throw e;
        }
    }
}
