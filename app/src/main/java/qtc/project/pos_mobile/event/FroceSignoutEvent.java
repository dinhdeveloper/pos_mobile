package qtc.project.pos_mobile.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class FroceSignoutEvent {
    public static void post()
    {
        BusHelper.post(new FroceSignoutEvent());
    }
}
