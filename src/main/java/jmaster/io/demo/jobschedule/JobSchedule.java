package jmaster.io.demo.jobschedule;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jmaster.io.demo.entity.User;
import jmaster.io.demo.repository.UserRepo;
import jmaster.io.demo.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobSchedule {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EmailService emailService;
	
	@Scheduled(fixedDelay = 60000)
	public void hello() {
//		log.info("Hello");
//		emailService.testEmail(); //comment để khi chạy server ko bị spam vào email
	}
	//GIÂY - PHÚT - GIỜ - NGÀY - THÁNG - THỨ
	@Scheduled(cron = "0 18 15 * * *")
	public void morning() {
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		
		//tháng 1 tương ứng 0
		int month = cal.get(Calendar.MONTH) + 1;
		
		List<User> users = userRepo.searchByBirthday(date, month);
		
		for (User u : users) {
			log.info("Happy Birthday " + u.getName());
			emailService.sendBirthdayEmail(u.getEmail(), u.getName());
		}
		log.info("Good Morning");
	}
}
