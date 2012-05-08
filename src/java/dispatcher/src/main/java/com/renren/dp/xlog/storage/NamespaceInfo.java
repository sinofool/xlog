package com.renren.dp.xlog.storage;

public class NamespaceInfo {

	private String namespace=null;
	private int queueSize;
	private long successCount;
	private long failureCount;
	
	public NamespaceInfo(String namespace, int queueSize, long successCount,
			long failureCount) {
		super();
		this.namespace = namespace;
		this.queueSize = queueSize;
		this.successCount = successCount;
		this.failureCount = failureCount;
	}
	
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public int getQueueSize() {
		return queueSize;
	}
	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}
	public long getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(long successCount) {
		this.successCount = successCount;
	}
	public long getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(long failureCount) {
		this.failureCount = failureCount;
	}
}
