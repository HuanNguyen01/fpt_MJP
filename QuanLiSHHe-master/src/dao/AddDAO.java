package dao;
import connectDB.ConnectDB;
import data.DangKyTre;
import data.TreEm;
import utility.InputUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static utility.InputUtils.*;

public class AddDAO {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<DangKyTre> dangKyTres = new ArrayList<>();
    static ArrayList<TreEm> dste = new ArrayList();
    static InputUtils check = new InputUtils();
    static ConnectDB conn = new ConnectDB();
    static Connection con = conn.getConnectDB();
    static PreparedStatement pst = null;
    public static void themTreEm(Scanner sc) {
        try {
            String hoTenTre = check.nhapHoTenTre(sc);
            java.sql.Date ngaySinh = (java.sql.Date) check.nhapNgaySinh(sc);
            String gioiTinh = check.nhapGioiTinh(sc);
            int maPH = check.nhapMaPH(sc);
            TreEm te = new TreEm(hoTenTre, ngaySinh.toLocalDate(), gioiTinh, maPH);
            dste.add(te);
            String sql = "INSERT INTO TREEM (HoTenTre, NgaySinh, GioiTinh, MaPH) " + " VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, hoTenTre);
            pst.setDate(2, new java.sql.Date(ngaySinh.getTime()));
            pst.setString(3, gioiTinh);
            pst.setInt(4, maPH);
            pst.executeUpdate();
            System.out.println("Thêm thông tin trẻ em thành công");
        } catch (SQLException e) {
            System.out.println("Thêm thông tin trẻ em không thành công: " + e.getMessage());
        }
    }
    public void themPhuHuynh(Scanner sc){
        String hoTenPH = null;
        boolean isValid;

        do {
            System.out.println("Họ và tên phụ huynh:");
            hoTenPH = sc.nextLine();
            isValid = InputUtils.isValidHoTenPH(hoTenPH);

            if (!isValid) {
                System.out.println("Họ tên phụ huynh không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        String diaChi = null;

        do {
            System.out.println(" Địa chỉ");
            diaChi = sc.nextLine();
            isValid = InputUtils.isValidDiaChi(diaChi);

            if (!isValid) {
                System.out.println("Địa chỉ không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);
        String soDT = null;
        do {
            System.out.println("Số điện thoại");
            soDT = sc.nextLine();
            isValid = InputUtils.isValidSDT(soDT);

            if (!isValid) {
                System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        String email = null;
        do {
            System.out.println("Nhập địa chỉ email:");
            email = sc.nextLine();
            isValid = InputUtils.isValidEmail(email);

            if (!isValid) {
                System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        try {
            con = ConnectDB.getConnectDB();
            String sql = "INSERT INTO PHUHUYNH (HoTenPH,DiaChi,SoDT,Email) VALUES(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, hoTenPH);
            pst.setString(2, diaChi);
            pst.setString(3, soDT);
            pst.setString(4, email);
            int a = pst.executeUpdate();
            System.out.println(a + " phụ huynh đã được thêm vào thành công ");
            con.close();
        } catch (SQLException e) {
            System.out.println(" số điện thoại và email đã tồn tại ");
        }

    }

    public static void themGV() {
        try {
            con = ConnectDB.getConnectDB();
            Scanner sc = new Scanner(System.in);
            String name = check.getValidName(sc);
            String sql = "INSERT INTO GiaoVien (HoTenGV) VALUES (?)";

            pst = con.prepareStatement(sql);
            pst.setString(1, name);


            int check = pst.executeUpdate();
            if (check > 0) System.out.println("Thêm dữ liệu thành công");
            else System.out.println("Thêm dữ liệu thất bại");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void themMonHoc() {
        try {
            con = ConnectDB.getConnectDB();
            Scanner sc = new Scanner(System.in);
            String name = check.getValidName(sc);
            String sql = "INSERT INTO MonHoc (TenMH) VALUES (?)";

            pst = con.prepareStatement(sql);
            pst.setString(1, name);


            int check = pst.executeUpdate();
            if (check > 0) System.out.println("Thêm dữ liệu thành công");
            else System.out.println("Thêm dữ liệu thất bại");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void themThoiGianHoc() {
        System.out.println("\n==========Thêm thời gian học==========");
        try {
            con = ConnectDB.getConnectDB();
            System.out.println("Nhập ngày học: ");
            String NgayHoc = check.getValidNgayHoc(sc);
            System.out.println("Nhập giờ học: ");
            String GioHoc = check.getValidGioHoc(sc);

            String sql = "INSERT INTO THOIGIANHOC (NgayHoc,GioHoc) VALUES (?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, NgayHoc);
            pst.setString(2, GioHoc);
            pst.executeUpdate();
            System.out.println("thêm thời gian học thành công");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void themThongTinLop() {
        System.out.println("\n========thêm thông tin lớp học===========");
        String sql = "INSERT INTO LOPHOC (MaMH, MaGV, MaTGH, PhongHoc, SoBuoi, NgayKhaiGiang, HocPhi) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int monHocID = 0;
        int giaoVienID = 0;
        int thoiGianHocID = 0;
        String phongHoc = "";
        int soBuoi = 0;
        String ngayKhaiGiangString = "";
        int hocPhi = 0;

        try {
            con = ConnectDB.getConnectDB();
            PreparedStatement pst = con.prepareStatement(sql);

            System.out.println("Nhâp thông tin cần thêm: ");

            monHocID = InputUtils.getCheckMonHocID(monHocID);
            giaoVienID = InputUtils.getCheckGiaoVienID(giaoVienID);
            thoiGianHocID = InputUtils.getCheckThoiGianHocID(thoiGianHocID);
            phongHoc = InputUtils.getCheckPhongHoc();
            soBuoi = InputUtils.getCheckSoBuoi(soBuoi);
            ngayKhaiGiangString = InputUtils.getCheckNgayKhaiGiang();
            hocPhi = InputUtils.getCheckHocPhi(hocPhi);

            pst.setInt(1, monHocID);
            pst.setInt(2, giaoVienID);
            pst.setInt(3, thoiGianHocID);
            pst.setString(4, phongHoc);
            pst.setInt(5, soBuoi);
            pst.setString(6, ngayKhaiGiangString);
            pst.setInt(7, hocPhi);
            // Thực thi câu truy vấn
            pst.executeUpdate();
            System.out.println("Đã thêm thành công");
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Vui lòng nhập lại");
        } finally { // Đóng kết nối sau khi hoàn thành hoặc gặp lỗi
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Đống kết nối thất bại");
            }
        }

    }

    public static void themThongTinDangKy(){
        System.out.println("\n==========Thêm thông tin đăng ký==========");
        boolean isValidInput = false;
        do {
            try {
                System.out.println("Nhập mã phụ huynh: ");
                int MaPH = Integer.parseInt(sc.nextLine());
                if (!InputUtils.validateMa(MaPH)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }
                if (!kiemTraMaPHTonTai(MaPH)) {
                    System.out.println("Mã Phụ huynh không tồn tại. Vui lòng nhập lại.");
                    continue;
                }


                System.out.println("Nhập mã trẻ em: ");
                int MaTre = Integer.parseInt(sc.nextLine());
                if (!InputUtils.validateMa(MaTre)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }
                if (!kiemTraMaTreTonTai(MaTre)){
                    System.out.println("Mã trẻ em không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("Nhập mã lớp: ");
                int MaLH = Integer.parseInt(sc.nextLine());
                if (!InputUtils.validateMa(MaLH)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }
                if (!kiemTraMaLHTonTai(MaLH)){
                    System.out.println("Mã lớp học không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("Nhập ngày đăng ký (yyyy-MM-dd):");
                String ngayDangKyStr = sc.nextLine();
                if (!InputUtils.validateDate(ngayDangKyStr)) {
                    System.out.println("Ngày đăng ký không hợp lệ.");
                    continue;
                }

                System.out.println("Nhập trạng thái đăng ký (ấn Enter để bỏ qua và sử dụng trạng thái mặc định 'Chưa Duyệt'):");
                String trangThaiInput = sc.nextLine();
                String trangThai = trangThaiInput.isEmpty() ? "Chưa Duyệt" : trangThaiInput;
                if (!InputUtils.validateTrangThai(trangThai)) {
                    System.out.println("Trạng thái đăng ký không hợp lệ.");
                    continue;
                }

                DangKyTre dangKyTre = new DangKyTre(MaPH, MaTre, MaLH, Date.valueOf(ngayDangKyStr), trangThai);
                dangKyTres.add(dangKyTre);
                themDangKyTreVaoDatabase(dangKyTre);

                isValidInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: ");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } while (!isValidInput);
    }
    public static void themDangKyTreVaoDatabase(DangKyTre dangKyTre){
        try {
            con = ConnectDB.getConnectDB();
            String sql = "INSERT INTO DANGKYTRE(MaPH,MaTre,MaLH,NgayDangKy,TrangThai) VALUES (?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, dangKyTre.getMaLH());
            pst.setInt(2,dangKyTre.getMaTre());
            pst.setInt(3,dangKyTre.getMaLH());
            pst.setDate(4,dangKyTre.getNgayDangKy());
            pst.setString(5,dangKyTre.getTrangThai());
            pst.executeUpdate();
            System.out.println("đã thêm thông tin đăng ký thành công");
            con.close();
        } catch (SQLException e) {
            System.out.println("vui lòng nhập lại");
        }
    }




}


