package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseSeeder {

    public static void seedAllData(SQLiteDatabase db) {
        seedNhanVien(db);
        seedCompany(db);
        seedContact(db);
        seedLead(db);
        seedOpportunity(db);
        seedProduct(db);
        // ... thêm các seed khác
    }

    private static void seedLead(SQLiteDatabase db) {
    }

    private static void seedNhanVien(SQLiteDatabase db) {
        String[] names = {"Nguyễn Văn A", "Trần Thị B", "Lê Văn C", "Phạm Thị D"};
        String[] emails = {"nva@company.com", "ttb@company.com", "lvc@company.com", "ptd@company.com"};
        String[] phones = {"0987654321", "0912345678", "0934567890", "0976543210"};
        String[] positions = {"Sales Manager", "Sales Executive", "Marketing", "Account Manager"};

        for (int i = 0; i < names.length; i++) {
            ContentValues values = new ContentValues();
            values.put("HOTEN", names[i]);
            values.put("EMAIL", emails[i]);
            values.put("DIENTHOAI", phones[i]);
            values.put("CHUCVU", positions[i]);
            values.put("BOPHAN", "Kinh doanh");
            values.put("TAIKHOAN", emails[i].split("@")[0]);
            values.put("MATKHAU", "123456");
            values.put("ROLE", i == 0 ? "ADMIN" : "USER");
            values.put("TRANGTHAI", "Đang làm việc");
            values.put("NGAYVAOLAM", "01/01/2023");
            values.put("MOTA", "Nhân viên " + positions[i]);

            db.insert("NHANVIEN", null, values);
        }
    }

    private static void seedCompany(SQLiteDatabase db) {
        String[] companies = {
                "Công ty TNHH ABC",
                "Tập đoàn XYZ",
                "Công ty CP DEF",
                "Doanh nghiệp GHI"
        };
        String[] industries = {"CNTT", "Bất động sản", "Tài chính", "Sản xuất"};

        for (int i = 0; i < companies.length; i++) {
            ContentValues values = new ContentValues();
            values.put("TENCONGTY", companies[i]);
            values.put("NGANHNGHE", industries[i]);
            values.put("DIENTHOAI", "028" + (i + 1) + "2345678");
            values.put("EMAIL", "info@" + companies[i].toLowerCase().replace(" ", "") + ".com");
            values.put("DIACHI", "Quận " + (i + 1) + ", TP.HCM");
            values.put("TRANGTHAI", "Đang hợp tác");
            values.put("NGAYTHANHLAP", "201" + i + "-01-01");
            values.put("GIAOCHO", (i % 4) + 1); // Giao cho nhân viên ID 1-4

            db.insert("COMPANY", null, values);
        }
    }

    private static void seedContact(SQLiteDatabase db) {
        String[] contacts = {
                "Lê Minh Anh", "Trần Quốc Bảo", "Nguyễn Thị Cẩm", "Phạm Đức Duy"
        };
        String[] positions = {"Giám đốc", "Trưởng phòng", "Chuyên viên", "Kế toán trưởng"};

        for (int i = 0; i < contacts.length; i++) {
            ContentValues values = new ContentValues();
            values.put("HOTEN", contacts[i]);
            values.put("DANHXUNG", i < 2 ? "Anh" : "Chị");
            values.put("DIENTHOAI", "09" + (i + 1) + "8765432");
            values.put("EMAIL", contacts[i].toLowerCase().replace(" ", "") + "@email.com");
            values.put("CHUCVU", positions[i]);
            values.put("CONGTY", (i % 4) + 1); // Liên kết với company ID
            values.put("GIAOCHO", (i % 4) + 1); // Giao cho nhân viên ID
            values.put("NGAYTAO", "2024-01-" + (10 + i));
            values.put("CUOCGOI", i + 2);
            values.put("CUOCHOP", i + 1);

            db.insert("CONTACT", null, values);
        }
    }

    private static void seedOpportunity(SQLiteDatabase db) {
        // Dữ liệu cơ hội mẫu
        Object[][] opportunities = {
                {"Phần mềm CloudWork", 14875000, "17/07/2024", "Thương lượng đàm phán", "Trao đổi (1)", 1, 1, 1},
                {"Ứng dụng CRM Mobile", 22300000, "12/08/2024", "Phân tích nhận thức", "Trao đổi (3)", 2, 2, 2},
                {"Website Quản lý Dự án", 9700000, "05/09/2024", "Đề xuất/ Báo giá", "Trao đổi (3)", 3, 3, 3},
                {"Hệ thống ERP", 45000000, "20/09/2024", "Đã báo giá", "Chờ phản hồi", 4, 4, 4},
                {"App Mobile bán hàng", 12800000, "15/10/2024", "Mới", "Liên hệ lần đầu", 1, 1, 1}
        };

        for (Object[] opp : opportunities) {
            ContentValues values = new ContentValues();
            values.put("TENCOHOI", (String) opp[0]);
            values.put("GIATRI", (double) opp[1]);
            values.put("NGAYTAO", (String) opp[2]);
            values.put("BUOCBANHANG", (String) opp[3]);
            values.put("MOTA", (String) opp[4]);
            values.put("CONGTY", (int) opp[5]);
            values.put("NGUOILIENHE", (int) opp[6]);
            values.put("GIAOCHO", (int) opp[7]);

            db.insert("COHOI", null, values);
        }
    }

    private static void seedProduct(SQLiteDatabase db) {
        String[] products = {"Phần mềm CRM", "Website thương mại", "App Mobile", "Hệ thống ERP"};
        double[] prices = {15000000, 25000000, 30000000, 50000000};

        for (int i = 0; i < products.length; i++) {
            ContentValues values = new ContentValues();
            values.put("TEN", products[i]);
            values.put("MOTA", "Giải pháp " + products[i] + " chuyên nghiệp");
            values.put("DONGIA", prices[i]);
            values.put("TRANGTHAI", "Đang bán");
            values.put("NGAYTAO", "2024-01-01");
            values.put("NGUOITAO", 1);

            db.insert("SANPHAM", null, values);
        }
    }

    // Thêm các seed methods khác...
}