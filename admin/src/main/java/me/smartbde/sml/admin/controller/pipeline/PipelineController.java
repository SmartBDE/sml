package me.smartbde.sml.admin.controller.pipeline;

import me.smartbde.sml.admin.domain.model.Jobs;
import me.smartbde.sml.admin.domain.model.Schedules;
import me.smartbde.sml.admin.domain.model.Streamings;
import me.smartbde.sml.admin.domain.repository.MySQLJobsRepository;
import me.smartbde.sml.admin.domain.repository.MySQLSchedulesRepository;
import me.smartbde.sml.admin.domain.repository.MySQLStreamingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pipeline")
public class PipelineController {
    @Autowired
    MySQLJobsRepository mySQLJobsRepository;
    @Autowired
    MySQLStreamingsRepository mySQLStreamingsRepository;

    @RequestMapping("/index")
    public String getIndex(Model model) {
        List<Streamings> schedules = mySQLStreamingsRepository.findAll();

        List<Jobs> jobs = new ArrayList<>();
        for (Streamings s : schedules) {
            List<Jobs> jobList = mySQLJobsRepository.findByName(s.getJobname());
            jobs.addAll(jobList);
        }

        model.addAttribute("jobs", jobs);
        return "pipeline/index";
    }
}
