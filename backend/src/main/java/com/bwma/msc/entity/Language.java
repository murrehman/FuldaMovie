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
public class Language {
    @Id
    @Column(name = "iso_639_1", nullable = false)
    private String iso_639_1;
    @Basic
    @Column(name = "english_name")
    private String english_name;
    @Basic
    @Column(name = "name")
    private String name;

    public Language(String iso_639_1){
        this.iso_639_1 = iso_639_1;
    }

    @Override
    public String toString() {
        return "Language{" +
                "iso_639_1='" + iso_639_1 + '\'' +
                ", english_name='" + english_name + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
