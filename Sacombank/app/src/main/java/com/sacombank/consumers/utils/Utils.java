package com.sacombank.consumers.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mastercard.mpqr.pushpayment.exception.ConflictiveTagException;
import com.mastercard.mpqr.pushpayment.exception.FormatException;
import com.mastercard.mpqr.pushpayment.exception.InvalidTagValueException;
import com.mastercard.mpqr.pushpayment.exception.MissingTagException;
import com.mastercard.mpqr.pushpayment.exception.UnknownTagException;
import com.mastercard.mpqr.pushpayment.model.PushPaymentData;
import com.mastercard.mpqr.pushpayment.parser.Parser;
import com.sacombank.consumers.R;

/**
 * Created by DUY on 7/13/2017.
 */

public class Utils {

//    public static List<Section> createMenu() {
//        List<Section> sectionList = new ArrayList<Section>();
//
//        Section oDemoSection = new Section("TRANG CHỦ");
//
//        Section oGeneralSection = new Section("VỀ CHÚNG TÔI");
//
//        Section oSettingSection = new Section("TÀI KHOẢN NGƯỜI DÙNG");
//        oSettingSection.addSectionItem(301, "Đăng nhập/Đăng ký", "");
//        oSettingSection.addSectionItem(302, "Cập nhật mật khẩu", "");
//        oSettingSection.addSectionItem(303, "Thông tin người dùng", "");
//        oSettingSection.addSectionItem(304, "Quản lý thẻ", "");
//
//        Section oMoreSection = new Section("THANH TOÁN");
//        oMoreSection.addSectionItem(601, "Thanh toán qua QR Code", "");
//        oMoreSection.addSectionItem(602, "Thanh toán hóa đơn", "");
//        oMoreSection.addSectionItem(603, "Thanh toán thẻ tín dụng", "");
//        oMoreSection.addSectionItem(604, "Nạp tiền điện thoại", "");
//
//        Section oFunSection = new Section("CHUYỂN KHOẢN TRỰC TUYẾN");
//
//        Section oConnectSection = new Section("LỊCH SỬ GIAO DỊCH");
//        oConnectSection.addSectionItem(601, "Thanh toán qua QR Code", "");
//        oConnectSection.addSectionItem(602, "Thanh toán bằng Mobile POS", "");
//        oConnectSection.addSectionItem(603, "Thanh toán hóa đơn", "");
//        oConnectSection.addSectionItem(604, "Thanh toán thẻ tín dụng", "");
//        oConnectSection.addSectionItem(605, "Nạp tiền điện thoại", "");
//
//        Section oCallSection = new Section("ĐIỀU KHOẢN & ĐIỀU KIỆN");
//        oCallSection.addSectionItem(701, "Điều khoản", "");
//        oCallSection.addSectionItem(702, "Điều kiện", "");
//
//        Section oPlaySection = new Section("NGÔN NGỮ");
//        oPlaySection.addSectionItem(701, "English", "");
//        oPlaySection.addSectionItem(702, "Tiếng Việt", "");
//
//        sectionList.add(oDemoSection);
//        sectionList.add(oGeneralSection);
//        sectionList.add(oSettingSection);
//        sectionList.add(oMoreSection);
//        sectionList.add(oFunSection);
//        sectionList.add(oConnectSection);
//        sectionList.add(oPlaySection);
//
//        return sectionList;
//    }

    public static int getSoftButtonsBarSizePort(Activity activity) {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    public static int getDrawableIdFromString(String name, Context context) {
        return context.getResources().getIdentifier("picture0001", "drawable", context.getPackageName());
    }


//    public static void setupItem(final View view, final LibraryObject libraryObject) {
//
//        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
//        img.setImageResource(libraryObject.getRes());
//    }

    public static void showLog(String TAG, String veryLongString) {
        int maxLogSize = 1000;
        for(int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            Log.v(TAG, veryLongString.substring(start, end));
        }
    }

    public static class LibraryObject {

        private String mTitle;
        private int mRes;

        public LibraryObject(final int res, final String title) {
            mRes = res;
            mTitle = title;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(final String title) {
            mTitle = title;
        }

        public int getRes() {
            return mRes;
        }

        public void setRes(final int res) {
            mRes = res;
        }
    }

    public static class QRCodePayment {
        public static PushPaymentData parseQRCode(String code) {
            System.out.println("parseQRCode-->code:"+code);
            PushPaymentData qrcode = null;
            try {
                qrcode = Parser.parseWithoutTagValidation(code);
                qrcode.validate();
                System.out.println("parseQRCode-> dumpdata:"+qrcode.dumpData());
            } catch (ConflictiveTagException e) {
                System.out.println("ConflictiveTagException : " + e);
            } catch (InvalidTagValueException e) {
                System.out.println("InvalidTagValueException : " + e);
            } catch (MissingTagException e) {
                System.out.println("MissingTagException : " + e);
            } catch (UnknownTagException e) {
                System.out.println("UnknownTagException : " + e);
            } catch (FormatException e) {
                System.out.println("FormatException : " + e);
            }

            return qrcode;
        }
    }
}
