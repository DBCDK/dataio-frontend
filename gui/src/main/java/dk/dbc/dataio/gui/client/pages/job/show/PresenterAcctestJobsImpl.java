package dk.dbc.dataio.gui.client.pages.job.show;

import com.google.gwt.place.shared.PlaceController;
import dk.dbc.dataio.jobstore.types.criteria.JobListCriteria;
import dk.dbc.dataio.jobstore.types.criteria.ListFilter;

/**
 *
 */
public class PresenterAcctestJobsImpl extends PresenterImpl {
    /**
     * Default constructor
     *
     * @param placeController PlaceController for navigation
     * @param globalJobsView  Global Jobs View, necessary for keeping filter state etc.
     * @param header          Breadcrumb header text
     */
    public PresenterAcctestJobsImpl(PlaceController placeController, View globalJobsView, String header) {
        super(placeController, globalJobsView, header);
        globalJobsView.jobsTable.getElement().addClassName("acctest-jobs-table");
    }

    /**
     * Abstract Methods
     */
    @Override
    protected void updateBaseQuery() {
        JobListCriteria criteria = new JobListCriteria()
                .where(new ListFilter<>(JobListCriteria.Field.SPECIFICATION, ListFilter.Op.JSON_LEFT_CONTAINS, "{ \"type\": \"ACCTEST\"}"));
        view.dataProvider.setBaseCriteria(criteria);
    }
}
