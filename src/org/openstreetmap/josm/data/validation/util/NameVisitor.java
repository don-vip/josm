// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.data.validation.util;

import static org.openstreetmap.josm.tools.I18n.trn;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.openstreetmap.josm.data.osm.DefaultNameFormatter;
import org.openstreetmap.josm.data.osm.INode;
import org.openstreetmap.josm.data.osm.IRelation;
import org.openstreetmap.josm.data.osm.IWay;
import org.openstreetmap.josm.data.osm.visitor.PrimitiveVisitor;
import org.openstreetmap.josm.tools.ImageProvider;

/**
 * Able to create a name and an icon for each data element.
 *
 * @author imi
 */
public class NameVisitor implements PrimitiveVisitor {

    /**
     * The name of the item class
     */
    public String className;

    /**
     * The plural name of the item class
     */
    public String classNamePlural;

    /**
     * The name of this item.
     */
    public String name = "";

    /**
     * The icon of this item.
     */
    public Icon icon;

    /**
     * If the node has a name-key or id-key, this is displayed. If not, (lat,lon) is displayed.
     */
    @Override
    public void visit(INode n) {
        name = n.getDisplayName(DefaultNameFormatter.getInstance());
        icon = ImageProvider.get("data", "node");
        className = "node";
        classNamePlural = trn("node", "nodes", 2);
    }

    /**
     * If the way has a name-key or id-key, this is displayed. If not, (x nodes)
     * is displayed with x being the number of nodes in the way.
     */
    @Override
    public void visit(IWay<?, ?> w) {
        name = w.getDisplayName(DefaultNameFormatter.getInstance());
        icon = ImageProvider.get("data", "way");
        className = "way";
        classNamePlural = trn("way", "ways", 2);
    }

    @Override
    public void visit(IRelation<?> e) {
        name = e.getDisplayName(DefaultNameFormatter.getInstance());
        icon = ImageProvider.get("data", "relation");
        className = "relation";
        classNamePlural = trn("relation", "relations", 2);
    }

    /**
     * Returns an horizontal {@code JLabel} with icon and name.
     * @return horizontal {@code JLabel} with icon and name
     */
    public JLabel toLabel() {
        return new JLabel(name, icon, JLabel.HORIZONTAL);
    }
}
