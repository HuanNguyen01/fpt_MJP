package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import connectDB.ConnectDB;
import utility.InputUtils;
import static utility.InputUtils.*;


public class EditDAO {
    static Scanner sc = new Scanner(System.in);
    static InputUtils check = new InputUtils();
    static ConnectDB conn = new ConnectDB();
    static Connection con = conn.getConnectDB();
    static PreparedStatement pst = null;
    public void suaTreEm(Scanner sc2) {
        try {
            con = ConnectDB.getConnectDB();
            int maSua = 0;
            boolean kt = false;
            while (!kt) {
                System.out.println("Nhập vào mã của trẻ cần sửa");
                maSua = Integer.parseInt(sc.nextLine());
                if (check.isExists("TREEM", "MaTre", String.valueOf(maSua)) == true) {
                    kt = true;
                } else {
                    System.out.println("Không tìm thấy mã trẻ!");
                }
            }
            System.out.println("Nhập vào họ tên của trẻ");
            String tenSua = check.nhapHoTenTre(sc);
            java.sql.Date ngaySinhSua = (Date) check.nhapNgaySinh(sc);
            String gioiTinhSua = check.nhapGioiTinh(sc);
            int maPHSua = check.nhapMaPH(sc);
            String sql = "UPDATE TREEM SET HoTenTre = ?, NgaySinh = ?, GioiTinh = ?, MaPH = ? WHERE MaTre = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, tenSua);
            pst.setDate(2, ngaySinhSua);
            pst.setString(3, gioiTinhSua);
            pst.setInt(4, maPHSua);
            pst.setInt(5, maSua);
            pst.executeUpdate();
            System.out.println("Đã sửa thông tin trẻ em thành công");
            System.out.println(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Sửa thông tin trẻ em không thành công: " + e.getMessage());
        }
    }
    public static void suaPhuHuynh() {
        System.out.println(" nhập mã cần sửa ");
        int maPH = 0;
        boolean t = true;
        while (t) {
            maPH = Integer.parseInt(sc.nextLine());
            try {
                if (check(maPH)) {
                    t = false;
                } else {
                    System.out.println(" nhập  lại mã cần sửa ");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
            String sql = "UPDATE  PHUHUYNH SET HoTenPH = ?,DiaChi = ?,SoDT =?,Email = ? WHERE MaPH=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, hoTenPH);
            pst.setString(2, diaChi);
            pst.setString(3, soDT);
            pst.setString(4, email);
            pst.setInt(5, maPH);
            int a = pst.executeUpdate();
            System.out.println(a + " phụ huynh đã được sửa vào thành công ");

            con.close();
        } catch (SQLException e) {
            System.out.println(" lỗi " + e.getMessage());
        }

    }

    public  void editGiaoVien() {
        try {
            con = ConnectDB.getConnectDB();
            Scanner sc = new Scanner(System.in);
            int maGV = check.getValidMa(sc);
            if (check.isExists("GiaoVien", "MaGV", String.valueOf(maGV)) == true) {
                String tenGV = check.getValidName(sc);
                String sql = "update GiaoVien set HoTenGV = ? where MaGV = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, tenGV);
                pst.setInt(2, maGV);
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Cập nhật giáo viên thành công!");
                } else {
                    System.out.println("Không tìm thấy mã giáo viên!!!!!!");
                }
            } else {
                System.out.println("Không tìm thấy mã giáo viên!");
                editGiaoVien();
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Cập nhật giáo viên thất bại!");
        }
    }

    public  void suaMonHoc() {
        try {
            con = ConnectDB.getConnectDB();
            Scanner sc = new Scanner(System.in);
            int maMH = check.getValidMa(sc);
            if (check.isExists("MonHoc", "MaMH", String.valueOf(maMH)) == true) {
                String tenMH = check.getValidName(sc);
                String sql = "update MonHoc set TenMH = ? where MaMH = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, tenMH);
                pst.setInt(2, maMH);
                pst.executeUpdate();
                if (pst.executeUpdate() > 0) {
                    System.out.println("Cập nhật môn học thành công!");
                } else {
                    System.out.println("Không tìm thấy mã môn học!");
                }
            } else {
                System.out.println("Không tìm thấy mã môn học!");
                suaMonHoc();
            }
        } catch (SQLException e) {
            System.out.println("Cập nhật môn học thất bại!");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public  void suaThoiGianHoc(){
        System.out.println("\n=========Sửa thời gian học=========");
        boolean isValidInput = false;
        do {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhâp mã thời gian học cần sửa");
                int MaTGH = Integer.parseInt(sc.nextLine());
                if (!InputUtils.validateMa(MaTGH)) {
                    System.out.println("mã đăng ký không hợp lệ");
                    continue;
                }
                if (!kiemTraMaTGH(MaTGH)) {
                    System.out.println("Mã đăng ký không tồn tại. Vui lòng nhập lại.");
                    continue;
                }
                System.out.println("Nhập ngày học mới: ");
                String NgayHoc = check.getValidNgayHoc(sc);
                System.out.println("Nhập giờ học mới: ");
                String GioHoc = check.getValidGioHoc(sc);

                String sql = "UPDATE THOIGIANHOC SET NgayHoc=? , GioHoc=? WHERE MaTGH=?";
                pst = con.prepareStatement(sql);
                pst.setString(1,NgayHoc);
                pst.setString(2,GioHoc);
                pst.setInt(3,MaTGH);
                pst.executeUpdate();
                System.out.println("Đã sửa thành công");
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            isValidInput = true;
        }while (!isValidInput);
    }
    public void suaThongTinLopHoc() {
        con = ConnectDB.getConnectDB();
        String sql = "UPDATE LOPHOC SET MaMH = ?, MaGV = ?, MaTGH = ?, PhongHoc = ?,"
                + "SoBuoi = ?, NgayKhaiGiang = ?, HocPhi = ? WHERE MaLH = ?";
        Connection con = null;
        int lopHocID = 0;
        int monHocID = 0;
        int giaoVienID = 0;
        int thoiGianHocID = 0;
        String phongHoc = "";
        int soBuoi = 0;
        String ngayKhaiGiangString = "";
        int hocPhi = 0;
        try {
            PreparedStatement pst = con.prepareStatement(sql);

            System.out.println("Nhâp thông tin cần sửa: ");
            lopHocID = InputUtils.getCheckLopHocID(lopHocID);
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
            pst.setInt(8, lopHocID);
            // Thực thi câu truy vấn
            pst.executeUpdate();
            System.out.println("Đã sửa thành công");
        } catch (SQLException e) {
            System.out.println("Vui lòng nhập lại");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Đống kết nối thất bại");
            }
        }
    }
    public  void suaThongTinDangKy()  {
        System.out.println("\n===========Sửa thông tin đăng ký==========");
        boolean isValidInput = false;
        do {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập MaDK cần sửa: ");
                int MaDK = Integer.parseInt(sc.nextLine());
                if (!InputUtils.validateMa(MaDK)){
                    System.out.println("mã đăng ký không hợp lệ");
                    continue;
                }
                if (!kiemTraMaDKTonTai(MaDK)) {
                    System.out.println("Mã đăng ký không tồn tại. Vui lòng nhập lại.");
                    continue;
                }


                System.out.println("Nhập MaPH mới: ");
                int MaPH = Integer.parseInt(sc.nextLine());
                if (!InputUtils.validateMa(MaPH)){
                    System.out.println("mã phụ huynh không hợp lệ");
                    continue;
                }
                if (!kiemTraMaPHTonTai(MaPH)) {
                    System.out.println("Mã Phụ huynh không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("Nhập MaTre mới: ");
                int MaTre = Integer.parseInt(sc.nextLine());
                if (!InputUtils.validateMa(MaTre)){
                    System.out.println("mã trẻ em không hợp lệ");
                    continue;
                }
                if (!kiemTraMaTreTonTai(MaTre)){
                    System.out.println("Mã trẻ em không tồn tại. Vui lòng nhập lại.");
                    continue;
                }


                System.out.println("nhập MaLH mới: ");
                int MaLH = Integer.parseInt(sc.nextLine());
                if (!InputUtils.validateMa(MaLH)){
                    System.out.println("mã lớp học không hợp lệ");
                    continue;
                }
                if (!kiemTraMaLHTonTai(MaLH)){
                    System.out.println("Mã lớp học không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("nhập NgayDangKy mới (yyyy-MM-dd): ");
                String NgayDangKyStr = sc.nextLine();
                if (!InputUtils.validateDate(NgayDangKyStr)) {
                    System.out.println("Ngày đăng ký không hợp lệ.");
                    continue;
                }

                System.out.println("nhập TrangThai mới: ");
                String TrangThai = sc.nextLine();
                if (!InputUtils.validateTrangThai(TrangThai)) {
                    System.out.println("Trạng thái đăng ký không hợp lệ.");
                    continue;
                }

                String sql = "UPDATE DANGKYTRE SET MaPH=?, MaTre=?, MaLH=?, NgayDangKy=?, TrangThai=? WHERE MaDK=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, MaPH);
                pst.setInt(2, MaTre);
                pst.setInt(3, MaLH);
                pst.setDate(4, Date.valueOf(NgayDangKyStr));
                pst.setString(5, TrangThai);
                pst.setInt(6, MaDK);
                pst.executeUpdate();
                System.out.println("Thông tin đăng ký đã sửa thành công");
                con.close();
            } catch (NumberFormatException e) {
                System.out.println("lỗi: " );
            } catch (SQLException e) {
                System.out.println("vui lòng nhập lại: ");
            }
            isValidInput = true;
        }while (!isValidInput);
    }

}
