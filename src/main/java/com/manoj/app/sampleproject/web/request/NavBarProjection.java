package com.manoj.app.sampleproject.web.request;

import java.util.List;

public interface NavBarProjection {

	String getName();
	
	String getUrl();
	
	String getIcon();
	
	List<NavBarResponse> getChildren();
}
