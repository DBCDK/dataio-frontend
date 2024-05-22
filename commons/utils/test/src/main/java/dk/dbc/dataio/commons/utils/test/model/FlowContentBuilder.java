package dk.dbc.dataio.commons.utils.test.model;

import dk.dbc.dataio.commons.types.FlowContent;

import java.util.Date;

public class FlowContentBuilder {
    private String name = "name";
    private String description = "description";
    private Date timeOfLastModification = null;

    public FlowContentBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public FlowContentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public FlowContentBuilder setTimeOfLastModification(Date timeOfLastModification) {
        this.timeOfLastModification = timeOfLastModification;
        return this;
    }

    public FlowContent build() {
        return new FlowContent(name, description, timeOfLastModification);
    }
}
