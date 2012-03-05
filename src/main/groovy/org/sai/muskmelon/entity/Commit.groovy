/* This code contains copyright information which is the proprietary property
 * of SITA Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 * SITA Advanced Travel Solutions.
 *
 * Copyright SITA Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package org.sai.muskmelon.entity;


import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.sai.muskmelon.domain.Visitable
import org.sai.muskmelon.domain.Visitor
import javax.persistence.*

@Entity
public class Commit implements Visitable, Serializable {

    private static final long serialVersionUID = 8661122431593788689L

    @Id
    @GeneratedValue
    private Long id

    @Column
    private String author

    @Column
    @Lob
    private String message

    @Column
    private Long revision

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date LastModified

    @Transient
    private String documentIdentifier

    @OneToMany
    @JoinColumn(name = "commitId")
    @Cascade(value = CascadeType.ALL)
    private List<Changelist> changeList = new ArrayList<Changelist>()

    @Override
    void accept(final Visitor visitor) {
        visitor.visit(this)
    }
}
