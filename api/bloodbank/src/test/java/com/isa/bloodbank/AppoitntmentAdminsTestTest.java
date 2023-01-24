package com.isa.bloodbank;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.entity.Address;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.mapping.AppointmentMapper;
import com.isa.bloodbank.service.AppointmentService;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppoitntmentAdminsTestTest {
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private AppointmentMapper mapper;

	@Test(expected = PessimisticLockingFailureException.class)
	public void testPessimisticLockingScenarioCreate() throws Throwable {
		final BloodBank bloodBank = new BloodBank();
		final Address address = new Address();
		final User nurse = new User();
		final LocalDateTime time = LocalDateTime.now();
		final Appointment appointment = new Appointment();
		appointment.setStartTime(time);
		final Long id = Long.valueOf(16);
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("Startovan Thread 1");
				appointmentService.createAppointment(appointment, id); // izvrsavanje transakcione metode traje oko 200 milisekundi
			}
		});
		final Future<?> future2 = executor.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("Startovan Thread 2");
				try {
					Thread.sleep(150);
				} catch (final InterruptedException e) {
					System.out.println("kad udje u drugi");
				}// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
				/*
				 * Drugi thread pokusava da izvrsi transakcionu metodu findOneById dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
				 * Metoda je oznacena sa NO_WAIT, sto znaci da drugi thread nece cekati da prvi thread zavrsi sa izvrsavanjem metode vec ce odmah dobiti PessimisticLockingFailureException uz poruke u logu:
				 * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : SQL Error: 0, SQLState: 55P03
				 * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : ERROR: could not obtain lock on row in relation "product"
				 * Prema Postgres dokumentaciji https://www.postgresql.org/docs/9.3/errcodes-appendix.html, kod 55P03 oznacava lock_not_available
				 */
				appointmentService.createAppointment(appointment, id);
				;
			}
		});
		try {
			future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
		} catch (final ExecutionException e) {
			System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
			throw e.getCause();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}

	@Test(expected = CannotAcquireLockException.class)
	public void testAdminUserConcurrency() throws Throwable {
		final LocalDateTime time = LocalDateTime.now();
		final Appointment appointment = new Appointment();
		appointment.setStartTime(time);
		final BloodBank bb = new BloodBank();
		bb.setId(16l);
		appointment.setBloodBank(bb);
		final Long adminId = 3l;
		final Long userId = 5l;
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("Startovan Thread 1");
				final var appointmentDto = new AppointmentDto();
				appointmentDto.setStartTime(time);
				final var bloodBankDto = new BloodBankDto();
				bloodBankDto.setId(16l);
				appointmentDto.setBloodBank(bloodBankDto);
				appointmentService.userCreatesAppointment(appointmentDto, userId);
			}
		});
		final Future<?> future2 = executor.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("Startovan Thread 2");
				try {
					Thread.sleep(1500);
				} catch (final InterruptedException e) {
					System.out.println("kad udje u drugi");
				}
				appointmentService.createAppointment(appointment, adminId);
				;
			}
		});
		try {
			future2.get();
		} catch (final ExecutionException e) {
			System.out.println("Exception from thread " + e.getCause().getClass());
			throw e.getCause();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}
}