package dk.dbc.dataio.commons.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dk.dbc.invariant.InvariantUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * FlowContent DTO class.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowContent implements Serializable {
    private static final long serialVersionUID = 5520247158829273054L;

    private final String name;
    private final String description;
    private Date timeOfLastModification;

    /**
     * Class constructor
     *
     * @param name                      flow name
     * @param description               flow description
     * @param timeOfLastModification    time of last flow update
     * @throws NullPointerException     if given null-valued name, description or components argument
     * @throws IllegalArgumentException if given empty-valued name
     */
    @JsonCreator
    public FlowContent(@JsonProperty("name") String name,
                       @JsonProperty("description") String description,
                       @JsonProperty("timeOfLastModification") Date timeOfLastModification) {

        this.name = InvariantUtil.checkNotNullNotEmptyOrThrow(name, "name");
        this.description = InvariantUtil.checkNotNullNotEmptyOrThrow(description, "description");
        this.timeOfLastModification = timeOfLastModification;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Date getTimeOfLastModification() {
        return timeOfLastModification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlowContent)) return false;

        FlowContent that = (FlowContent) o;

        return name.equals(that.name)
                && description.equals(that.description)
                && timeOfLastModification.equals(timeOfLastModification);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + timeOfLastModification.hashCode();
        return result;
    }
}
