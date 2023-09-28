package com.example.firebasechat.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasechat.R;

import com.google.firebase.appcheck.interop.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonFunctions {

    public static Bundle getBundle(Activity activity) {
        return activity.getIntent().getExtras() != null ? activity.getIntent().getExtras() : new Bundle();
    }

    public static boolean isLastItemDisplayingLinear(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }

    public static void setSuccessResult(Activity activity, Intent intent) {
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

    public static Map<String, Integer> getScreenSize(Activity activity) {
        int columnWidth;
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();

        point.x = display.getWidth();
        point.y = display.getHeight();

        Map<String, Integer> mapScreenSize = new HashMap<>();
        mapScreenSize.put("width", point.x);
        mapScreenSize.put("height", point.y);
        return mapScreenSize;


    }


    public static boolean CameracheckPermissionsGranted(String[] permissions) {
        boolean granted = true;
        for (String permission : permissions) {
            if (isPermissionDenied(App.getInstance(), permission)) {
                granted = false;
            }
        }
        return granted;
}

   /* public static void showDatePickerMinMaxDate(Activity activity, String defaultDate, String minDate, String maxDate, final String tag, final MyDateSelectedListener myDateSelectedListener) {


        Calendar calendar = Calendar.getInstance();
        *//*try {*//*
        if (!defaultDate.equals("") && !defaultDate.equalsIgnoreCase("0000-00-00")) {
            String[] date = defaultDate.split("-");

           *//* calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date[1]));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[2]));*//*
            try {
                calendar.setTime(DataFormats.dateFormatDbDate.parse(defaultDate));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                myDateSelectedListener.onDateSet(tag, calendar, calendar.getTime());
            }
        };
        DatePickerDialog aDatePickerDialog = new DatePickerDialog(
                activity, onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateandTime = sdf.format(new Date());

            //minDate =currentDateandTime;
            if (!minDate.equals("") && !minDate.equalsIgnoreCase("0000-00-00")) {
                Log.e("minDatee",minDate);


                long dateMin = DataFormats.dateFormatDbDate.parse(currentDateandTime).getTime();
                aDatePickerDialog.getDatePicker().setMinDate(dateMin);

            }
           *//* if (!maxDate.equals("") && !maxDate.equalsIgnoreCase("0000-00-00")) {
                long dateMax = DataFormats.dateFormatDbDate.parse(maxDate).getTime();
                aDatePickerDialog.getDatePicker().setMaxDate(dateMax);
            }*//*
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        DialogUtils.setButtonTextColor(activity, aDatePickerDialog);
        ArrayList<View> views = aDatePickerDialog.getDatePicker().getTouchables();
        if (views != null && views.size() > 0)
            aDatePickerDialog.getDatePicker().getTouchables().get(0).performClick();
        aDatePickerDialog.show();
        *//*} catch (ParseException e) {
            Log.i("catchcc", e.getMessage());
            e.printStackTrace();
        }*//*

    }*/


    public static void writeAPIResponse(String data, Context context) {
        if (BuildConfig.DEBUG) {
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
            String time = format.format(calendar.getTime());
/*
            final File path =
                    Environment.getExternalStoragePublicDirectory
                            (
                                    //Environment.DIRECTORY_PICTURES
                                    Environment.DIRECTORY_DOCUMENTS + "/SnapayAPI/"
                            );*/
            File path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/SnapayAPI/");
            // Make sure the path directory exists.
            if (!path.exists()) {
                // Make it, if it doesn't exit
                path.mkdirs();
            }


            final File file = new File(path, "response_" + time + ".txt");

            // Save your stream, don't forget to flush() it before closing it.


            try {
                file.createNewFile();
                FileOutputStream fOut = new FileOutputStream(file);
                fOut.write(data.getBytes());
                /*OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);

                myOutWriter.close();*/

                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public static <T> void setJsonArrayGson(JSONArray array, List<T> list, Class<T> tClass, RecyclerView.Adapter<?> adapter) throws JSONException {
        for (int i = 0; i < array.length(); i++) {
            T t = App.gson.fromJson(array.getJSONObject(i).toString(), tClass);
            list.add(t);
        }
        adapter.notifyDataSetChanged();
    }


    public static boolean checkPermissionsGranted(Context context, String[] permissions) {
        boolean granted = true;
        for (String permission : permissions) {
            if (!(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)) {
                granted = false;
            }
        }
        return granted;
    }

    public static void openFile(Activity activity, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String path = file.getPath();
        Log.i("File", path);

        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        String extension = path.substring(path.lastIndexOf(".") + 1);
        String mimeType = myMime.getMimeTypeFromExtension(extension);

        Uri uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, mimeType);
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public static void openWeb(Activity activity,String docUrl){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(activity, R.color.black));
        builder.setShowTitle(true);

        CustomTabsIntent customTabsIntent = builder.build();
        try {
            customTabsIntent.intent.setPackage("com.android.chrome");


        } catch (Exception e) {

            e.printStackTrace();
        }
        customTabsIntent.launchUrl(activity, Uri.parse(docUrl));

    }

    public static String getCode(String msg) {
        String[] msgArray = msg.split(" ");
        return msgArray[1];
    }

/*
    public static void showDatePickerMinMaxDate(Activity activity, String defaultDate, String minDate, String maxDate, final String tag, final MyDateSelectedListener myDateSelectedListener) {
        Calendar calendar = Calendar.getInstance();

        try {
            if (!defaultDate.equals("") && !defaultDate.equalsIgnoreCase("0000-00-00")) {
                String[] date = defaultDate.split("-");

           */
/* calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date[1]));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[2]));*//*

                calendar.setTime(DataFormats.dateFormatDbDate.parse(defaultDate));
            }
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.YEAR, year);
                    myDateSelectedListener.onDateSet(tag, calendar, calendar.getTime());
                }
            };
            DatePickerDialog aDatePickerDialog = new DatePickerDialog(activity, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            if (!minDate.equals("") && !minDate.equalsIgnoreCase("0000-00-00")) {
                long dateMin = DataFormats.dateFormatDbDate.parse(minDate).getTime();
                aDatePickerDialog.getDatePicker().setMinDate(dateMin);

            }
            if (!maxDate.equals("") && !maxDate.equalsIgnoreCase("0000-00-00")) {
                long dateMax = DataFormats.dateFormatDbDate.parse(maxDate).getTime();
                aDatePickerDialog.getDatePicker().setMaxDate(dateMax);
            }
            ArrayList<View> views = aDatePickerDialog.getDatePicker().getTouchables();
            if (views != null && views.size() > 0)
                //aDatePickerDialog.getDatePicker().getTouchables().get(0).performClick();
                aDatePickerDialog.show();

        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

    }
*/



/*
    public static void showTimePicker(Activity activity, final String tag, final MyDateSelectedListener myDateSelectedListener) {
        Calendar calendar = Calendar.getInstance();


        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                myDateSelectedListener.onDateSet(tag, calendar, calendar.getTime());
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, timeSetListener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false);
        timePickerDialog.show();


    }
*/

    public static List<JSONObject> getJSONObjectsList(JSONArray array) {

        List<JSONObject> list = new ArrayList<>();
        list.clear();
        try {
            for (int i = 0; i < array.length(); i++) {

                JSONObject object = array.getJSONObject(i);

                list.add(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }



    public static boolean checkPermissionsRationale(Activity activity, Fragment fragment, String[] permissions) {
        boolean granted = true;
        for (String permission : permissions) {
            if (isPermissionDenied(activity, permission)) {
                if (fragment != null) {
                    Log.i("PermissionRationale", String.valueOf(fragment.shouldShowRequestPermissionRationale(permission)));
                    if (!fragment.shouldShowRequestPermissionRationale(permission)) {
                        granted = false;
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!activity.shouldShowRequestPermissionRationale(permission)) {
                            granted = false;
                        }
                    }
                }
            }
        }
        Log.i("PermissionRationale", String.valueOf(granted));
        return granted;
    }

    public static LayoutInflater getAdapterInflater(ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext());
    }

    public static void openDialPad(Activity activity, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        activity.startActivity(intent);
    }/*
    public static boolean checkPermissionsGranted(String[] permissions) {
        boolean granted = true;
        for (String permission : permissions) {
            if (isPermissionDenied(App.getInstance(), permission)) {
                granted = false;
            }
        }
        return granted;
    }
*/

    public static boolean isPermissionDenied(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

    public static void gotoPermission(Activity activity, Fragment fragment, int code) {

        Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + activity.getPackageName()));
       /* i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);*/
        if (fragment != null) {
            fragment.startActivityForResult(i, code);
        } else {
            activity.startActivityForResult(i, code);
        }
    }


    public static void askPermission(Activity activity, Fragment fragment, String[] permissions, int code) {

        if (!checkPermissionsRationale(activity, fragment, permissions)) {
            gotoPermission(activity, fragment, code);
        } else {
            if (fragment != null) {
                fragment.requestPermissions(permissions, code);
            } else {
                ActivityCompat.requestPermissions(activity, permissions, code);
            }
        }

    }


    public static void setJSONArray(JSONObject object, String arrayName, List<JSONObject> list, RecyclerView.Adapter adapter) throws JSONException {
        JSONArray arrayExp = object.getJSONArray(arrayName);
        list.addAll(getJSONObjectsList(arrayExp));
       adapter.notifyDataSetChanged();
    }


    public static Bundle getBundle(Fragment fragment) {
        return fragment.getArguments() != null ? fragment.getArguments() : new Bundle();
    }
}
