package xyz.rohan;

import com.amazonaws.auth.BasicAWSCredentials;

/**
 * @author Rohan More
 * @project face-compare
 */
public class Auth {
    private final String accessKey;
    private final String secretKey;
    private final String bucket_name;
    private final BasicAWSCredentials creds;

    /**
     * Constructor for Auth
     * @param accessKey accessKey used for authentication
     * @param secretKey secretKey used for authentication
     * @param bucket_name bucket_name used to determine what S3 bucket is being used
     */
    public Auth(String accessKey, String secretKey, String bucket_name) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket_name = bucket_name;
        this.creds = new BasicAWSCredentials(accessKey, secretKey);
    }

    /**
     * Returns the accessKey for BasicAWSCredentials
     * @return String of the accessKey
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * Returns the secretKey for BasicAWSCredentials
     * @return String of the secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Returns the bucket_name used in AWS S3
     * @return String of the bucket name
     */
    public String getBucket_name() {
        return bucket_name;
    }

    /**
     * Returns the credentials for AWSCredentialsProvider
     * @return BasicAWSCredentials for the current credentials
     */
    public BasicAWSCredentials getCreds() {
        return creds;
    }
}
