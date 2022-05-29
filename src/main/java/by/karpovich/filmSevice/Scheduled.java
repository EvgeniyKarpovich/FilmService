package by.karpovich.filmSevice;

import by.karpovich.filmSevice.jpa.model.FilmModel;
import by.karpovich.filmSevice.jpa.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduled {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private FilmRepository filmRepository;

    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 0 * * ?")
    private void checkBD() {

        List<FilmModel> all = filmRepository.findAll();
        if (all.size() > 10000) {
            initiateShutdown();
        }
    }

    private void initiateShutdown() {
        SpringApplication.exit(appContext, () -> 0);
    }
}
