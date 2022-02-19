package com.bwma.msc.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Season {
    @Basic
    @Column(name = "air_date", columnDefinition = "DATE")
    private Date airDate;
    @Basic
    @Column(name = "episode_count")
    private Integer episodeCount;
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "overview")
    private String overview;
    @Basic
    @Column(name = "poster_path")
    private String posterPath;
    @Basic
    @Column(name = "season_number")
    private Integer seasonNumber;
    @ManyToOne
    @JoinColumn(name = "tv_id")
    private Content tv;

    @Override
    public String toString() {
        return "Season{" +
                "airDate=" + airDate +
                ", episodeCount=" + episodeCount +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", seasonNumber=" + seasonNumber +
                ", tv=" + tv +
                '}';
    }
}
