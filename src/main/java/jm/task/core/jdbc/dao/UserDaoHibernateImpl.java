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
    private final SessionFactory sF = Util.getConnection();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS test.users" +
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
        }finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.createNativeQuery("DROP TABLE IF EXISTS test.users").executeUpdate();
            tx.commit();
            System.out.println("Таблица удалена");
        }catch (HibernateException e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.save(new User(name,lastName,age));
            tx.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        }catch(HibernateException e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(session.get(User.class, id));
            tx.commit();
            System.out.println("User удален");
        }catch (HibernateException e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = sF.openSession();
        CriteriaQuery<User> cq = session.getCriteriaBuilder().createQuery(User.class);
        cq.from(User.class);
        Transaction tx = session.beginTransaction();
        List<User> uL = session.createQuery(cq).getResultList();
        try {
            tx.commit();
            return uL;
        }catch (HibernateException e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
        }
        return uL;

    }

    @Override
    public void cleanUsersTable() {
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createNativeQuery("TRUNCATE TABLE test.users").executeUpdate();
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
