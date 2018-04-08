package com.moon.admin.controller;

import com.moon.admin.common.utils.LogAnnotation;
import com.moon.admin.dao.JobDao;
import com.moon.admin.domain.JobModel;
import com.moon.admin.service.JobService;
import com.moon.admin.vo.page.PageTableHandler;
import com.moon.admin.vo.page.PageTableRequest;
import com.moon.admin.vo.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;

@Api(tags = "定时任务")
@RestController
@RequestMapping("/jobs")
public class JobController {

	@Autowired
	private JobService jobService;
	@Autowired
	private JobDao jobDao;

	@LogAnnotation
	@ApiOperation("添加定时任务")
	@PostMapping
	@RequiresPermissions("job:add")
	public void add(@RequestBody JobModel jobModel) {
		JobModel model = jobDao.getByName(jobModel.getJobName());
		if (model != null) {
			throw new IllegalArgumentException(jobModel.getJobName() + "已存在");
		}

		jobModel.setIsSysJob(false);
		jobService.saveJob(jobModel);
	}

	@LogAnnotation
	@ApiOperation("修改定时任务")
	@PutMapping
	@RequiresPermissions("job:add")
	public void update(@RequestBody JobModel jobModel) {
		jobModel.setStatus(1);
		jobService.saveJob(jobModel);
	}

	@LogAnnotation
	@ApiOperation("删除定时任务")
	@DeleteMapping("/{id}")
	@RequiresPermissions("job:del")
	public void delete(@PathVariable Long id) throws SchedulerException {
		jobService.deleteJob(id);
	}

	@ApiOperation("根据id获取定时任务")
	@GetMapping("/{id}")
	@RequiresPermissions("job:query")
	public JobModel getById(@PathVariable Long id) {
		return jobDao.getById(id);
	}

	@GetMapping
	@ApiOperation(value = "定时任务列表")
	@RequiresPermissions("job:query")
	public PageTableResponse list(PageTableRequest request) {
		return new PageTableHandler(new PageTableHandler.CountHandler() {

			@Override
			public int count(PageTableRequest request) {
				return jobDao.count(request.getParams());
			}
		}, new PageTableHandler.ListHandler() {

			@Override
			public List<JobModel> list(PageTableRequest request) {
				List<JobModel> list = jobDao.list(request.getParams(), request.getOffset(), request.getLimit());
				return list;
			}
		}).handle(request);
	}

	@ApiOperation(value = "校验cron表达式")
	@GetMapping(params = "cron")
	public boolean checkCron(String cron) {
		return CronExpression.isValidExpression(cron);
	}

	@Autowired
	private ApplicationContext applicationContext;

	@ApiOperation(value = "springBean名字")
	@GetMapping("/beans")
	public List<String> listAllBeanName() {
		String[] strings = applicationContext.getBeanDefinitionNames();
		List<String> list = new ArrayList<>();
		for (String str : strings) {
			if (str.contains(".")) {
				continue;
			}

			Class<?> clazz = getClass(str);
			if (clazz.isAssignableFrom(Controller.class) || clazz.isAnnotationPresent(RestController.class)) {
				continue;
			}

			list.add(str);
		}
		list.sort((l1, l2) -> l1.compareTo(l2));

		return list;
	}

	@ApiOperation(value = "springBean的无参方法")
	@GetMapping("/beans/{name}")
	public Set<String> listMethodName(@PathVariable String name) {
		Class<?> clazz = getClass(name);
		Method[] methods = clazz.getDeclaredMethods();

		Set<String> names = new HashSet<>();
		Arrays.asList(methods).parallelStream().forEach(m -> {
			int b = m.getModifiers();// public 1 static 8 final 16
			if (b == 1 || b == 9 || b == 17 || b == 25) {
				Class<?>[] classes = m.getParameterTypes();
				if (classes.length == 0) {
					names.add(m.getName());
				}
			}
		});

		return names;
	}

	private Class<?> getClass(String name) {
		Object object = applicationContext.getBean(name);
		Class<?> clazz = object.getClass();
		if (AopUtils.isAopProxy(object)) {
			clazz = clazz.getSuperclass();
		}

		return clazz;
	}

}
