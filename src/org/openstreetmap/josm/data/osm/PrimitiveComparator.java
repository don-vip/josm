// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.data.osm;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.openstreetmap.josm.tools.AlphanumComparator;

/**
 * Comparators for comparing primitives.
 * @since xxx
 */
public final class PrimitiveComparator {

    /**
     * Returns a comparator comparing primitives by their name using {@link DefaultNameFormatter}.
     *
     * {@linkplain DefaultNameFormatter#format(IPrimitive) Formatted names} are cached.
     *
     * @return a comparator comparing primitives by their name using {@link DefaultNameFormatter}
     */
    public static Comparator<IPrimitive> comparingNames() {
        final Comparator<String> digitsLast = comparing(str -> !str.isEmpty() && Character.isDigit(str.charAt(0)) ? 1 : 0);
        return comparing(memoize(DefaultNameFormatter.getInstance()::format),
                digitsLast.thenComparing(AlphanumComparator.getInstance()));
    }

    /**
     * Returns a comparator comparing primitives by their {@linkplain IPrimitive#getUniqueId unique id}.
     *
     * @return a comparator comparing primitives by their {@linkplain IPrimitive#getUniqueId unique id}.
     */
    public static Comparator<IPrimitive> comparingUniqueId() {
        return comparing(IPrimitive::getUniqueId);
    }

    /**
     * Returns a comparator ordering the primitives by type in the order NODE, WAY, RELATION
     *
     * @return a comparator ordering the primitives by type in the order NODE, WAY, RELATION
     */
    public static Comparator<IPrimitive> orderingNodesWaysRelations() {
        return comparingInt(osm -> osm.getType().ordinal());
    }

    /**
     * Returns a comparator ordering the primitives by type in the order WAY, RELATION, NODE
     *
     * @return a comparator ordering the primitives by type in the order WAY, RELATION, NODE
     */
    public static Comparator<IPrimitive> orderingWaysRelationsNodes() {
        return comparingInt(osm -> {
            switch (osm.getType()) {
                case WAY:
                    return 1;
                case RELATION:
                    return 2;
                case NODE:
                    return 3;
                default:
                    throw new IllegalStateException();
            }
        });
    }

    /**
     * Returns a comparator ordering the primitives by type in the order RELATION, WAY, NODE
     *
     * @return a comparator ordering the primitives by type in the order RELATION, WAY, NODE
     */
    public static Comparator<IPrimitive> orderingRelationsWaysNodes() {
        return comparingInt(osm -> {
            switch (osm.getType()) {
                case RELATION:
                    return 1;
                case WAY:
                    return 2;
                case NODE:
                    return 3;
                default:
                    throw new IllegalStateException();
            }
        });
    }

    private static <T, R> Function<T, R> memoize(Function<T, R> base) {
        final Map<T, R> cache = new HashMap<>();
        return t -> cache.computeIfAbsent(t, base);
    }

    private PrimitiveComparator() {
    }
}
