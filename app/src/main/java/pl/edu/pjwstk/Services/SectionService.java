package pl.edu.pjwstk.Services;

import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.BadRequestException;
import pl.edu.pjwstk.Request.CategoryRequest;
import pl.edu.pjwstk.Entity.Auction;
import pl.edu.pjwstk.Entity.Category;
import pl.edu.pjwstk.Entity.Section;
import pl.edu.pjwstk.Entity.UserEntity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Transactional
@Service
public class SectionService {

    private final EntityManager em;

    public SectionService(EntityManager em) {
        this.em = em;
    }

    public void createSection(String sectionName) {
            var section = new Section();
            section.setName(sectionName);
            em.persist(section);
    }

    public void updateSection(String sectionName,String newName) {
        try{
            var section = findSectionByName(sectionName);
            section.setName(newName);
            em.merge(section);
        }
        catch (NoResultException exception) {
            throw new BadRequestException();
        }
    }

    public void createCategory(CategoryRequest categoryRequest){

        var category  = new Category();
        category.setName(categoryRequest.getCategoryName());
        category.setSection(findSectionByName(categoryRequest.getSectionName()));
        em.persist(category);
    }

    public void updateCategory(String categoryName,CategoryRequest categoryRequest) {
        try{
            var category = findCategoryByName(categoryName);
            category.setName(categoryRequest.getCategoryName());
            if(categoryRequest.getSectionName() != null){
                category.setSection(findSectionByName(categoryRequest.getSectionName()));
            }
            em.merge(category);
        }catch (NoResultException exception) {
            throw new BadRequestException();
        }
    }

    public Category findCategoryByName(String categoryName) {
        return em.createQuery("SELECT category FROM Category category WHERE category.name =: categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getSingleResult();
    }

    public Section findSectionByName(String sectionName) {
        return em.createQuery("SELECT section FROM Section section WHERE section.name =: sectionName", Section.class)
                .setParameter("sectionName", sectionName)
                .getSingleResult();
    }

    public List<Auction> getAuctionsByCreator(UserEntity userEntity){
        return em.createQuery("select auction FROM Auction auction WHERE auction.userEntity =:userEntity",Auction.class)
                .setParameter("userEntity",userEntity)
                .getResultList();
    }

}
