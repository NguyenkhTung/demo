package com.example.demo.repository;

import com.example.demo.entity.BaiHat;
import com.example.demo.response.BaiHatResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BaiHatRepository extends JpaRepository<BaiHat,Integer> {
    @Query("select new com.example.demo.response.BaiHatResponse(" +
            "bh.id," +
            "bh.tenBaiHat," +
            "bh.tenTacGia," +
            "bh.thoiLuong," +
            "bh.ngaySanXuat," +
            "bh.gia," +
            "bh.caSi.tenCaSi," +
            "bh.caSi.queQuan," +
            "bh.ngayRaMat)" +
            "from BaiHat bh")
    List<BaiHatResponse> getAll();

    @Query("select new com.example.demo.response.BaiHatResponse(" +
            "bh.id," +
            "bh.tenBaiHat," +
            "bh.tenTacGia," +
            "bh.thoiLuong," +
            "bh.ngaySanXuat," +
            "bh.gia," +
            "bh.caSi.tenCaSi," +
            "bh.caSi.queQuan," +
            "bh.ngayRaMat)" +
            "from BaiHat bh")
    Page<BaiHatResponse> phanTrang(Pageable pageable);
}
