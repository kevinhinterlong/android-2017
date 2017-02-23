package org.hackillinois.app2017;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hackillinois.app2017.Utils.HackIllinoisStatus.AFTER;
import static org.hackillinois.app2017.Utils.HackIllinoisStatus.BEFORE;
import static org.hackillinois.app2017.Utils.HackIllinoisStatus.DURING;

/**
 * Created by kevin on 2/21/2017.
 */

public class Utils {
    public static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String HACKILLINOIS_START = "2017-02-24T16:00:00.000Z";
    public static final String HACKILLINOIS_END = "2017-02-26T17:00:00.000Z";

    public static Bitmap getQRCodeBitmap(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.sharedPrefsName, Context.MODE_PRIVATE);
        return QRCode.from(sharedPreferences.getString("id", "N/A")).withSize(400, 400).bitmap();
    }

    //TODO save the image instead of creating it every time
    public static void showFullScreenQRCode(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        Bitmap qrCode = getQRCodeBitmap(context);
        imageView.setImageBitmap(qrCode);

        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(imageView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public static TextView generateLocationTextView(final Context context, String name) {
        TextView textView = new TextView(context);
        textView.setText(name);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(MainActivity.BOTTOM_BAR_TAB, 2);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });
        textView.setGravity(View.TEXT_ALIGNMENT_TEXT_START | View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(ContextCompat.getColor(context, R.color.seafoam_blue));
        return textView;
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static Date getDateFromAPI(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.US);
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringDateAsAPI(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.US);
        return dateFormat.format(date);
    }

    public static boolean isBeforeHackIllinois(Date date) {
        Date start = getDateFromAPI(HACKILLINOIS_START);
        return date.before(start);
    }

    public static boolean isDuringHackIllinois(Date date) {
        return !isBeforeHackIllinois(date) && !isAfterHackIllinois(date);
    }

    public static boolean isAfterHackIllinois(Date date) {
        Date end = getDateFromAPI(HACKILLINOIS_END);
        return date.after(end);
    }

    public static HackIllinoisStatus getHackIllinoisStatus() {
        Date date = new Date();
        if (isBeforeHackIllinois(date)) {
            return BEFORE;
        } else if (isDuringHackIllinois(date)) {
            return DURING;
        } else {
            return AFTER;
        }
    }

    public enum HackIllinoisStatus {
        BEFORE,
        DURING,
        AFTER
    }
}
