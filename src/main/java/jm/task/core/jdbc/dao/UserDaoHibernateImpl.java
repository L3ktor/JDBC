package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sF = Util.getSF();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try(Session session1 = sF.openSession()) {
            tx = session1.beginTransaction();
            session1.createNativeQuery("CREATE TABLE IF NOT EXISTS users" +
                    " (id mediumint not null auto_increment, name VARCHAR(45), " +
                    "lastname VARCHAR(45), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))").executeUpdate();
            tx.commit();
            System.out.println("Таблица создана");
        }catch (HibernateException e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try(Session session2 = sF.openSession()){
            tx = session2.beginTransaction();
            session2.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            tx.commit();
            System.out.println("Таблица удалена");
        }catch (HibernateException e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try(Session session3 = sF.openSession()){
            tx = session3.beginTransaction();
            session3.save(new User(name,lastName,age));
            tx.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        }catch(HibernateException e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try(Session session4 = sF.openSession()){
            tx = session4.beginTransaction();
            session4.delete(session4.get(User.class, id));
            tx.commit();
            System.out.println("User удален");
        }catch (HibernateException e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> uL = null;
        Transaction tx = null;
        try(Session session5 = sF.openSession()) {
            CriteriaQuery<User> cq = session5.getCriteriaBuilder().createQuery(User.class);
            cq.from(User.class);
            tx = session5.beginTransaction();
            tx.commit();
            uL = session5.createQuery(cq).getResultList();
            return uL;
        }catch (HibernateException e){
            e.printStackTrace();
            tx.rollback();
        }return uL;

    }

    @Override
    public void cleanUsersTable() {
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            tx.commit();
            System.out.println("Таблица очищена");
        }catch (HibernateException e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }

    }
}
