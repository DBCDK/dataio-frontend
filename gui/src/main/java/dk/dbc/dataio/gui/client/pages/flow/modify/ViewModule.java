package dk.dbc.dataio.gui.client.pages.flow.modify;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * Created by ThomasBerg on 09/11/15.
 */
public class ViewModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ViewWidget.class).in(Singleton.class);
        bind(Texts.class).in(Singleton.class);
    }
}
