package com.zzk.integration.samples.endpoints;

public interface Activator<T> {
	
	public void handleMessage(T input);

}
