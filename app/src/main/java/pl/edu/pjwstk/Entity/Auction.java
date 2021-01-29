package pl.edu.pjwstk.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="auction")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String Title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Long version;

    @ManyToOne
    @JoinColumn(name="creator_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    private Set<Photo> photosSet;

    @OneToMany(mappedBy ="auction",cascade = CascadeType.ALL)
    private Set<AuctionParameter> auctionParameters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Photo> getPhotosSet() {
        return photosSet;
    }

    public void setPhotosSet(Set<Photo> photosSet) {
        this.photosSet = photosSet;
    }

    public Set<AuctionParameter> getAuctionParameters() {
        return auctionParameters;
    }

    public void setAuctionParameters(Set<AuctionParameter> auctionParameters) {
        this.auctionParameters = auctionParameters;
    }
    public Long getVersion(){return version;}

    public void setVersion(Long version){this.version=version;}
}
