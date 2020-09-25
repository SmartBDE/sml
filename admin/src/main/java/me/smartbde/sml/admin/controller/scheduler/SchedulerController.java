package me.smartbde.sml.admin.controller.scheduler;

import me.smartbde.sml.admin.domain.model.Jobs;
import me.smartbde.sml.admin.domain.model.Schedules;
import me.smartbde.sml.admin.domain.repository.MySQLJobsRepository;
import me.smartbde.sml.admin.domain.repository.MySQLSchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/scheduler")
public class SchedulerController {
    @Autowired
    MySQLJobsRepository mySQLJobsRepository;
    @Autowired
    MySQLSchedulesRepository mySQLSchedulesRepository;

    @RequestMapping("/index")
    public String getIndex(Model model) {
        List<Schedules> schedules = mySQLSchedulesRepository.findAll();

        model.addAttribute("schedules", schedules);
        return "scheduler/index";
    }
    @RequestMapping("/list")
    public String getList(Model model) {
        List<Schedules> schedules = mySQLSchedulesRepository.findAll();

        List<Jobs> jobs = new ArrayList<>();
        for (Schedules schedule : schedules) {
            List<Jobs> jobList = mySQLJobsRepository.findByName(schedule.getJobname());
            jobs.addAll(jobList);
        }

        model.addAttribute("jobs", jobs);
        return "scheduler/list";
    }
}
