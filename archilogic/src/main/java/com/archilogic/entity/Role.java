/***********************************************************
 * Copyright (c) 2025. All rights reserved to ArchiLogic.
 * Developer: Nikhil Gadhavajula
 **********************************************************/

package com.archilogic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
