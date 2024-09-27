package com.fpoly.datn.service;

import com.fpoly.datn.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhachHangService {
    List<KhachHang> list = new ArrayList<>();
    private Integer currentId = 1;
    public KhachHangService() {
        list.add(new KhachHang(currentId++, "Nguyen Van A", true, "Chuong My, Ha Noi", "0974492604", "anv08@gmail.com"));
        list.add(new KhachHang(currentId++, "Nguyen Van B", false, "Thanh Xuan, Ha Noi", "0974492605", "anv09@gmail.com"));
        list.add(new KhachHang(currentId++, "Nguyen Van C", true, "Ha Dong, Ha Noi", "0974492606", "anv10@gmail.com"));

    }
    public List<KhachHang> getAll(){
        return list;
    }
    public KhachHang getKhachHangById(Integer id) {
        return list.stream().filter(kh -> kh.getId() == id)
                .findFirst().orElse(null);
    }
    public KhachHang addKhachHang(KhachHang khachHang){
        khachHang.setId(currentId++);
        list.add(khachHang);
        return khachHang;
    }
    public KhachHang updateKH(KhachHang kh,Integer id){
        KhachHang checkKH = getKhachHangById(id);
        if(checkKH != null){
            checkKH.setId(kh.getId());
            checkKH.setHoTen(kh.getHoTen());
            checkKH.setGioiTinh(kh.isGioiTinh());
            checkKH.setDiaChi(kh.getDiaChi());
            checkKH.setSoDienThoai(kh.getSoDienThoai());
            checkKH.setEmail(kh.getEmail());
            return checkKH;
        }
        return null;
    }
    public boolean remove(Integer id){
        return  list.removeIf(kh -> kh.getId() == id);
    }
    public Page<KhachHang> search(String keyword, Boolean gioiTinh, String diaChi, String soDienThoai, String email, Pageable pageable) {
        List<KhachHang> filteredKhachHangs = filterKhachHang(keyword, gioiTinh, diaChi, soDienThoai, email);

        // Calculate pagination
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredKhachHangs.size());
        List<KhachHang> pagedList = filteredKhachHangs.subList(start, end);

        return new PageImpl<>(pagedList, pageable, filteredKhachHangs.size());
    }

    public List<KhachHang> filterKhachHang(String keyword, Boolean gioiTinh, String diaChi, String soDienThoai, String email) {
        return list.stream()
                .filter(kh -> (keyword == null || kh.getHoTen().toLowerCase().contains(keyword.toLowerCase())) &&
                        (gioiTinh == null || kh.isGioiTinh() == gioiTinh) &&
                        (diaChi == null || kh.getDiaChi().toLowerCase().contains(diaChi.toLowerCase())) &&
                        (soDienThoai == null || kh.getSoDienThoai().contains(soDienThoai)) &&
                        (email == null || kh.getEmail().toLowerCase().contains(email.toLowerCase())))
                .collect(Collectors.toList());
    }
}
