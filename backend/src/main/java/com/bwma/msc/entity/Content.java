package com.bwma.msc.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Content {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "imdb_id")
    private String imdbId;
    @Basic
    @Column(name = "popularity")
    private BigDecimal popularity;
    @Basic
    @Column(name = "vote_count")
    private Integer voteCount;
    @Basic
    @Column(name = "release_date", columnDefinition = "DATE")
    private Date releaseDate;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "vote_Average")
    private BigDecimal voteAverage;
    @Basic
    @Column(name = "poster_path")
    private String posterPath;
    @Basic
    @Column(name = "overview")
    private String overview;
    @Basic
    @Column(name = "runtime")
    private Integer runtime;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "datatype",nullable = false)
    private ContentType contentType;
    @Basic
    @Column(name = "adult")
    private Boolean adult;
    @Basic
    @Column(name = "budget")
    private Integer budget;
    @Basic
    @Column(name = "revenue")
    private Long revenue;
    @Basic
    @Column(name = "in_production")
    private Boolean inProduction;
    @Basic
    @Column(name = "last_air_date", columnDefinition = "DATE")
    private Date lastAirDate;
    @Basic
    @Column(name = "number_of_episodes")
    private Integer numberOfEpisodes;
    @Basic
    @Column(name = "number_of_seasons")
    private Integer numberOfSeasons;
    @ManyToOne
    @JoinColumn(name = "original_language")
    private Language originalLanguage;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "content_genre",
            joinColumns = @JoinColumn(name = "content_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id",
                    referencedColumnName = "id"))
    private List<Genre> genre;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "content_company",
            joinColumns = @JoinColumn(name = "content_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "company_id",
                    referencedColumnName = "id"))
    private List<Company> companies;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "content_country",
            joinColumns = @JoinColumn(name = "content_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "country_id",
                    referencedColumnName = "iso_3166_1"))
    private List<Country> countries;
}
