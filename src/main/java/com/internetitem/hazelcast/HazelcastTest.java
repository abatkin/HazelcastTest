package com.internetitem.hazelcast;

import groovy.lang.Binding;

import org.codehaus.groovy.tools.shell.Groovysh;
import org.codehaus.groovy.tools.shell.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastTest {

	public static final Logger logger = LoggerFactory.getLogger(HazelcastTest.class);

	public static void main(String[] args) throws Exception {
		System.setProperty("hazelcast.logging.type", "slf4j");
		HazelcastTestConfiguration testConfig = HazelcastTestConfiguration.buildConfiguration(args);

		Config config = new Config();
		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);

		int port = testConfig.getPort();
		logger.info("Listening on port [" + port + "]");
		config.getNetworkConfig().setPort(port);

		for (String host : testConfig.getHosts()) {
			logger.info("Adding connect host [" + host + "]");
			config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(host);
		}

		HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);

		Binding binding = new Binding();
		binding.setVariable("hz", hz);
		Groovysh gs = new Groovysh(binding, new IO());
		gs.run(testConfig.getProgram());
	}

}
