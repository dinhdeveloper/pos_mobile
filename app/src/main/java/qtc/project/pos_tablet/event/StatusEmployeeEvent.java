package qtc.project.pos_tablet.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class StatusEmployeeEvent {
    public static void post()
    {
        BusHelper.post(new StatusEmployeeEvent());
    }
}
