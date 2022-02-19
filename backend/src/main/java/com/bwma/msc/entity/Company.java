package com.bwma.msc.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Company {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "headquarters")
    private String headquarters;
    @Basic
    @Column(name = "homepage")
    private String homepage;
    @Basic
    @Column(name = "logo_path")
    private String logo_path;
    @Basic
    @Column(name = "name")
    private String name;


}
