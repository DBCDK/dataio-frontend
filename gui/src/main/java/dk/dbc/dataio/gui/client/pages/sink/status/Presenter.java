package dk.dbc.dataio.gui.client.pages.sink.status;

import dk.dbc.dataio.gui.client.presenters.GenericPresenter;

public interface Presenter extends GenericPresenter {
    void showJobsFilteredBySink(long sinkId);

    void fetchSinkStatus();
}
