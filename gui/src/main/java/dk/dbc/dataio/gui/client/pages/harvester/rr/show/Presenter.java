package dk.dbc.dataio.gui.client.pages.harvester.rr.show;

import dk.dbc.dataio.gui.client.presenters.GenericPresenter;

public interface Presenter extends GenericPresenter {
    void editHarvesterConfig(String id);

    void createHarvester();
}
