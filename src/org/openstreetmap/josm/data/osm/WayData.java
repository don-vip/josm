// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.data.osm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openstreetmap.josm.data.coor.ILatLon;
import org.openstreetmap.josm.data.osm.visitor.PrimitiveVisitor;

/**
 * The data (tags and node ids) that is stored for a way in the database.
 * @since 2284
 */
public class WayData extends PrimitiveData implements IWay<Long, NodeData> {

    private static final long serialVersionUID = 106944939313286415L;
    private List<Long> nodes = new ArrayList<>();

    /**
     * Constructs a new {@code NodeData}.
     */
    public WayData() {
        // contents can be set later with setters
    }

    /**
     * Constructs a new {@code WayData} with given id.
     * @param id id
     * @since 12017
     */
    public WayData(long id) {
        super(id);
    }

    /**
     * Constructs a new {@code WayData}.
     * @param data way data to copy
     */
    public WayData(WayData data) {
        super(data);
        nodes.addAll(data.getNodes());
    }

    /**
     * Gets a list of nodes the way consists of
     * @return The ids of the nodes
     */
    @Override
    public List<Long> getNodes() {
        return nodes;
    }

    @Override
    public List<NodeData> getRealNodes() {
        return getNodes().stream()
                         .map(id -> (NodeData) getDataSet().getPrimitiveById(id, OsmPrimitiveType.NODE))
                         .collect(Collectors.toList());
    }

    @Override
    public NodeData getNode(int index) {
        return (NodeData) getDataSet().getPrimitiveById(nodes.get(index), OsmPrimitiveType.NODE);
    }

    @Override
    public NodeData lastNode() {
        return getNode(nodes.size() - 1);
    }

    @Override
    public NodeData firstNode() {
        return getNode(0);
    }

    @Override
    public int getNodesCount() {
        return nodes.size();
    }

    @Override
    public long getNodeId(int idx) {
        return nodes.get(idx);
    }

    @Override
    public boolean isClosed() {
        if (isIncomplete()) return false;
        return nodes.get(0).equals(nodes.get(nodes.size() - 1));
    }

    /**
     * Sets the nodes array
     * @param nodes The nodes this way consists of
     */
    public void setNodes(List<Long> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    @Override
    public WayData makeCopy() {
        return new WayData(this);
    }

    @Override
    public String toString() {
        return super.toString() + " WAY" + nodes;
    }

    @Override
    public OsmPrimitiveType getType() {
        return OsmPrimitiveType.WAY;
    }

    @Override
    public void accept(PrimitiveVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public BBox getBBox() {
        BBox bbox = new BBox();
        getNodes().forEach(id -> bbox.add((ILatLon) getDataSet().getPrimitiveById(id, OsmPrimitiveType.NODE)));
        return bbox;
    }

    @Override
    protected void addToBBox(BBox box, Set<PrimitiveId> visited) {
        box.add(getBBox());
    }
}
