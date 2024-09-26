package com.fpoly.datn.controller;

import com.fpoly.datn.entity.KhachHang;
import com.fpoly.datn.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class KhachHangController {
    @Autowired
    KhachHangService khachHangService;

    @GetMapping("/khachhang")
    public String listKhachHang(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "gioiTinh", required = false) Boolean gioiTinh,
            @RequestParam(value = "diaChi", required = false) String diaChi,
            @RequestParam(value = "soDienThoai", required = false) String soDienThoai,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "2") int size,
            Model model) {

        PageRequest pageable = PageRequest.of(page, size);

        Page<KhachHang> khachHangPage = khachHangService.search(keyword, gioiTinh, diaChi, soDienThoai, email, pageable);

        model.addAttribute("khachHangPage", khachHangPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", khachHangPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("gioiTinh", gioiTinh);
        model.addAttribute("diaChi", diaChi);
        model.addAttribute("soDienThoai", soDienThoai);
        model.addAttribute("email", email);

        return "khachhang/KhachHang";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute KhachHang khachHang) {
        khachHangService.addKhachHang(khachHang);
        return "redirect:/khachhang";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "khachhang/add";
    }
    @GetMapping("/remove/{id}")
    public String xoa(@PathVariable("id") Integer id) {
        boolean isRemoved = khachHangService.remove(id);
        return "redirect:/khachhang";
    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        KhachHang khachHang = khachHangService.getKhachHangById(id);
        if (khachHang != null) {
            model.addAttribute("khachHang", khachHang);
            return "/khachhang/update";
        }
        return "redirect:/khachhang";
    }

    @PostMapping("/update/{id}")
    public String updateKhachHang(@PathVariable("id") Integer id, @ModelAttribute KhachHang khachHang) {
        khachHangService.updateKH(khachHang, id);
        return "redirect:/khachhang";
    }
    @GetMapping("/detail/{id}")
    public String hienThi(@PathVariable("id") Integer id, Model model) {
        if (id <= 0) {
            return "redirect:/khachhang";
        }

        KhachHang khachHang = khachHangService.getKhachHangById(id);
        if (khachHang != null) {
            model.addAttribute("khachHang", khachHang);
            return "/khachhang/detail";
        }
        return "redirect:/khachhang";
    }




}
