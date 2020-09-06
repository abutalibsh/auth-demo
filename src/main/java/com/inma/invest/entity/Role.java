package com.inma.invest.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GenericGenerator(name = "assigned-sequence", strategy = "com.inma.invest.utils.StringSequenceIdentifier", parameters = {
			@org.hibernate.annotations.Parameter(name = "sequence_name", value = "role_seq"),
			@org.hibernate.annotations.Parameter(name = "sequence_prefix", value = "RL"), })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assigned-sequence")
    private String id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;

    public Role() {

    }

    public Role(RoleName name) {
        this.name = name;
    }

    
}
