package dk.dbc.dataio.gui.client.model;

import dk.dbc.dataio.gui.client.util.Format;

import java.util.List;

public class FlowModel extends GenericBackendModel {

    private String flowName;
    private String description;
    private String timeOfLastModification;

    public FlowModel(long id, long version, String name, String description, String timeOfLastModification) {
        super(id, version);
        this.flowName = name;
        this.description = description;
        this.timeOfLastModification = timeOfLastModification;
    }

    public FlowModel() {
        super(0L, 0L);
        this.flowName = "";
        this.description = "";
        this.timeOfLastModification = "";
    }

    /**
     * @return flowName The name of the flow
     */
    public String getFlowName() {
        return flowName;
    }

    /**
     * Set flow name
     *
     * @param flowName The name of the flow
     */
    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    /**
     * @return description The flow description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set flow description
     *
     * @param description The flow description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return time when the flow was last updated
     */
    public String getTimeOfLastModification() {
        return timeOfLastModification;
    }

    /**
     * Checks for empty String values
     *
     * @return true if no empty String values were found, otherwise false
     */
    public boolean isInputFieldsEmpty() {
        return flowName == null
                || flowName.isEmpty()
                || description == null
                || description.isEmpty();
    }

    /**
     * Checks if the flow name contains illegal characters.
     * A-Å, 0-9, - (minus), + (plus), _ (underscore) and space is valid
     *
     * @return a list containing illegal characters found. Empty list if none found.
     */
    public List<String> getDataioPatternMatches() {
        return Format.getDataioPatternMatches(flowName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlowModel)) return false;

        FlowModel flowModel = (FlowModel) o;

        if (flowName != null ? !flowName.equals(flowModel.flowName) : flowModel.flowName != null) return false;
        return description != null ? description.equals(flowModel.description) : flowModel.description == null;

    }

    @Override
    public int hashCode() {
        int result = flowName != null ? flowName.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
