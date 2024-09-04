package com.example.demo.controller;

import com.example.demo.entity.BaiHat;
import com.example.demo.entity.CaSi;
import com.example.demo.repository.BaiHatRepository;
import com.example.demo.repository.CaSiRepository;
import com.example.demo.request.BaiHatRequest;
import com.example.demo.response.BaiHatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bai-hat")
public class BaiHatController {
    @Autowired
    private BaiHatRepository baiHatRepository;
    @Autowired
    private CaSiRepository caSiRepository;


    @GetMapping("/getAll")
    public List<BaiHatResponse> baiHatList() {
        return baiHatRepository.getAll();
    }

    @GetMapping("/phan-trang")
    public Page<BaiHatResponse> phanTrang(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 5);
        return baiHatRepository.phanTrang(pageable);
    }

    @PostMapping("add")
    public BaiHat addBaiHat(@RequestBody BaiHatRequest baiHatRequest) {
        CaSi caSi = caSiRepository.findById(baiHatRequest.getCaSi().getId()).orElseThrow(() ->
                new RuntimeException("Ca Si khong ton tai")
        );

        BaiHat baiHat = new BaiHat();
        baiHat.setTenBaiHat(baiHatRequest.getTenBaiHat());
        baiHat.setCaSi(caSi);
        baiHat.setTenTacGia(baiHatRequest.getTenTacGia());
        baiHat.setThoiLuong(baiHatRequest.getThoiLuong());
        baiHat.setNgaySanXuat(baiHatRequest.getNgaySanXuat());
        baiHat.setGia(baiHatRequest.getGia());
        baiHat.setNgayRaMat(baiHatRequest.getNgayRaMat());
        return baiHatRepository.save(baiHat);
    }

    @GetMapping("{id}")
    public BaiHat findById(@PathVariable Integer id) {
        return baiHatRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Bai Hat khong ton tai")
        );
    }

    @PutMapping("{id}")
    public BaiHat updateBaiHat(@PathVariable Integer id, @RequestBody BaiHatRequest baiHatRequest) {
        BaiHat baiHat = baiHatRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Bai Hat khong ton tai")
        );

        CaSi caSi = caSiRepository.findById(baiHatRequest.getCaSi().getId()).orElseThrow(() ->
                new RuntimeException("Ca Si khong ton tai")
        );

        baiHat.setTenBaiHat(baiHatRequest.getTenBaiHat());
        baiHat.setCaSi(caSi);
        baiHat.setTenTacGia(baiHatRequest.getTenTacGia());
        baiHat.setThoiLuong(baiHatRequest.getThoiLuong());
        baiHat.setNgaySanXuat(baiHatRequest.getNgaySanXuat());
        baiHat.setGia(baiHatRequest.getGia());
        baiHat.setNgayRaMat(baiHatRequest.getNgayRaMat());
        return baiHatRepository.save(baiHat);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id) {
        baiHatRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Bai Hat khong ton tai")
        );
        baiHatRepository.deleteById(id);
        return "Xoa thanh cong";
    }

    @GetMapping("/filter")
    public List<BaiHatResponse> filter(@RequestParam(value = "tenBaiHat", required = false, defaultValue = "") String tenBaiHat, @RequestParam(value = "giaMin", required = false, defaultValue = "0") Double giaMin, @RequestParam(value = "giaMax", required = false, defaultValue = "100000") Double giaMax) {
        List<BaiHatResponse> baiHatResponseList = baiHatRepository.getAll();
        if (tenBaiHat != null) {
            baiHatResponseList = baiHatResponseList.stream()
                    .filter(baiHatResponse -> baiHatResponse.getTenBaiHat().toLowerCase().contains(tenBaiHat.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (giaMin != null) {
            baiHatResponseList = baiHatResponseList.stream()
                    .filter(baiHatResponse -> baiHatResponse.getGia() >= giaMin)
                    .collect(Collectors.toList());
        }
        if (giaMax != null) {
            baiHatResponseList = baiHatResponseList.stream()
                    .filter(baiHatResponse -> baiHatResponse.getGia() <= giaMax)
                    .collect(Collectors.toList());
        }

        return baiHatResponseList;
    }
}
