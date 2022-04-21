package com.example.demo.service;

import java.io.File; 
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;

@Service
public class HardWareCheckService {

	private  DecimalFormat df = new DecimalFormat("###");
	
	public String getMemoryStatus() {
		OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		double totalMemory = osBean.getTotalPhysicalMemorySize() + osBean.getTotalSwapSpaceSize();
		double freeMemory = osBean.getFreePhysicalMemorySize() + osBean.getFreeSwapSpaceSize();

		double usage = ((totalMemory - freeMemory) / totalMemory) * 100;
	
		return df.format(usage);
	}
	
	public String getCpuStatus() {
		OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		RuntimeMXBean runbean = (RuntimeMXBean) ManagementFactory.getRuntimeMXBean();

		long bfprocesstime = osbean.getProcessCpuTime();
		long bfuptime = runbean.getUptime();
		long ncpus = osbean.getAvailableProcessors();

		for (int i = 0; i < 1000000; ++i) {
			ncpus = osbean.getAvailableProcessors();
		}

		long afprocesstime = osbean.getProcessCpuTime();
		long afuptime = runbean.getUptime();

		float cal = (afprocesstime - bfprocesstime) / ((afuptime - bfuptime) * 10000f);

		float usage = 100 - Math.min(99f, cal) % 90 * 10;
		if (usage < 0)
			usage = 100;

		return df.format(usage);
	}
	
	public Map<String,List<String>> getDiskStatus() {
		Map<String,List<String>> map = new HashMap<>();
		List<String>driveNameArr = new ArrayList<>();
		List<String>totalSizeArr = new ArrayList<>();
		List<String>useSizeArr = new ArrayList<>();
		List<String>freeSizeArr = new ArrayList<>();
		
		File[] drives = File.listRoots();

		String driveName = "";

		double totalSize = 0;

		double freeSize = 0;

		double useSize = 0;
		
		for(File drive : drives) {

			driveName = drive.getAbsolutePath();
			driveNameArr.add(driveName);
			
			totalSize = drive.getTotalSpace() / Math.pow(1024, 3);
			totalSizeArr.add(String.valueOf(totalSize));
			
			useSize = drive.getUsableSpace() / Math.pow(1024, 3);
			useSizeArr.add(String.valueOf(useSize));
			
			freeSize = totalSize - useSize;
			freeSizeArr.add(String.valueOf(freeSize));
			
			System.out.println("하드 디스크 이름 : " + driveName + "\n");
			System.out.println("전체 디스크 용량 : " + totalSize + " GB \n");
			System.out.println("디스크 사용 용량 : " + freeSize + " GB \n");
			System.out.println("디스크 남은 용량 : " + useSize + " GB \n");
		}
		
		map.put("drivename", driveNameArr);
		map.put("totalSize", totalSizeArr);
		map.put("useSize", useSizeArr);
		map.put("freeSize", freeSizeArr);

		return map;
	}
	
}
