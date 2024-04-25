package view;

import controller.DangKyController;
import utility.InputUtils;

import java.sql.Connection;
import java.time.LocalDate;

public class DangKyView {
    private DangKyController dangKyController;

    public DangKyView(Connection conn) {
        this.dangKyController = new DangKyController(conn);
    }

    public void nhapThongTinDangKy() {
        System.out.println("Mời bạn nhập vào các thông tin cần để đăng ký!");


        String hoTenPH = InputUtils.getStringInput("Nhập họ tên phụ huynh: ");
        String diaChi = InputUtils.getStringInput("Nhập địa chỉ phụ huynh: ");
        String soDT = InputUtils.getStringInput("Nhập số điện thoại phụ huynh: ");
        while (!InputUtils.isValidPhoneNumber(soDT)) {
            System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
            soDT = InputUtils.getStringInput("Nhập số điện thoại phụ huynh: ");
        }
        String email = InputUtils.getStringInput("Nhập email phụ huynh: ");
        while (!InputUtils.isValidEmail(email)) {
            System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
            email = InputUtils.getStringInput("Nhập email phụ huynh: ");
        }


        String hoTenTre = InputUtils.getStringInput("Nhập họ tên trẻ em: ");

        LocalDate ngaySinh = InputUtils.getDateInput("Nhập ngày sinh trẻ em (dd/mm/yyyy): ");

        boolean coDuDieuKienNhapHoc = dangKyController.kiemTraDieuKienNhapHoc(ngaySinh);
        if (!coDuDieuKienNhapHoc) {
            System.out.println("Trẻ không đủ điều kiện nhập học (tuổi từ 5 đến 15). Vui lòng kiểm tra lại ngày sinh của trẻ.");
            return;
        }

        String gioiTinh = InputUtils.getStringInput("Nhập giới tính trẻ em: ");


        int maLH = InputUtils.getIntInput("Nhập mã lớp học mà trẻ đăng ký: ");
        LocalDate ngayDangKy = LocalDate.now();


        boolean isSuccess = dangKyController.dangKy(hoTenPH, diaChi, soDT, email, hoTenTre, ngaySinh, gioiTinh, maLH, ngayDangKy);
        if (isSuccess) {
            System.out.println("Đăng ký thành công!");
        } else {
            System.out.println("Đăng ký không thành công. Vui lòng thử lại.");
        }

        boolean tiepTuc = InputUtils.getBooleanInput("Bạn có muốn nhập đăng ký khác không? (C/K)");
        while (tiepTuc) {
            nhapThongTinDangKy();
            tiepTuc = InputUtils.getBooleanInput("Bạn có muốn nhập đăng ký khác không? (C/K)");
        }
    }

    public void hienThiMonHocNhieuNguoiDangKyNhat() {
        dangKyController.hienThiMonHocNhieuNguoiDangKyNhat();
    }
}

