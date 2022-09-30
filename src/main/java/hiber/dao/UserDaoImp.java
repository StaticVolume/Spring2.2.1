package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
      return query.getResultList();
   }

   @Override
   public User getUserByeCar(Car car) {
      //решено модификацией таблиц баз данных через добавление в полю model модификатора UNIQUE
      TypedQuery<User> query =  sessionFactory
              .getCurrentSession()
              .createQuery("From User where car.model = ?1 and car.series = ?2", User.class)
              .setParameter(1, car.getModel())
              .setParameter(2, car.getSeries());
      return query.getSingleResult();
   }

}

