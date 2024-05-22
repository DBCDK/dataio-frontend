package dk.dbc.dataio.gui.server.modelmappers;

import dk.dbc.dataio.commons.types.Flow;
import dk.dbc.dataio.commons.types.FlowContent;
import dk.dbc.dataio.commons.utils.test.model.FlowBuilder;
import dk.dbc.dataio.commons.utils.test.model.FlowContentBuilder;
import dk.dbc.dataio.gui.client.model.FlowModel;
import dk.dbc.dataio.gui.client.modelBuilders.FlowModelBuilder;
import dk.dbc.dataio.gui.client.util.Format;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * FlowModelMapper unit tests
 * <p>
 * The test methods of this class uses the following naming convention:
 * <p>
 * unitOfWork_stateUnderTest_expectedBehavior
 */
public class FlowModelMapperTest {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format.LONG_DATE_TIME_FORMAT);
    private static final long ID = 746L;
    private static final long VERSION = 8483L;
    private static final String NAME = "the name";
    private static final String DESCRIPTION = "the description";
    private static final Date TIME_OF_LAST_MODIFICATION = new Date();

    @Test
    public void toModel_validInputNoFlowComponents_returnsValidModelNoFlowComponents() {

        FlowContent flowContent = new FlowContent(NAME, DESCRIPTION, TIME_OF_LAST_MODIFICATION);
        Flow flow = new Flow(ID, VERSION, flowContent);

        FlowModel model = FlowModelMapper.toModel(flow);
        assertThat(model.getId(), is(ID));
        assertThat(model.getVersion(), is(VERSION));
        assertThat(model.getFlowName(), is(NAME));
        assertThat(model.getDescription(), is(DESCRIPTION));
        assertThat(model.getTimeOfLastModification(), is(simpleDateFormat.format(TIME_OF_LAST_MODIFICATION)));
    }

    @Test
    public void toModel_validInput_returnsValidModel() {

        FlowContent flowContent = new FlowContentBuilder()
                .setName(NAME)
                .setDescription(DESCRIPTION)
                .setTimeOfLastModification(TIME_OF_LAST_MODIFICATION)
                .build();

        Flow flow = new FlowBuilder()
                .setId(ID)
                .setVersion(VERSION)
                .setContent(flowContent)
                .build();

        // Convert flow content to model
        FlowModel model = FlowModelMapper.toModel(flow);

        // Verify that the flow specific values have been set in the flow model.
        // (The flow component specific values have been tested in flowComponentModelMapperTest)
        assertThat(model.getId(), is(ID));
        assertThat(model.getVersion(), is(VERSION));
        assertThat(model.getFlowName(), is(NAME));
        assertThat(model.getDescription(), is(DESCRIPTION));

        // Verify that the mapping between flow model object and flow object is correct
        assertThat(flow.getId(), is(model.getId()));
        assertThat(flow.getVersion(), is(model.getVersion()));
        assertThat(flow.getContent().getName(), is(model.getFlowName()));
        assertThat(flow.getContent().getDescription(), is(model.getDescription()));
        assertThat(simpleDateFormat.format(flow.getContent().getTimeOfLastModification()), is(model.getTimeOfLastModification()));
    }

    @Test
    public void toFlowContent_invalidFlowName_throwsIllegalArgumentException() {
        final String flowName = "*%(Illegal)_&Name - €";
        final String expectedIllegalCharacters = "[*], [%], [(], [)], [&], [€]";
        FlowModel model = new FlowModelBuilder().setName(flowName).build();

        try {
            FlowModelMapper.toFlowContent(model);
            fail("Illegal flow name not detected");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage().contains(expectedIllegalCharacters), is(true));
        }
    }

    @Test
    public void toFlowContent_validInput_returnsValidFlowContent() {

        FlowModel model = new FlowModelBuilder().setId(ID).setVersion(VERSION).setName(NAME).setDescription(DESCRIPTION).setTimeOfLastModification("").build();

        // Convert model to flow content
        FlowContent flowContent = FlowModelMapper.toFlowContent(model);

        // Verify that the correct values have been set in the flow content
        assertThat(flowContent.getName(), is(NAME));
        assertThat(flowContent.getDescription(), is(DESCRIPTION));

        // Verify that the mapping between flow model object and flow object is correct
        assertThat(flowContent.getName(), is(model.getFlowName()));
        assertThat(flowContent.getDescription(), is(model.getDescription()));
        assertThat(flowContent.getTimeOfLastModification(), is(nullValue()));
    }

    @Test(expected = NullPointerException.class)
    public void toListOfFlowModels_nullInput_throws() {
        FlowModelMapper.toListOfFlowModels(null);
    }

    @Test
    public void toListOfFlowModels_emptyInputList_returnsEmptyList() {
        List<FlowModel> flowModels = FlowModelMapper.toListOfFlowModels(new ArrayList<>());
        assertThat(flowModels.size(), is(0));
    }

    @Test
    public void toListOfFlowModels_twoValidFlows_twoValidFlowModelsReturned() {
        Flow flow1 = new FlowBuilder()
                .setId(ID)
                .build();
        Flow flow2 = new FlowBuilder()
                .setId(ID + 1)
                .build();

        List<Flow> flows = new ArrayList<>(2);
        flows.add(flow1);
        flows.add(flow2);

        List<FlowModel> flowModels = FlowModelMapper.toListOfFlowModels(flows);

        assertThat(flowModels.size(), is(2));
        assertThat(flowModels.get(0).getId(), is(flow1.getId()));
        assertThat(flowModels.get(1).getId(), is(flow2.getId()));
    }

}
