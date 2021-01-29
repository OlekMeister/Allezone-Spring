package pl.edu.pjwstk.Services;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.BadRequestException;
import pl.edu.pjwstk.Request.ParameterRequest;
import pl.edu.pjwstk.Request.PhotoRequest;
import pl.edu.pjwstk.Entity.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AuctionViewService {

    private final EntityManager entityManager;

    public AuctionViewService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ViewAuction getAuction(Long auctionId){

        ViewAuction viewAuction = new ViewAuction();

        try{
            var auction = findAuctionById(auctionId);

            viewAuction.setAuctionId(auctionId);
            viewAuction.setPrice(auction.getPrice());
            viewAuction.setAuctionTitle(auction.getTitle());
            viewAuction.setAuctionDescription(auction.getDescription());
            viewAuction.setCategoryName(auction.getCategory().getName());
            viewAuction.setUserName(auction.getUserEntity().getUsername());


            Set<PhotoRequest> photoRequests = new HashSet<>();
            for (Photo photo : auction.getPhotosSet()){
                var auctionPhoto = new PhotoRequest(photo.getName(), photo.getPosition());
                photoRequests.add(auctionPhoto);
            }
            viewAuction.setPhotoList(photoRequests);

            Set<ParameterRequest> auctionParameters = new HashSet<>();
            for (AuctionParameter auctionParameter : auction.getAuctionParameters()){
                var parameter = new ParameterRequest(auctionParameter.getParameter().getKey(),auctionParameter.getValue());
                auctionParameters.add(parameter);
            }
            viewAuction.setParameterList(auctionParameters);

            viewAuction.setVersion(auction.getVersion());
        }catch (NoResultException exception) {
            throw new BadRequestException();
        }

        return viewAuction;
    }


    public List<AuctionView> getAllAuctions(){
        return getListAuctionViews();
    }


    public List<AuctionView> getListAuctionViews(){
        return entityManager.createQuery("select view FROM AuctionView view",AuctionView.class)
                .getResultList();
    }

    public Auction findAuctionById(Long auction_id) {
        return entityManager.createQuery("SELECT auction FROM Auction auction WHERE auction.id =: auction_id", Auction.class)
                .setParameter("auction_id", auction_id)
                .getSingleResult();
    }



}
