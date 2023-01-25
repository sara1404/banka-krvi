package com.isa.bloodbank.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

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
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	public LocalDate DateTime;
	//@ManyToOne
	//@JoinColumn(name = "bloodbank_id", referencedColumnName = "id")
	//public BloodBank bloodBank;
	@Column
	Long bloodBankId;
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
