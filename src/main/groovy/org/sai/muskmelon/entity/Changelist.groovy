/* This code contains copyright information which is the proprietary property
 * of SITA Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 * SITA Advanced Travel Solutions.
 *
 * Copyright SITA Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package org.sai.muskmelon.entity;


import org.sai.muskmelon.domain.Visitable
import org.sai.muskmelon.domain.Visitor
import javax.persistence.*

@Entity
public class Changelist implements Visitable, Serializable {

    private static final long serialVersionUID = -9205200486894944662L

    @Id
    @GeneratedValue
    private Long id

    @Column
    @Lob
    private String resourcePath

    @Column
    @Enumerated(EnumType.STRING)
    private ChangelistType changelistType

    @Column
    @Lob
    private String resourceUrl

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this)
    }

}
