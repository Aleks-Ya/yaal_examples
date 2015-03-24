import entity.CityEntity;
import entity.RegionEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class Select {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void main() {
        saveEntities();
        {
            System.out.println("\nSelect (1 table, one field):");
            Query query = em.createQuery("SELECT name FROM CityEntity");
            List result = query.getResultList();
            pringList(result);
        }
        {
            System.out.println("\nSelect (1 table, whole object):");
            Query query = em.createQuery("FROM CityEntity");
            List result = query.getResultList();
            pringList(result);
        }
        {
            System.out.println("\nSelect (1 table, whole object, where):");
            Query query = em.createQuery("FROM CityEntity c WHERE c.name = :name");
            query.setParameter("name", "Вологда");
            List result = query.getResultList();
            pringList(result);
        }
        {
            System.out.println("\nJoin by path:");
            Query query = em.createQuery("FROM CityEntity c WHERE c.region.name = :name");
            query.setParameter("name", "Вологодская область");
            List result = query.getResultList();
            pringList(result);
        }
        {
            System.out.println("\nJoin by join (указание возвращаемого класса):");
            Query query = em.createQuery("SELECT r FROM CityEntity c JOIN c.region r WHERE r.name = :name");
            query.setParameter("name", "Вологодская область");
            List result = query.getResultList();
            pringList(result);
        }
        em.close();
    }

    private static void pringList(List result) {
        for (Object obj : result) {
            System.out.printf("%s - %s %n", obj, obj.getClass().getSimpleName());
        }
    }

    private void saveEntities() {
        RegionEntity region = new RegionEntity("Вологодская область");
        final long vologdaPopulation = 300000L;
        final String vologdaName = "Вологда";
        CityEntity city1 = new CityEntity(vologdaName, vologdaPopulation, region);
        long spbPopulation = 4000000L;
        CityEntity city2 = new CityEntity("Санкт-Петербург", spbPopulation, region);
        CityEntity city3 = new CityEntity("Волгоград", 1000000L, region);


        RegionEntity region2 = new RegionEntity("Московская область");
        long moscowPopulation = 12000000L;
        CityEntity city4 = new CityEntity("Москва", moscowPopulation, region2);

        em.persist(region);
        em.persist(region2);
        em.persist(city1);
        em.persist(city2);
        em.persist(city3);
        em.persist(city4);
        em.flush();
    }
}