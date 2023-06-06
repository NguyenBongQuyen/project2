package jmaster.io.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.demo.dto.ResponseDTO;

@RestController
@RequestMapping("cache")
public class CacheController {
	@Autowired
	CacheManager cacheManager;
	
	@GetMapping("/")
	public List<String> getCache() {
		return cacheManager.getCacheNames().stream().collect(Collectors.toList());
	}
	
	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam("name") String name) {
		Cache cache = cacheManager.getCache(name);
		if (cache != null)
			cache.clear();
		
		return ResponseDTO.<Void>builder().status(200).build();
	}
}
