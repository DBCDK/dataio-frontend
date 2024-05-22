package dk.dbc.dataio.gui.client.pages.flow.show;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwtmockito.GwtMockitoTestRunner;
import dk.dbc.dataio.gui.client.model.FlowModel;
import dk.dbc.dataio.gui.client.modelBuilders.FlowModelBuilder;
import dk.dbc.dataio.gui.client.util.CommonGinjector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * PresenterImpl unit tests
 * <p/>
 * The test methods of this class uses the following naming convention:
 * <p/>
 * unitOfWork_stateUnderTest_expectedBehavior
 */
@RunWith(GwtMockitoTestRunner.class)
public class ViewTest {

    @Mock
    Presenter mockedPresenter;
    @Mock
    dk.dbc.dataio.gui.client.pages.navigation.Texts mockedMenuTexts;
    @Mock
    CommonGinjector mockedCommonGinjector;
    @Mock
    ViewGinjector mockedViewGinjector;

    // Test Data
    private FlowModel flowModel1 = new FlowModelBuilder().setName("Fnam1").setTimeOfLastModification("2016-11-18 15:24:40").build();
    private FlowModel flowModel2 = new FlowModelBuilder().setName("Fnam2").build();
    private FlowModel flowModel3 = new FlowModelBuilder().setName("Fnam3").build();
    private List<FlowModel> flowModels = Arrays.asList(flowModel1, flowModel2);

    // Subject Under Test
    private View view;

    // Mocked Texts
    @Mock
    static Texts mockedTexts;
    final static String MOCKED_LABEL_FLOWS = "Mocked Text: label_Flows";
    final static String MOCKED_COLUMNHEADER_NAME = "Mocked Text: columnHeader_Name";
    final static String MOCKED_COLUMNHEADER_DESCRIPTION = "Mocked Text: columnHeader_Description";
    final static String MOCKED_COLUMNHEADER_TIME_OF_LAST_MODIFICATON = "Mocked Text: columnHeader_TimeOfLastModification";
    final static String MOCKED_COLUMNHEADER_ACTION_DELETE = "Mocked Text: columnHeader_Action_delete";
    final static String MOCKED_BUTTON_DELETE = "Mocked Text: button_Delete";

    class ViewConcrete extends View {
        public ViewConcrete() {
            super();
            commonInjector = mockedCommonGinjector;
            viewInjector = mockedViewGinjector;
        }

        @Override
        public Texts getTexts() {
            return mockedTexts;
        }
    }

    @Before
    public void setupMockedTextsBehaviour() {
        when(mockedViewGinjector.getTexts()).thenReturn(mockedTexts);
        when(mockedCommonGinjector.getMenuTexts()).thenReturn(mockedMenuTexts);
        when(mockedMenuTexts.menu_Flows()).thenReturn("Header Text");

        when(mockedTexts.label_Flows()).thenReturn(MOCKED_LABEL_FLOWS);
        when(mockedTexts.columnHeader_Name()).thenReturn(MOCKED_COLUMNHEADER_NAME);
        when(mockedTexts.columnHeader_Description()).thenReturn(MOCKED_COLUMNHEADER_DESCRIPTION);
        when(mockedTexts.columnHeader_TimeOfLastModification()).thenReturn(MOCKED_COLUMNHEADER_TIME_OF_LAST_MODIFICATON);
        when(mockedTexts.columnHeader_Action_Delete()).thenReturn(MOCKED_COLUMNHEADER_ACTION_DELETE);
        when(mockedTexts.button_Delete()).thenReturn(MOCKED_BUTTON_DELETE);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void constructor_instantiate_objectCorrectInitialized() {
        // Subject Under Test
        setupView();

        // Verify invocations
        verify(view.flowsTable).addColumn(isA(Column.class), eq(MOCKED_COLUMNHEADER_NAME));
        verify(view.flowsTable).addColumn(isA(Column.class), eq(MOCKED_COLUMNHEADER_DESCRIPTION));
        verify(view.flowsTable).addColumn(isA(Column.class), eq(MOCKED_COLUMNHEADER_TIME_OF_LAST_MODIFICATON));
        verify(view.flowsTable).addColumn(isA(Column.class), eq(MOCKED_COLUMNHEADER_ACTION_DELETE));
    }

    @Test
    public void setFlows_callSetupFlows_dataSetupCorrect() {
        setupView();

        List<FlowModel> models = view.dataProvider.getList();
        assertThat(models.isEmpty(), is(true));

        // Subject Under Test
        view.setFlows(flowModels);

        assertThat(models.isEmpty(), is(false));
        assertThat(models.size(), is(2));
        assertThat(models.get(0).getFlowName(), is(flowModel1.getFlowName()));
        assertThat(models.get(1).getFlowName(), is(flowModel2.getFlowName()));
    }

    @Test
    public void setFlows_callSetupFlowsTwice_dataSetupCorrect() {
        setupView();
        List<FlowModel> models = view.dataProvider.getList();

        // Subject Under Test
        view.setFlows(flowModels);
        view.setFlows(Collections.singletonList(flowModel3));  // The second call clears the existing flowModels, and puts flowModel3 only

        assertThat(models.isEmpty(), is(false));
        assertThat(models.size(), is(1));
        assertThat(models.get(0).getFlowName(), is(flowModel3.getFlowName()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void constructNameColumn_call_correctlySetup() {
        setupView();

        // Subject Under Test
        Column column = view.constructNameColumn();

        // Test that correct getValue handler has been setup
        assertThat(column.getValue(flowModel1), is(flowModel1.getFlowName()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void constructDescriptionColumn_call_correctlySetup() {
        setupView();

        // Subject Under Test
        Column column = view.constructDescriptionColumn();

        // Test that correct getValue handler has been setup
        assertThat(column.getValue(flowModel1), is(flowModel1.getDescription()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void constructTimeOfFlowComponentUpdate_call_correctlySetup() {
        setupView();

        // Subject Under Test
        Column column = view.constructTimeOfLastModificationColumn();

        // Test that correct getValue handler has been setup
        assertThat(column.getValue(flowModel1), is(flowModel1.getTimeOfLastModification()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void constructDeleteActionColumn_call_correctlySetup() {
        setupView();

        // Subject Under Test
        Column column = view.constructDeleteActionColumn();

        // Test that correct getValue handler has been setup
        assertThat((String) column.getValue(flowModel1), is(mockedTexts.button_Delete()));

        // Test that the right action is activated upon click
        view.setPresenter(mockedPresenter);
        FieldUpdater fieldUpdater = column.getFieldUpdater();
        fieldUpdater.update(334, flowModel1, "Updated Button Text");  // Simulate a click on the column
        verify(mockedPresenter).deleteFlow(flowModel1);
    }

    private void setupView() {
        view = new ViewConcrete();
    }
}
