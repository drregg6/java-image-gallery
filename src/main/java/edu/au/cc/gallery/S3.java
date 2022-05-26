package edu.au.cc.gallery;

import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;
import software.amazon.awssdk.core.sync.RequestBody;

public class S3 {
	private S3Client client;
	private static final Region region = Region.US_EAST_1;

	// Connect to S3
	public void connect() {
		Region region = Region.US_EAST_1;
        	client  = S3Client.builder()
                	.region(region)
                	.build();
	}
	
	// Create a Bucket with a specified name
	public void createBucket(String bucketName) {
		try {
           		S3Waiter s3Waiter = client.waiter();
            		CreateBucketRequest bucketRequest = CreateBucketRequest
				.builder()
                    		.bucket(bucketName)
                    		.build();

            		client.createBucket(bucketRequest);
            		HeadBucketRequest bucketRequestWait = HeadBucketRequest
				.builder()
                    		.bucket(bucketName)
                    		.build();


            		// Wait until the bucket is created and print out the response
            		WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter
				.waitUntilBucketExists(bucketRequestWait);
            			waiterResponse.matched().response().ifPresent(System.out::println);
            			System.out.println(bucketName +" is ready");

        	} catch (S3Exception e) {
            		System.err.println(e.awsErrorDetails().errorMessage());
            		System.exit(1);
        	}
	}

	// Place an Object in the Bucket
	public void putObject(String bucketName, String key, String value) {
		PutObjectRequest por = PutObjectRequest
			.builder()
			.bucket(bucketName)
			.key(key)
			.build();

		client.putObject(por, RequestBody.fromString(value));
	}

	// Run the S3
	public static void demo() {
		String bucketName = "edu-au-cc-dzr0056-image-gallery";
		S3 s3 = new S3();
		s3.connect();
		// Bucket is already created
		// s3.createBucket(bucketName);
		s3.putObject(bucketName, "hello", "world");
	}
}
