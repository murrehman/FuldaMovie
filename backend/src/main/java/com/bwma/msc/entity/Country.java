package com.bwma.msc.entity;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Country {
    @Id
    @Column(name = "iso_3166_1", nullable = false)
    private String iso_3166_1;
    @Basic
    @Column(name = "name")
    private String english_name;

    @Override
    public String toString() {
        return "Country{" +
                "iso31661='" + iso_3166_1 + '\'' +
                ", name='" + english_name + '\'' +
                '}';
    }
}
