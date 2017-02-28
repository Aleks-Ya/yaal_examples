package ru.yaal.bigdata.epam.course.lesson2;

import java.util.HashMap;
import java.util.Map;

public class Result {
	private Map<String, Integer> idCountMap = new HashMap<>();

	public Result(Map<String, Integer> idCountMap) {
		this.idCountMap = idCountMap;
	}

	public Map<String, Integer> getIdCountMap() {
		return idCountMap;
	}

}
