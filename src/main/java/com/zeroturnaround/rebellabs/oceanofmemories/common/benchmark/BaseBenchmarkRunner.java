package com.zeroturnaround.rebellabs.oceanofmemories.common.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@Fork(jvmArgsPrepend = {"-Xmx4G", "-Xms4G", "-XX:+UseTLAB"})
public abstract class BaseBenchmarkRunner implements BenchmarkRunner {

	protected int id;
	protected String name;
	protected String description;
	
	public BaseBenchmarkRunner(int id) {
		this(id, null, null);
	}
	
	public BaseBenchmarkRunner(int id, String name) {
		this(id, name, null);
	}
	
	public BaseBenchmarkRunner(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getName() {
		if (name != null) {
			return name;
		}
		else {
			return getClass().getName();
		}	
	}
	
	@Override 
	public String getDescription() {
		if (description != null) {
			return description;
		}
		else {
			return "Benchmark of " + getName();
		}	
	}
	
	protected void beforeRun() {
		
	}
	
	protected void doRun() {
		
	}

	protected void afterRun() {
		
	}
	
	protected void onError(Throwable error) {
		error.printStackTrace();
	}
	
	protected void onTerminate() {
		
	}

	@Benchmark
	@Override 
	public void run() {
		try {
			beforeRun();
			doRun();
			afterRun();
		}
		catch (Throwable t) {
			onError(t);
		}
		finally {
			onTerminate();
		}
	}

}
