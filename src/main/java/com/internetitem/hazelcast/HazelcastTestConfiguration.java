package com.internetitem.hazelcast;

import java.util.List;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class HazelcastTestConfiguration {

	private int port;
	private List<String> hosts;
	private String[] program;

	private HazelcastTestConfiguration() {
	}

	public int getPort() {
		return port;
	}

	public List<String> getHosts() {
		return hosts;
	}

	public String[] getProgram() {
		return program;
	}

	@SuppressWarnings("unchecked")
	public static HazelcastTestConfiguration buildConfiguration(String[] args) {
		OptionParser parser = new OptionParser();
		parser.accepts("connect", "Connect to this host(s)").withRequiredArg().required();
		ArgumentAcceptingOptionSpec<Integer> portArg = parser.accepts("port", "Listen port").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(7777));

		OptionSet options = parser.parse(args);

		HazelcastTestConfiguration config = new HazelcastTestConfiguration();
		config.hosts = (List<String>) options.valuesOf("connect");
		config.port = options.valueOf(portArg).intValue();
		config.program = options.nonOptionArguments().toArray(new String[0]);

		return config;
	}

}
