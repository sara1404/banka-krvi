package com.isa.bloodbank.Tests;


import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.BloodBankMapper;
import com.isa.bloodbank.repository.AppointmentRepository;
import com.isa.bloodbank.repository.BloodBankRepository;
import com.isa.bloodbank.service.AppointmentService;
import com.isa.bloodbank.service.BloodBankService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSchedulingAppointment {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private BloodBankService bloodBankService;
    @Autowired
    private BloodBankRepository bloodBankRepository;
    @Autowired
    private  AppointmentService appointmentService;
    @Autowired
    private BloodBankMapper bloodBankMapper;

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testConcurrentTransactionalMethod() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                BloodBank current = bloodBankRepository.findById(1L).get();
                if(!current.getAvailable())
                    throw new ObjectOptimisticLockingFailureException(BloodBank.class, current);
                current.setAvailable(false);
                bloodBankRepository.save(current);
                Appointment appointment = new Appointment();
                appointment.setBloodBank(current);
                appointmentRepository.save(appointment);
                current.setAvailable(true);
                bloodBankRepository.save(current);






                //try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju

            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                BloodBank current = bloodBankRepository.findById(1L).get();
                if(!current.getAvailable())
                    throw new ObjectOptimisticLockingFailureException(BloodBank.class, current);
                current.setAvailable(false);
                bloodBankRepository.save(current);
                Appointment appointment = new Appointment();
                appointment.setBloodBank(current);
                appointmentRepository.save(appointment);
                current.setAvailable(true);
                bloodBankRepository.save(current);
            }
        });
        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();

    }


}
