package pl.edu.pjwstk.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="parameter")
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String key;

    @OneToMany(mappedBy = "parameter")
    private Set<AuctionParameter> auctionParameterSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<AuctionParameter> getAuctionParameterSet() {
        return auctionParameterSet;
    }

    public void setAuctionParameterSet(Set<AuctionParameter> auctionParameterSet) {
        this.auctionParameterSet = auctionParameterSet;
    }
}
