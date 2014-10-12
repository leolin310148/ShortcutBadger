package me.leolin.shortcutbadger.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import me.leolin.shortcutbadger.ShortcutBadgeException;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: leolin
 * Date: 2013/11/14
 * Time: 下午7:15
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtil {
    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();

    }

    public static byte[] drawBadgeOnAppIcon(Context context, int badgeCount) throws ShortcutBadgeException {

        Bitmap appIcon = null;
        String gText = String.valueOf(badgeCount);

        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if (context.getPackageName().equals(packageInfo.packageName)) {
                Drawable drawable = packageInfo.applicationInfo.loadIcon(context.getPackageManager());
                appIcon = drawableToBitmap(drawable);
            }
        }

        if (appIcon == null) {
            throw new ShortcutBadgeException("count not load the app Icon");
        }

        if (badgeCount == 0) {
            return bitmapToByteArray(appIcon);
        }

        float scale = context.getResources().getDisplayMetrics().density;

        android.graphics.Bitmap.Config bitmapConfig =
                appIcon.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        appIcon = appIcon.copy(bitmapConfig, true);

        float width = appIcon.getWidth();
        float height = appIcon.getHeight();
        float radius = ((width > height ? width : height) / 4);
        float cx = appIcon.getWidth() - radius;
        float cy = radius;


        Paint paint_red = new Paint();
        paint_red.setColor(Color.RED);
        Paint paint_white = new Paint();
        paint_white.setColor(Color.WHITE);
        Canvas canvas = new Canvas(appIcon);
        canvas.drawCircle(cx, cy, radius, paint_white);


        canvas.drawCircle(cx, cy, radius * 6 / 7, paint_red);


        // new antialised Paint
        Paint paint_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint_text.setColor(Color.WHITE);
        // text size in pixels
        int textSize = (int)(radius*0.7);
        if (gText.length() > 1) {
            textSize =  (int)(radius*0.5);
        }
        paint_text.setTextSize((int) (textSize * scale));
        paint_text.setFakeBoldText(true);
        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint_text.getTextBounds(gText, 0, gText.length(), bounds);
        float bw = bounds.width() / 2;
        if (gText.endsWith("1")) {
            bw *= 1.25;
        }
        float bh = bounds.height() / 2;
        canvas.drawText(gText, cx - bw, cy + bh, paint_text);

        return bitmapToByteArray(appIcon);
    }


}
