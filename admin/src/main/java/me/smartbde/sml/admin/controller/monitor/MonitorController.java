package me.smartbde.sml.admin.controller.monitor;

import me.smartbde.sml.admin.domain.model.JobLogger;
import me.smartbde.sml.admin.domain.model.Jobs;
import me.smartbde.sml.admin.domain.model.StatInfo;
import me.smartbde.sml.admin.domain.repository.MySQLJobsRepository;
import me.smartbde.sml.admin.domain.repository.MySQLStatInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/monitor")
public class MonitorController {
    @Autowired
    MySQLJobsRepository mySQLJobsRepository;
    @Autowired
    MySQLStatInfoRepository mySQLStatInfoRepository;

    @RequestMapping("/index")
    public String getIndex(Model model) {
        List<JobLogger> jobloggers = new ArrayList<JobLogger>();
        
        List<Jobs> jobs = mySQLJobsRepository.findByPluginLike("%Logger%");
        for (Jobs job : jobs) {
            JobLogger jlogger = new JobLogger();
            jlogger.setJobName(job.getName());
            jlogger.setPlugin(job.getPlugin());
            jobloggers.add(jlogger);
        }

        model.addAttribute("jobloggers", jobloggers);
        return "monitor/index";
    }

    @RequestMapping("/list")
    public String getList(@RequestParam(name = "num") Integer pageNum, Model model) {
        Sort sort = new Sort(Sort.Direction.DESC, "acttime");
        // 这里的"recordNo"是实体类的主键，记住一定要是实体类的属性，而不能是数据库的字段
        Pageable pageable = new PageRequest(pageNum - 1, 10, sort); // （当前页， 每页记录数， 排序方式）
        Page<StatInfo> list = mySQLStatInfoRepository.findAll(pageable);

        model.addAttribute("stats", list);
        return "monitor/list";
    }
}
