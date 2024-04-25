package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import connectDB.ConnectDB;
import utility.InputUtils;
import static utility.InputUtils.*;

public class DeleteDAO {
    static Scanner sc = new Scanner(System.in);
    static InputUtils check = new InputUtils();
    static ConnectDB conn = new ConnectDB();
    static Connection con = conn.getConnectDB();
    static PreparedStatement pst = null;
    public static void xoaThongTinDangKyDatabase(){
        System.out.println("\n=========== Xóa thông tin đăng ký ==========");
        boolean isValidInput = false;
        do {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập MaDK cần xóa: ");
                int MaDK = Integer.parseInt(sc.nextLine());
                if (!InputUtils.validateMa(MaDK)) {
                    System.out.println("mã đăng ký không hợp lệ");
                    continue;
                }
                if (!kiemTraMaDKTonTai(MaDK)) {
                    System.out.println("Mã đăng ký không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                String sql = "DELETE FROM DANGKYTRE WHERE MaDK=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, MaDK);
                int hangDuocXoa = pst.executeUpdate();

                if (hangDuocXoa > 0) {
                    System.out.println("Thông tin đăng ký đã được xóa thành công");
                } else {
                    System.out.println("Không tìm thấy MaDK tương ứng. Không có gì được xóa.");
                }
                con.close();
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: ");
            } catch (SQLException e) {
                throw new RuntimeException("Lỗi SQL: ");
            }
            isValidInput = true;
        }while (!isValidInput);
    }
}

