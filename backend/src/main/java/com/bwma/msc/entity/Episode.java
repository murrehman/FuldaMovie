package com.bwma.msc.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Episode {
    @Basic
    @Column(name = "air_date", columnDefinition = "DATE")
    private Date airDate;
    @Basic
    @Column(name = "episode_number")
    private Integer episodeNumber;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "overview")
    private String overview;
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "production_code")
    private String productionCode;
    @Basic
    @Column(name = "season_number")
    private Integer seasonNumber;
    @Basic
    @Column(name = "vote_average")
    private BigDecimal voteAverage;
    @Basic
    @Column(name = "vote_count")
    private Integer voteCount;
    @Basic
    @Column(name = "imdb_id")
    private String imdbId;
    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    @Override
    public String toString() {
        return "Episode{" +
                "airDate=" + airDate +
                ", episodeNumber=" + episodeNumber +
                ", name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", id=" + id +
                ", productionCode='" + productionCode + '\'' +
                ", seasonNumber=" + seasonNumber +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                ", imdbId='" + imdbId + '\'' +
                ", season=" + season +
                '}';
    }
}
