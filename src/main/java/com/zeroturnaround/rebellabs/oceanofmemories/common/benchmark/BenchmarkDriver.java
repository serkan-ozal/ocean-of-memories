package com.zeroturnaround.rebellabs.oceanofmemories.common.benchmark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.BenchmarkParams;

import com.zeroturnaround.rebellabs.oceanofmemories.common.util.LogUtil;

@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"-Xmx4G", "-Xms4G", "-XX:+UseTLAB"})
public class BenchmarkDriver {

	private static final Logger logger = LogUtil.getLogger();
	
	public static final String BENCHMARK_ID_PARAM_NAME = "benchmarkId";
	
	private static Map<Integer, BenchmarkRunner> benchmarkRunnerMap = new HashMap<Integer, BenchmarkRunner>();
	private List<BenchmarkRunner> actualBenchmarkRunners = new ArrayList<BenchmarkRunner>();
	
	static {
		init();
	}
	
	private static void init() {
		
	}
	
	public static void registerBenchmarkRunner(BenchmarkRunner benchmarkRunner) {
		benchmarkRunnerMap.put(benchmarkRunner.getId(), benchmarkRunner);
	}
	
	@Setup(Level.Trial)
    public void benchmarkSetup(BenchmarkParams params) {
		logger.info("Benchmark setup ...");
        String benchmarkIdParam = params.getParam(BENCHMARK_ID_PARAM_NAME);
        if (StringUtils.isNotEmpty(benchmarkIdParam)) {
        	Integer benchmarkId = Integer.parseInt(benchmarkIdParam);
        	BenchmarkRunner benchmarkRunner = benchmarkRunnerMap.get(benchmarkId);
        	if (benchmarkRunner != null) {
        		actualBenchmarkRunners.add(benchmarkRunner);
        	}	
        }
        else {
        	actualBenchmarkRunners.addAll(benchmarkRunnerMap.values());
        }
    }
	
	@Setup(Level.Iteration)
    public void setup(BenchmarkParams params) {
        logger.info("Iteration setup ...");
    }

	@Benchmark
	public void benchmark() {
	    if (actualBenchmarkRunners != null) {
	    	for (BenchmarkRunner benchmarkRunner : actualBenchmarkRunners) {
	    		benchmarkRunner.run();
	    	}
	    }
	}

	@TearDown(Level.Iteration)
    public void teardown() {
		logger.info("Iteration teardown ...");
    }
	
    @TearDown(Level.Trial)
    public void benchmarkTeardown() {
    	logger.info("Benchmark teardown ...");
    	actualBenchmarkRunners.clear();
    }
    
}
