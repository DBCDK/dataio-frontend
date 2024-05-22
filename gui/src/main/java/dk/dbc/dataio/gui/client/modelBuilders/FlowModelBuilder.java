package dk.dbc.dataio.gui.client.modelBuilders;

import dk.dbc.dataio.gui.client.model.FlowModel;

public class FlowModelBuilder {
    private long id = 3L;
    private long version = 1L;
    private String name = "name";
    private String description = "description";
    private String timeOfLastModification = "2016-11-18 15:24:40";

    public FlowModelBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public FlowModelBuilder setVersion(long version) {
        this.version = version;
        return this;
    }

    public FlowModelBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public FlowModelBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public FlowModelBuilder setTimeOfLastModification(String timeOfLastModification) {
        this.timeOfLastModification = timeOfLastModification;
        return this;
    }

    public FlowModel build() {
        return new FlowModel(id, version, name, description, timeOfLastModification);
    }
}
