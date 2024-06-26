package dk.dbc.dataio.gui.client.pages.flow.show;

import com.google.gwt.i18n.client.Constants;
import dk.dbc.dataio.gui.client.i18n.MainConstants;


public interface Texts extends Constants {
    // Labels
    @DefaultStringValue(MainConstants.TRANSLATED_TEXT_IS_MISSING)
    String label_Flows();

    // Column Headers
    @DefaultStringValue(MainConstants.TRANSLATED_TEXT_IS_MISSING)
    String columnHeader_Name();

    @DefaultStringValue(MainConstants.TRANSLATED_TEXT_IS_MISSING)
    String columnHeader_Description();

    @DefaultStringValue(MainConstants.TRANSLATED_TEXT_IS_MISSING)
    String columnHeader_TimeOfLastModification();

    @DefaultStringValue(MainConstants.TRANSLATED_TEXT_IS_MISSING)
    String columnHeader_Action_Delete();

    // Buttons
    @DefaultStringValue(MainConstants.TRANSLATED_TEXT_IS_MISSING)
    String button_Delete();
}
