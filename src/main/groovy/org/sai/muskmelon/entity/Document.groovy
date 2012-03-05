/* This code contains copyright information which is the proprietary property
 * of SITA Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 * SITA Advanced Travel Solutions.
 *
 * Copyright SITA Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package org.sai.muskmelon.entity;


import org.sai.muskmelon.domain.Visitor
import javax.persistence.*
import org.sai.muskmelon.domain.Visitable

@Entity
public class Document implements Visitable,Serializable {
    private static final long serialVersionUID = -5091123127887466941L

    @Id
    @GeneratedValue
    private Long id

    @Column
    private String identifier

    @Column
    private String author

    @Column
    private String title

    @Column
    private String description

    @Column
    private String status

    @Column
    private String lastModifiedBy

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date created

    @OneToMany
    @JoinColumn(name = "documentId")
    private List<Commit> commits = new ArrayList<Commit>()

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this)
    }
}
