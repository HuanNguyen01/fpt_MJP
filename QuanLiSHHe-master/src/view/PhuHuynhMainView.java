
package view;

import controller.PhuHuynhController;
import dao.LopHocDAO;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.SQLException;
import connectDB.ConnectDB;

public class PhuHuynhMainView {
    private PhuHuynhView phuHuynhView;
    private DangKyView dangKyView;
    private Scanner scanner;

    public PhuHuynhMainView() throws SQLException {
        this.phuHuynhView = new PhuHuynhView(new PhuHuynhController(new LopHocDAO(ConnectDB.getConnectDB())));
        this.dangKyView = new DangKyView(ConnectDB.getConnectDB());
        this.scanner = new Scanner(System.in);
    }

    public void showPhuHuynhMenu() {
        while (true) {
            System.out.println("\n===============================Menu Phụ Huynh==========================================");
            System.out.println("|  1. Xem thông tin lớp học                                                           |");
            System.out.println("|  2. Tìm kiếm thông tin lớp học theo tên môn học                                     |");
            System.out.println("|  3. Đăng ký lớp học cho trẻ                                                         |");
            System.out.println("|  4. Liệt kê môn học được nhiều Phụ huynh đăng ký nhất từ tháng 1/2024 đến 3/2024    |");
            System.out.println("|  5. Thoát                                                                           |");
            System.out.println("=======================================================================================");
            System.out.print("Lựa chọn của bạn: ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        phuHuynhView.layDanhSachLopHoc();
                        break;
                    case 2:
                        phuHuynhView.timKiemLopHocTheoTenMH();
                        break;
                    case 3:
                        dangKyView.nhapThongTinDangKy();
                        break;
                    case 4:
                        dangKyView.hienThiMonHocNhieuNguoiDangKyNhat();
                        break;
                    case 5:
                        System.out.println("Cảm ơn bạn đã sử dụng hệ thống.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
                scanner.next();
            } catch (SQLException e) {
                System.out.println("Lỗi khi xử lý dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi không mong muốn: " + e.getMessage());
            }
        }
    }
}




