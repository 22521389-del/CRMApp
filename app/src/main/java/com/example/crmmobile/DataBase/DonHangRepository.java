package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crmmobile.OrderDirectory.DonHang;
import com.example.crmmobile.OrderDirectory.Order;

import java.util.ArrayList;
import java.util.List;

public class DonHangRepository {

    private final DBCRMHandler dbHelper;

    // Tên bảng + cột đúng như trong DBCRMHandler
    private static final String TABLE_NAME       = "DONHANG";
    private static final String COL_ID           = "ID";
    private static final String COL_TENDONHANG   = "TENDONHANG";
    private static final String COL_CONGTY       = "CONGTY";
    private static final String COL_NGUOILIENHE  = "NGUOILIENHE";
    private static final String COL_COHOI        = "COHOI";
    private static final String COL_BAOGIA       = "BAOGIA";
    private static final String COL_TINHTRANG    = "TINHTRANG";
    private static final String COL_NGAYDATHANG  = "NGAYDATHANG";
    private static final String COL_NGAYNHANHANG = "NGAYNHANHANG";
    private static final String COL_SANPHAM      = "SANPHAM";
    private static final String COL_SOLUONG      = "SOLUONG";
    private static final String COL_DONGIA       = "DONGIA";
    private static final String COL_TONGTIEN     = "TONGTIEN";
    private static final String COL_MOTA         = "MOTA";
    private static final String COL_GIAOCHO      = "GIAOCHO";

    public DonHangRepository(Context context) {
        dbHelper = new DBCRMHandler(context.getApplicationContext());
    }

    // ===== CREATE =====
    public long insert(DonHang donHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TENDONHANG,   donHang.getTenDonHang());
        values.put(COL_CONGTY,       donHang.getCongTyId());
        values.put(COL_NGUOILIENHE,  donHang.getNguoiLienHeId());
        values.put(COL_COHOI,        donHang.getCoHoiId());
        values.put(COL_BAOGIA,       donHang.getBaoGiaId());
        values.put(COL_TINHTRANG,    donHang.getTinhTrang());
        values.put(COL_NGAYDATHANG,  donHang.getNgayDatHang());
        values.put(COL_NGAYNHANHANG, donHang.getNgayNhanHang());
        values.put(COL_SANPHAM,      donHang.getSanPham());
        values.put(COL_SOLUONG,      donHang.getSoLuong());
        values.put(COL_DONGIA,       donHang.getDonGia());
        values.put(COL_TONGTIEN,     donHang.getTongTien());
        values.put(COL_MOTA,         donHang.getMoTa());
        values.put(COL_GIAOCHO,      donHang.getGiaoChoId());

        return db.insert(TABLE_NAME, null, values);
    }

    // ===== READ: tất cả đơn hàng =====
    public List<DonHang> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<DonHang> result = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null, null,
                null, null,
                COL_ID + " DESC"
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        result.add(fromCursor(cursor));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return result;
    }

    // ===== READ: 1 đơn theo ID =====
    public DonHang getById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        DonHang donHang = null;

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COL_ID + " = ?",
                new String[]{ String.valueOf(id) },
                null, null, null
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    donHang = fromCursor(cursor);
                }
            } finally {
                cursor.close();
            }
        }
        return donHang;
    }

    // ===== UPDATE =====
    public int update(DonHang donHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TENDONHANG,   donHang.getTenDonHang());
        values.put(COL_CONGTY,       donHang.getCongTyId());
        values.put(COL_NGUOILIENHE,  donHang.getNguoiLienHeId());
        values.put(COL_COHOI,        donHang.getCoHoiId());
        values.put(COL_BAOGIA,       donHang.getBaoGiaId());
        values.put(COL_TINHTRANG,    donHang.getTinhTrang());
        values.put(COL_NGAYDATHANG,  donHang.getNgayDatHang());
        values.put(COL_NGAYNHANHANG, donHang.getNgayNhanHang());
        values.put(COL_SANPHAM,      donHang.getSanPham());
        values.put(COL_SOLUONG,      donHang.getSoLuong());
        values.put(COL_DONGIA,       donHang.getDonGia());
        values.put(COL_TONGTIEN,     donHang.getTongTien());
        values.put(COL_MOTA,         donHang.getMoTa());
        values.put(COL_GIAOCHO,      donHang.getGiaoChoId());

        return db.update(
                TABLE_NAME,
                values,
                COL_ID + " = ?",
                new String[]{ String.valueOf(donHang.getId()) }
        );
    }

    // ===== DELETE =====
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                TABLE_NAME,
                COL_ID + " = ?",
                new String[]{ String.valueOf(id) }
        );
    }

    // ===== Map Cursor -> DonHang =====
    private DonHang fromCursor(Cursor c) {
        DonHang dh = new DonHang();
        dh.setId(c.getInt(c.getColumnIndexOrThrow(COL_ID)));
        dh.setTenDonHang(c.getString(c.getColumnIndexOrThrow(COL_TENDONHANG)));
        dh.setCongTyId(c.getInt(c.getColumnIndexOrThrow(COL_CONGTY)));
        dh.setNguoiLienHeId(c.getInt(c.getColumnIndexOrThrow(COL_NGUOILIENHE)));
        dh.setCoHoiId(c.getInt(c.getColumnIndexOrThrow(COL_COHOI)));
        dh.setBaoGiaId(c.getInt(c.getColumnIndexOrThrow(COL_BAOGIA)));
        dh.setTinhTrang(c.getString(c.getColumnIndexOrThrow(COL_TINHTRANG)));
        dh.setNgayDatHang(c.getString(c.getColumnIndexOrThrow(COL_NGAYDATHANG)));
        dh.setNgayNhanHang(c.getString(c.getColumnIndexOrThrow(COL_NGAYNHANHANG)));
        dh.setSanPham(c.getString(c.getColumnIndexOrThrow(COL_SANPHAM)));
        dh.setSoLuong(c.getInt(c.getColumnIndexOrThrow(COL_SOLUONG)));
        dh.setDonGia(c.getLong(c.getColumnIndexOrThrow(COL_DONGIA)));
        dh.setTongTien(c.getLong(c.getColumnIndexOrThrow(COL_TONGTIEN)));
        dh.setMoTa(c.getString(c.getColumnIndexOrThrow(COL_MOTA)));
        dh.setGiaoChoId(c.getInt(c.getColumnIndexOrThrow(COL_GIAOCHO)));
        return dh;
    }

    // ===== Helper: convert sang List<Order> cho màn hình list =====
    public List<Order> getOrdersForList() {
        List<DonHang> donHangs = getAll();
        List<Order> orders = new ArrayList<>();

        for (DonHang dh : donHangs) {
            String orderCode = dh.getTenDonHang();
            //String company   = "Công ty #" + dh.getCongTyId(); // sau này map từ CompanyRepository
            String company   = dh.getMoTa();  // tạm dùng mô tả để show tên công ty
            String price     = String.valueOf(dh.getTongTien()) + " đ";
            String date      = dh.getNgayDatHang();
            String status    = dh.getTinhTrang();
            String orderType = ""; // có thể để trống hoặc suy ra từ TINHTRANG

            // Order sẽ được chỉnh thêm field id ở bước dưới
            Order o = new Order(dh.getId(), orderCode, company, price, date, status, orderType);
            orders.add(o);
        }
        return orders;
    }
}
