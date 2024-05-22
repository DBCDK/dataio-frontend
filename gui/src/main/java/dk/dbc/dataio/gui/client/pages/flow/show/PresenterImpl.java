package dk.dbc.dataio.gui.client.pages.flow.show;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import dk.dbc.dataio.gui.client.exceptions.FilteredAsyncCallback;
import dk.dbc.dataio.gui.client.exceptions.ProxyErrorTranslator;
import dk.dbc.dataio.gui.client.model.FlowModel;
import dk.dbc.dataio.gui.client.util.CommonGinjector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This class represents the show flows presenter implementation
 */
public class PresenterImpl extends AbstractActivity implements Presenter {

    ViewGinjector viewInjector = GWT.create(ViewGinjector.class);
    CommonGinjector commonInjector = GWT.create(CommonGinjector.class);
    private final PlaceController placeController;

    public PresenterImpl(PlaceController placeController) {
        this.placeController = placeController;
    }


    /**
     * start method
     * Is called by PlaceManager, whenever the Place is being invoked
     * This method is the start signal for the presenter
     *
     * @param containerWidget the widget to use
     * @param eventBus        the eventBus to use
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        initializeView();
        containerWidget.setWidget(getView().asWidget());
        fetchFlows();
    }

    /*
     * Local methods
     */

    /**
     * This method fetches all flows, and sends them to the view
     */
    private void fetchFlows() {
        commonInjector.getFlowStoreProxyAsync().findAllFlows(new FetchFlowsCallback());
    }

    /**
     * This method deciphers if a flow has been added, updated or deleted.
     * The view and selection model are updated accordingly
     *
     * @param models the list of flows returned from flow store proxy
     */
    private void setFlowsAndDecipherSelection(Set<FlowModel> dataProviderSet, List<FlowModel> models) {
        if (dataProviderSet.size() > models.size() || dataProviderSet.size() == 0) {
            getView().selectionModel.clear();
            getView().setFlows(models);
        } else {
            for (FlowModel current : models) {
                if (!dataProviderSet.contains(current)) {
                    getView().setFlows(models);
                    getView().selectionModel.setSelected(current, true);
                    break;
                }
            }
        }
    }

    @Override
    public void deleteFlow(FlowModel flow) {
        commonInjector.getFlowStoreProxyAsync().deleteFlow(flow.getId(), flow.getVersion(), new DeleteFlowCallback());
    }

    /*
     * Private classes
     */
    private void initializeView() {
        getView().setPresenter(this);
        getView().setHeader(commonInjector.getMenuTexts().menu_Flows());
    }

    private View getView() {
        return viewInjector.getView();
    }

    /**
     * This class is the callback class for the findAllFlows method in the Flow Store
     */
    protected class FetchFlowsCallback extends FilteredAsyncCallback<List<FlowModel>> {
        @Override
        public void onFilteredFailure(Throwable e) {
            getView().setErrorText(ProxyErrorTranslator.toClientErrorFromFlowStoreProxy(e, commonInjector.getProxyErrorTexts(), this.getClass().getCanonicalName()));
        }

        @Override
        public void onSuccess(List<FlowModel> models) {
            setFlowsAndDecipherSelection(new HashSet<FlowModel>(getView().dataProvider.getList()), models);
        }
    }

    class DeleteFlowCallback extends FilteredAsyncCallback<Void> {
        @Override
        public void onFilteredFailure(Throwable e) {
            getView().setErrorText(ProxyErrorTranslator.toClientErrorFromFlowStoreProxy(e, commonInjector.getProxyErrorTexts(), null));
        }

        @Override
        public void onSuccess(Void aVoid) {
            fetchFlows();
        }
    }
}
