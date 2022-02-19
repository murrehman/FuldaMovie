package com.bwma.msc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;



import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "watch_provider")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class WatchProvider {
    @ManyToOne
    @JoinColumn(name = "content_id")
    @JsonIgnore
    private Content content;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @Id
    @Column(name = "link", nullable = false)
    private String link;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "watch_provider_provider_rent",
            joinColumns = @JoinColumn(name = "link", referencedColumnName = "link"),
            inverseJoinColumns = @JoinColumn(name = "provider_id",
                    referencedColumnName = "provider_id"))
    private List<Provider> rent;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "watch_provider_provider_buy",
            joinColumns = @JoinColumn(name = "link", referencedColumnName = "link"),
            inverseJoinColumns = @JoinColumn(name = "provider_id",
                    referencedColumnName = "provider_id"))
    private List<Provider> buy;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "watch_provider_provider_flatrent",
            joinColumns = @JoinColumn(name = "link", referencedColumnName = "link"),
            inverseJoinColumns = @JoinColumn(name = "provider_id",
                    referencedColumnName = "provider_id"))
    private List<Provider> flatrate;

    @Override
    public String toString() {
        return "WatchProvider{" +
                "content=" + content +
                ", country=" + country +
                ", link='" + link + '\'' +
                ", rent=" + rent +
                ", buy=" + buy +
                ", flatrate=" + flatrate +
                '}';
    }
}
