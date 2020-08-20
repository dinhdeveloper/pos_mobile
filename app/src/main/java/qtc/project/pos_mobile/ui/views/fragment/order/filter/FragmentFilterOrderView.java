package qtc.project.pos_mobile.ui.views.fragment.order.filter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos_mobile.R;
import qtc.project.pos_mobile.activity.HomeActivity;

public class FragmentFilterOrderView extends BaseView<FragmentFilterOrderView.UIContainer> implements FragmentFilterOrderViewInterface{
    HomeActivity activity;
    FragmentFilterOrderViewCallback callback;

    DatePickerDialog picker;
    TimePickerDialog timePicker;
    String date = "";
    String time = "";
    @Override
    public void init(HomeActivity activity, FragmentFilterOrderViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        ui.title_header.setText("Filter");
        ui.imageNavLeft.setOnClickListener(v -> {
            if (callback!=null)
                callback.goBackP();
        });
        
        onClick();
    }

    private void onClick() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);


        ui.imageCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                ui.calendarFilter.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " ";


                            }
                        }, year, month, day);
                picker.show();
            }
        });
        ui.hourFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                ui.hourFilter.setText(sHour + ":" + sMinute);
                                time = sHour + ":" + sMinute;
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });

        ui.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.filterData(date + time, ui.idOrderFilter.getText().toString());
                }
            }
        });


        ui.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.goBackP();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentFilterOrderView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_order_filter;
    }



    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.title_header)
        public TextView title_header;

        @UiElement(R.id.imvHome)
        public ImageView imvHome;

        @UiElement(R.id.calendarFilter)
        public TextView calendarFilter;

        @UiElement(R.id.imageCalendar)
        public ImageView imageCalendar;

        @UiElement(R.id.hourFilter)
        public TextView hourFilter;

        @UiElement(R.id.idOrderFilter)
        public EditText idOrderFilter;

        @UiElement(R.id.btnExit)
        public LinearLayout btnExit;

        @UiElement(R.id.btnSearch)
        public LinearLayout btnSearch;



    }
}
