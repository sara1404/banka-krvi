package com.isa.bloodbank.entity;

import com.isa.bloodbank.entity.enums.BloodType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class BloodSupply extends  BaseEntity{
    @Column
    Long bloodBankId;
    @Column
    Map<BloodType, Double> bloodSupply;
}
