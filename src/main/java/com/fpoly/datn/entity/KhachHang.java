package com.fpoly.datn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class KhachHang {
    private Integer id;
    private String hoTen;
    private boolean gioiTinh;
    private String diaChi;
    private String soDienThoai;
    private String email;




}
