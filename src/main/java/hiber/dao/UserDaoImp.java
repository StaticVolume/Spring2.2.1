package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> getUserByeCar(Car car) { // создал фунекцию, возвращающую List<User>, а не конкретный обьект User
                                             // по причине того, что на данный момент придерживаюсь идеи, что в базе нет
                                             // поля, или пруппы полей, являющихся уникальными,
                                             // а значит и результат запроса не может быть в одном экземпляре
                                             // если нужно будет переделать, это не проблема
      return  sessionFactory.getCurrentSession().createQuery("from User where car.model = ?1 and car.series = ?2")
              .setParameter(1, car.getModel())
              .setParameter(2, car.getSeries())
              .getResultList();
   }

}
