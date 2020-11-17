
package com.manoj.app.sampleproject.web.request;


public interface AbstractProjection {

    String getId();

    String getCreatedBy();

    String getUpdatedBy();

    // Date createdOn;

    // Date updatedOn;

    Boolean getIsDelete();

    Boolean getDeleteBy();

}
