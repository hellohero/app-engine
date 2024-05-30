package com.appengine.task.rest;

import com.appengine.common.exception.EngineExceptionHelper;
import com.appengine.frame.context.RequestContext;
import com.appengine.task.domain.Task;
import com.appengine.task.service.TaskService;
import com.appengine.task.utils.TaskExcepFactor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Authors: sofn
 * Version: 1.0  Created at 2015-10-12 00:18.
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    private static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Resource
    private TaskService taskService;

    @GetMapping(value = "/list")
    public Page<Task> list(
            RequestContext rc,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize
    ) {
        PageRequest request = new PageRequest(page - 1, pageSize);
        return taskService.getTasksByPage(rc.getCurrentUid(), request);
    }

    @GetMapping(value = "/get")
    public Task get(@RequestParam("id") Long id) {
        Task task = taskService.getTask(id);
        if (task == null) {
            String message = "任务不存在(id:" + id + ")";
            logger.warn(message);
            throw EngineExceptionHelper.localException(TaskExcepFactor.TASK_NOT_EXISTS);
        }
        return task;
    }

    @PostMapping(value = "/save")
    public long save(RequestContext rc,
                     @RequestParam(required = false, defaultValue = "0") long id,
                     @RequestParam String title,
                     @RequestParam String desc
    ) {
        Task task = new Task(title, desc, rc.getCurrentUid());
        if (id > 0) {
            task.setId(id);
        }
        //保存任务,没有则创建,有则更新
        taskService.saveTask(task);
        return task.getId();
    }

    @DeleteMapping(value = "/delete")
    public boolean delete(@RequestParam("id") Long id) {
        return taskService.deleteTask(id);
    }
}
