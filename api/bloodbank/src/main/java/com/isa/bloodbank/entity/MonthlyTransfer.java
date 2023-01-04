package com.isa.bloodbank.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@Entity
public class MonthlyTransfer extends BaseEntity {
	@Column
	public LocalDate DateTime;
	@ManyToOne
	@JoinColumn(name = "bloodbank_id", referencedColumnName = "id")
	public BloodBank bloodBank;
	@Column
	public int APlus;
	@Column
	public int AMinus;
	@Column
	public int BPlus;
	@Column
	public int BMinus;
	@Column
	public int ABPlus;
	@Column
	public int ABMinus;
	@Column
	public int OPlus;
	@Column
	public int OMinus;

	public MonthlyTransfer() {
	}

}
