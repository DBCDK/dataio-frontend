package dk.dbc.dataio.gui.client.pages.flow.show;


import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import dk.dbc.dataio.gui.client.model.FlowModel;
import dk.dbc.dataio.gui.client.util.CommonGinjector;

import java.util.List;

/**
 * This class is the View class for the Flows Show View
 */
public class View extends ViewWidget {

    CommonGinjector commonInjector = GWT.create(CommonGinjector.class);
    ViewGinjector viewInjector = GWT.create(ViewGinjector.class);

    ListDataProvider<FlowModel> dataProvider;
    SingleSelectionModel<FlowModel> selectionModel = new SingleSelectionModel<FlowModel>();

    public View() {
        super();
        setupColumns();
    }


    /**
     * This method is used to put data into the view
     *
     * @param flowModels The list of flows to put into the view
     */
    public void setFlows(List<FlowModel> flowModels) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(flowModels);
    }


    /**
     * Private methods
     */


    /**
     * This method sets up all columns in the view
     * It is called before data has been applied to the view - data is being applied in the setFlows method
     */
    @SuppressWarnings("unchecked")
    private void setupColumns() {
        dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(flowsTable);

        flowsTable.addColumn(constructNameColumn(), getTexts().columnHeader_Name());
        flowsTable.addColumn(constructDescriptionColumn(), getTexts().columnHeader_Description());
        flowsTable.addColumn(constructTimeOfLastModificationColumn(), getTexts().columnHeader_TimeOfLastModification());
        flowsTable.addColumn(constructDeleteActionColumn(), getTexts().columnHeader_Action_Delete());
        flowsTable.setSelectionModel(selectionModel);
    }

    /**
     * This method constructs the Name column
     * Should have been private, but is package-private to enable unit test
     *
     * @return the constructed Name column
     */
    Column constructNameColumn() {
        return new TextColumn<FlowModel>() {
            @Override
            public String getValue(FlowModel model) {
                return model.getFlowName();
            }
        };
    }

    /**
     * This method constructs the Description column
     * Should have been private, but is package-private to enable unit test
     *
     * @return the constructed Description column
     */
    Column constructDescriptionColumn() {
        return new TextColumn<FlowModel>() {
            @Override
            public String getValue(FlowModel model) {
                return model.getDescription();
            }
        };
    }

    /**
     * This method constructs the timeOfLastModification column
     * Should have been private, but is package-private to enable unit test
     *
     * @return the constructed timeOfLastModification column
     */
    Column constructTimeOfLastModificationColumn() {
        return new TextColumn<FlowModel>() {
            @Override
            public String getValue(FlowModel model) {
                return model.getTimeOfLastModification();
            }
        };
    }

    /**
     * This method constructs the Action column
     * Should have been private, but is package-private to enable unit test
     *
     * @return the constructed Action column
     */
    @SuppressWarnings("unchecked")
    Column constructDeleteActionColumn() {
        Column column = new Column<FlowModel, String>(new ButtonCell()) {
            @Override
            public String getValue(FlowModel model) {
                // The value to display in the button.
                return getTexts().button_Delete();
            }
        };

        column.setFieldUpdater(new FieldUpdater<FlowModel, String>() {
            @Override
            public void update(int index, FlowModel model, String buttonText) {
                presenter.deleteFlow(model);
            }
        });
        return column;
    }

    Texts getTexts() {
        return viewInjector.getTexts();
    }
}
