package com.manoj.app.sampleproject.constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ScheduledFuture;

public interface AppConstants {
	
	public final HashMap<Object, ScheduledFuture<?>> scheduledTask =
	        new LinkedHashMap<>();
}
