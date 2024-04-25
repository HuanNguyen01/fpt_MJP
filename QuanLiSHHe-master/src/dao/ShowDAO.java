package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import connectDB.ConnectDB;
import controller.PhuHuynhController;
import utility.InputUtils;

import static utility.InputUtils.*;

public class ShowDAO {
    static Scanner sc = new Scanner(System.in);
    static ConnectDB conn = new ConnectDB();
    static Connection con = conn.getConnectDB();
    static Statement st = null;
    static PreparedStatement pst = null;
    public void hienThiTreEm() {
        try {
            con = ConnectDB.getConnectDB();
            String sql = "SELECT * FROM TREEM";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            System.out.printf("%-10s %-20s %-20s %-15s %-10s\n", "Mã Trẻ", "Họ Tên Trẻ", "Ngày Sinh", " Giới Tính",
                    "Mã Phụ Huynh");
            while (rs.next()) {
                int MaTre = rs.getInt("MaTre");
                String HoTenTre = rs.getString("HoTenTre");
                Date NgaySinh = rs.getDate("NgaySinh");
                String GioiTinh = rs.getString("GioiTinh");
                int MaPH = rs.getInt("MaPH");
                System.out.printf("%-10d %-20s %-20s %-15s %-10d\n", MaTre, HoTenTre,
                        new SimpleDateFormat("yyyy-MM-dd").format(NgaySinh), GioiTinh, MaPH);
            }
            System.out.println(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Xem thông tin trẻ em không thành công: " + e.getMessage());
        }
    }

    public void hienThiPhuHuynh() {
        System.out.println("\n==========Danh sách phụ huynh==========");
        System.out.println(
                "+---------+-------------------+---------------------+-------------------------+----------------------------+");
        System.out.println(
                "|   MaPH  |      HoTenPH      |        DiaChi       |           SoDT          |            Email           |");
        System.out.println(
                "+---------+-------------------+---------------------+-------------------------+----------------------------+");
        try {
            con = ConnectDB.getConnectDB();
            String sql = "SELECT* FROM PHUHUYNH";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int count = 0;
            while (rs.next()) {
                int maPH = rs.getInt("MaPH");
                String hoTenPH = rs.getString("HoTenPH");
                String diaChi = rs.getString("DiaChi");
                String soDT = rs.getString("SoDT");
                String email = rs.getString("Email");
                count++;
                System.out.printf("| %-7s | %-17s | %-19s | %-23s | %-26s |\n", maPH, hoTenPH, diaChi, soDT, email);
            }
            if (count == 0) {
                System.out.println(" Chưa có dữ liệu ");
            }

            con.close();
        } catch (SQLException e) {
            System.out.println(" lỗi " + e.getMessage());

        }
        System.out.println(
                "+---------+-------------------+---------------------+-------------------------+----------------------------+");


    }
    public void hienThiGiaoVien() {
        try {
            con = ConnectDB.getConnectDB();
            String sql = "select * from GiaoVIen";

            st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            System.out.println("--------------------------------------------------------------");
            System.out.printf("| %-15s | %-40s |\n",   "Mã giáo viên", "Họ và tên giáo viên   ");
            System.out.println("|-----------------+------------------------------------------|");

            while(rs.next()) {

                System.out.printf("| %-15s | %-40s |\n", rs.getString(1), rs.getString(2));
            }

            System.out.println("--------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Lỗi:");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void hienThiMonHoc() {
        try {
            con = ConnectDB.getConnectDB();
            String sql = "select * from MonHoc";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("--------------------------------------------------------------");
            System.out.printf("| %-15s | %-40s |\n",         "Mã môn học", "Tên môn học       ");
            System.out.println("|-----------------+------------------------------------------|");

            while(rs.next()) {
                System.out.printf("| %-15s | %-40s |\n", rs.getString(1), rs.getString(2));
            }

            System.out.println("--------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Lỗi: ");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public static void hienThiThoiGianHoc(){
        System.out.println("\n============Hiển thị thơi gian học============");
        try {
            con = ConnectDB.getConnectDB();
            String sql = "SELECT * FROM THOIGIANHOC";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                System.out.println("MaTGH: " + rs.getInt("MaTGH"));
                System.out.println("NgayHoc: " + rs.getString("NgayHoc"));
                System.out.println("GioHoc: " + rs.getString("GioHoc"));
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void hienThiThongTinLop() {
        con = ConnectDB.getConnectDB();
        String sql = "SELECT MaLH, TenMH, HoTenGV, NgayHoc, GioHoc, PhongHoc,SoBuoi,NgayKhaiGiang,HocPhi FROM LOPHOC as LH , MONHOC as MH, GIAOVIEN as GV, THOIGIANHOC as TGH Where MH.MaMH = LH.MaMH AND GV.MaGV = LH.MaGV AND TGH.MaTGH = LH.MaTGH";
        try {
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            System.out.println(
                    ">++=========================================================++@>THÔNG TIN LỚP HỌC<@++=========================================================++< ");
            System.out.println(
                    " ------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("|%10s||%15s||%25s||%10s||%15s||%10s||%8s||%15s||%10s           |", "Mã Lớp học",
                    "Tên Môn học", "Tên Giáo viên", "Ngày học", "Giờ học", "Phòng học", "Số buổi", "Ngày khai khảng",
                    "Học phí");
            System.out.println();
            System.out.println(
                    " ------------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf("|%10d||%15s||%25s||%10s||%15s||%10s||%8d||%15tD||%10d%10s|", rs.getInt("MaLH"),
                        rs.getString("TenMH"), rs.getString("HoTenGV"), rs.getString("NgayHoc"), rs.getString("GioHoc"),
                        rs.getString("PhongHoc"), rs.getInt("SoBuoi"), rs.getDate("NgayKhaiGiang"), rs.getInt("HocPhi"),
                        " Nghìn đồng");
                System.out.println();

            }
            System.out.println(
                    " ------------------------------------------------------------------------------------------------------------------------------------------------");
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Không hiển thị");
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

    public static void hienThiToanBoThongTinDangKy(){
        System.out.println("\n==========Hiển thị toàn bộ thông tin============");
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("+----------+----------+------------------+---------------------+-------------------------+--------------------------+----------+------------------+----------------+----------+----------+--------------+-----------+-------------------+------------+--------------+--------------+");
                System.out.println("|   MaDK   |   MaPH   |     HoTenPH      |        DiaChi       |          SoDT           |            Email         |  MaTre   |     HoTenTre     |     NgaySinh   | GioiTinh |  MaLH    |   PhongHoc   |   SoBuoi  |    NgayKhaiGiang  |   HocPhi   |  NgayDangKy  |   TrangThai  |");
                System.out.println("+----------+----------+------------------+---------------------+-------------------------+--------------------------+----------+------------------+----------------+----------+----------+--------------+-----------+-------------------+------------+--------------+--------------+");


                String sql = "SELECT DK.MaDK, PH.MaPH, PH.HoTenPH, PH.DiaChi, PH.SoDT, PH.Email, T.MaTre, T.HoTenTre, T.NgaySinh, T.GioiTinh, LH.MaLH, LH.PhongHoc, LH.SoBuoi, LH.NgayKhaiGiang, LH.HocPhi, DK.NgayDangKy, DK.TrangThai\n" +
                        "FROM DANGKYTRE DK\n" +
                        "JOIN PHUHUYNH PH ON DK.MaPH = PH.MaPH\n" +
                        "JOIN TREEM T ON DK.MaTre = T.MaTre\n" +
                        "JOIN LOPHOC LH ON DK.MaLH = LH.MaLH\n";
                pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while (rs.next()){
                    System.out.printf("| %-8s | %-8s | %-16s | %-19s | %-23s | %-23s | %-8s | %-16s | %-14s | %-8s | %-8s | %-12s | %-9s | %-17s | %-10s | %-12s | %-12s |\n",
                            rs.getInt("MaDK"),
                            rs.getInt("MaPH"),
                            rs.getString("HoTenPH"),
                            rs.getString("DiaChi"),
                            rs.getString("SoDT"),
                            rs.getString("Email"),
                            rs.getInt("MaTre"),
                            rs.getString("HoTenTre"),
                            rs.getDate("NgaySinh"),
                            rs.getString("GioiTinh"),
                            rs.getInt("MaLH"),
                            rs.getString("PhongHoc"),
                            rs.getInt("SoBuoi"),
                            rs.getDate("NgayKhaiGiang"),
                            rs.getDouble("HocPhi"),
                            rs.getDate("NgayDangKy"),
                            rs.getString("TrangThai"));
                }
                rs.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        System.out.println("+----------+----------+------------------+---------------------+-------------------------+--------------------------+----------+------------------+----------------+----------+----------+--------------+-----------+-------------------+------------+--------------+--------------+");
    }

}

