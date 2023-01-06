package com.isa.bloodbank.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.mailer.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

@Service
public class QrCodeService {

    public void generateAppointmentQrCode(Appointment appointment){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try{
            BitMatrix bitMatrix = qrCodeWriter.encode(appointment.getId().toString(), BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", FileSystems.getDefault().getPath("../qrcodes/" + appointment.getId() + ".png"));
        }catch(WriterException | IOException e){
            e.printStackTrace();
        }
    }
}
