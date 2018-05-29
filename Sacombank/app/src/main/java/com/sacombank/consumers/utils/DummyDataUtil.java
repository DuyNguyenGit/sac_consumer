package com.sacombank.consumers.utils;

import android.content.Context;
import com.sacombank.consumers.models.home.Category;
import com.sacombank.consumers.models.home.CategoryReport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DUY on 7/14/2017.
 */

public class DummyDataUtil {

    public static List<Category> getCategories() {
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category();
        for (int i = 0; i < 10; i++) {
            categoryList.add(category);
        }

        return categoryList;
    }

    public static List<CategoryReport> getCategoryReports() {
        List<CategoryReport> categoryList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            CategoryReport categoryReport = new CategoryReport();
            categoryReport.setTitle("Chương trình KM " + (i + 1));
            categoryList.add(categoryReport);
        }

        return categoryList;
    }

    public static String getDummyDataFromAssets(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

}
