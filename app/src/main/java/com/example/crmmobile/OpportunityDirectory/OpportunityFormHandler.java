
package com.example.crmmobile.OpportunityDirectory;

import android.text.TextUtils;

import java.text.ParseException;
import java.util.List;

public class OpportunityFormHandler {

    public Opportunity createFromForm(
            String mode,
            Opportunity existing,
            String title,
            String priceStr,
            String stage,
            String date1,
            String date2,
            String desc,
            int companyId,
            int contactId,
            int managementId
    ) throws ParseException {

        int id = (MODE_UPDATE.equals(mode) && existing != null)
                ? existing.getId()
                : 0;

        double price = parsePrice(priceStr);

        return new Opportunity(
                id,
                title,
                companyId,
                contactId,
                price,
                stage,
                date1,
                date2,
                desc,
                managementId,
                0,
                0
        );
    }

    public boolean validateTitle(String title) {
        return !TextUtils.isEmpty(title);
    }

    public double parsePrice(String raw) {
        try {
            String cleaned = raw.replaceAll("[^\\d.]", "");
            if (cleaned.isEmpty()) return 0;
            return Double.parseDouble(cleaned);
        } catch (Exception e) {
            return 0;
        }
    }

    /** Giúp Fragment tìm text theo ID khi populate form */
    public String findCompanyName(int id, List<Company> list) {
        for (Company c : list) if (c.getId() == id) return c.getName();
        return "";
    }

    public String findContactName(int id, List<Contact> list) {
        for (Contact c : list) if (c.getId() == id) return c.getFull_name();
        return "";
    }

    public String findEmployeeName(int id, List<Employee> list) {
        for (Employee e : list) if (e.getId() == id) return e.getName();
        return "";
    }

    public static final String MODE_CREATE = "create";
    public static final String MODE_UPDATE = "update";
}
