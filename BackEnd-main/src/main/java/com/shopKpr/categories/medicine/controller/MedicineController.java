package com.shopKpr.categories.medicine.controller;

import com.shopKpr.categories.medicine.model.Medicine;
import com.shopKpr.categories.medicine.service.MedicineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/category/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController( MedicineService medicineService ) {
        this.medicineService = medicineService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("addNewMedicine")
    public void addNewMedicine( @RequestBody Medicine medicine) {
        medicineService.addNewMedicine(medicine);
    }

}
