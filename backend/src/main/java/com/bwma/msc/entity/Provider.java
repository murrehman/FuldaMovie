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
public class Provider {
    @Basic
    @Column(name = "display_priority")
    private Integer display_priority;
    @Basic
    @Column(name = "logo_path")
    private String logo_path;
    @Id
    @Column(name = "provider_id", nullable = false)
    private int provider_id;
    @Basic
    @Column(name = "provider_name")
    private String provider_name;

    @Override
    public String toString() {
        return "Provider{" +
                "display_priority=" + display_priority +
                ", logo_path='" + logo_path + '\'' +
                ", provider_id=" + provider_id +
                ", provider_name='" + provider_name + '\'' +
                '}';
    }
}
