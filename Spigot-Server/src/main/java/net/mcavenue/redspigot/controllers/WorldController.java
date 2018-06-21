package net.mcavenue.redspigot.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import net.mcavenue.redspigot.configuration.pojo.ServerConfig;

@Controller
public class WorldController {
	@Autowired
	private ServerConfig cfg;

	@PostConstruct
	public void printCfg() {
		System.out.println("CFG: \n");
		System.out.println(cfg);
		System.out.println(cfg.getMotd());
	}
}
