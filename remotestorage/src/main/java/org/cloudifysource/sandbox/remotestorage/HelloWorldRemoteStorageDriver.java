package org.cloudifysource.sandbox.remotestorage;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.cloudifysource.dsl.cloud.Cloud;
import org.cloudifysource.esc.driver.provisioning.storage.StorageProvisioningDriver;
import org.cloudifysource.esc.driver.provisioning.storage.StorageProvisioningException;
import org.cloudifysource.esc.driver.provisioning.storage.VolumeDetails;

public class HelloWorldRemoteStorageDriver implements StorageProvisioningDriver {

	public String hello() {
		return HelloWorldRemoteStorageDriver.class.getName() + " Says Hello!";
	}

    @Override
    public void setConfig(Cloud cloud, String computeTemplateName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VolumeDetails createVolume(String templateName, String location, long duration, TimeUnit timeUnit) throws TimeoutException, StorageProvisioningException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void attachVolume(String volumeId, String device, String ip, long duration, TimeUnit timeUnit) throws TimeoutException, StorageProvisioningException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void detachVolume(String volumeId, String ip, long duration,
			TimeUnit timeUnit) throws TimeoutException,
			StorageProvisioningException {
		// TODO Auto-generated method stub
		
	}

	public void deleteVolume(String location, String volumeId, long duration,
			TimeUnit timeUnit) throws TimeoutException,
			StorageProvisioningException {
		// TODO Auto-generated method stub
		
	}

	public Set<VolumeDetails> listVolumes(String ip, long duration,
			TimeUnit timeUnit) throws TimeoutException,
			StorageProvisioningException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVolumeName(String volumeId)
			throws StorageProvisioningException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setComputeContext(Object context)
			throws StorageProvisioningException {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

}
