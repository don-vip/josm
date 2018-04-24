// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.data.osm;

import java.util.Arrays;

/**
 * IRelationMember captures the common functions of {@link RelationMember} and {@link RelationMemberData}.
 * @param <O> the base type of OSM primitives
 * @param <N> type representing OSM nodes
 * @param <W> type representing OSM ways
 * @param <R> type representing OSM relations
 * @since xxx
 */
public interface IRelationMember<O extends IPrimitive, N extends INode, W extends IWay<?, N>, R extends IRelation<?>> extends PrimitiveId {

    /**
     * Returns the role of this relation member.
     * @return Role name or "". Never returns null
     * @since 1930
     */
    String getRole();

    /**
     * Determines if this relation member has a role.
     * @return True if role is set
     * @since 1930
     */
    default boolean hasRole() {
        return !"".equals(getRole());
    }

    /**
     * Determines if this relation member's role is in the given list.
     * @param roles The roles to look after
     * @return True if role is in the given list
     * @since 6305
     */
    default boolean hasRole(String... roles) {
        return Arrays.asList(roles).contains(getRole());
    }

    /**
     * Determines if this relation member is a node.
     * @return True if member is node
     * @since 1937
     */
    boolean isNode();

    /**
     * Determines if this relation member is a way.
     * @return True if member is way
     * @since 1937
     */
    boolean isWay();

    /**
     * Determines if this relation member is a relation.
     * @return True if member is relation
     * @since 1937
     */
    boolean isRelation();

    /**
     * Returns type of member for icon display.
     * @return type of member for icon display
     * @since 3844
     */
    default OsmPrimitiveType getDisplayType() {
        return getMember().getDisplayType();
    }

    /**
     * Returns the relation member.
     * @return Member. Returned value is never null.
     * @since 1937
     */
    O getMember();

    /**
     * Returns the relation member as a node.
     * @return Member as node
     * @since 1937
     */
    @SuppressWarnings("unchecked")
    default N getNode() {
        return (N) getMember();
    }

    /**
     * Returns the relation member as a way.
     * @return Member as way
     * @since 1937
     */
    @SuppressWarnings("unchecked")
    default W getWay() {
        return (W) getMember();
    }

    /**
     * Returns the relation member as a relation.
     * @return Member as relation
     * @since 1937
     */
    @SuppressWarnings("unchecked")
    default R getRelation() {
        return (R) getMember();
    }

    /**
     * Replies true, if this relation member refers to the primitive
     *
     * @param primitive  the primitive to check
     * @return true, if this relation member refers to the primitive
     */
    default boolean refersTo(O primitive) {
        return getMember() == primitive;
    }

    /**
     * Returns the parent data set of this relation member.
     * @return OsmData this relation member is part of.
     */
    OsmData<?, ?, ?, ?> getDataSet();
}
