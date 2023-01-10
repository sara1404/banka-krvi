package com.isa.bloodbank;

import com.isa.bloodbank.entity.Address;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.User;
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
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppoitntmentAdminsTestTest {

	@Autowired
	private AppointmentService appointmentService;

	/*
	@Test(expected = PessimisticLockingFailureException.class)
	public void testPessimisticLockingScenario() throws Throwable {
		final LocalDateTime start = LocalDateTime.now();
		final Long id = Long.valueOf(16);
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("Startovan Thread 1");
				appointmentService.findAvailableMedicalStaff(id, start, 10); // izvrsavanje transakcione metode traje oko 200 milisekundi
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
				}*/// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
	/*
	 * Drugi thread pokusava da izvrsi transakcionu metodu findOneById dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
	 * Metoda je oznacena sa NO_WAIT, sto znaci da drugi thread nece cekati da prvi thread zavrsi sa izvrsavanjem metode vec ce odmah dobiti PessimisticLockingFailureException uz poruke u logu:
	 * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : SQL Error: 0, SQLState: 55P03
	 * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : ERROR: could not obtain lock on row in relation "product"
	 * Prema Postgres dokumentaciji https://www.postgresql.org/docs/9.3/errcodes-appendix.html, kod 55P03 oznacava lock_not_available
	 */
	//System.out.println("poziva drugi servis");
				/*appointmentService.findAvailableMedicalStaff(id, start, 10);
				;
			}
		});
		try {
			//System.out.println("prodje");
			future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
		} catch (final ExecutionException e) {
			System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
			throw e.getCause();
		} catch (final InterruptedException e) {
			System.out.println("uhvati drugi");
			e.printStackTrace();
		}
		executor.shutdown();
	}*/

	@Test(expected = PessimisticLockingFailureException.class)
	public void testPessimisticLockingScenarioCreate() throws Throwable {
		final BloodBank bloodBank = new BloodBank();
		final Address address = new Address();
		final User nurse = new User();//("ime", "imenic", Long.valueOf(1235555), "email", UserType.ADMIN_CENTER, BloodType.O_NEGATIVE, "sifra", false, "01100",
		//Gender.MALE, "string", "string",
		//address, bloodBank, null, 0);
		final LocalDateTime time = LocalDateTime.now();
		final Appointment appointment = new Appointment();//(bloodBank, time, Long.valueOf(10), true, null, nurse, false, nurse, Long.valueOf(0));
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
				//System.out.println("poziva drugi servis");
				appointmentService.createAppointment(appointment, id);
				;
			}
		});
		try {
			System.out.println("prodje");
			future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
		} catch (final ExecutionException e) {
			System.out.println("ovde");
			System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
			throw e.getCause();
		} catch (final InterruptedException e) {
			System.out.println("uhvati drugi");
			e.printStackTrace();
		}
		executor.shutdown();
	}
}