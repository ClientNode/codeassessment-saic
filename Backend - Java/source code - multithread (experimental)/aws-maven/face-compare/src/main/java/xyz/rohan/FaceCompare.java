package xyz.rohan;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Rohan More
 * @project face-compare
 */
public class FaceCompare {

    //Variables for thread pools, results, and aws services
    public static final ExecutorService taskList = Executors.newFixedThreadPool(5);
    public static final ArrayList<String> matched = new ArrayList<>();
    public static final List<Future> allFutures = new ArrayList<>();

    public static final Auth creds = new Auth("AKIAS2PKP64RMKKP2AVN", "0U4yjz8cyxRORDcwd/WnSWi9D+RAppfDDgTliY58", "saic-code-assessment-rohan");
    public static final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(creds.getCreds()))
            .build();
    public static final AmazonRekognition rekognition = AmazonRekognitionClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(creds.getCreds()))
            .build();
    /**
     * Creates and uses credentials for amazon s3 and rekognition client builders
     * Gets all images from an s3 bucket declared in the Auth object and stores them in a list
     * Loops through all the images in the bucket and calls a CompareFaceRequest when the source_img and target_img are not the same
     *
     * @return String that contains source_img and target_img names along with their similarity and confidence
     */
    public static String compareFaces() {
        ListObjectsV2Result result = s3.listObjectsV2(creds.getBucket_name());
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        for (S3ObjectSummary source_img : objects) {
            Image source = new Image()
                    .withS3Object(new S3Object()
                            .withBucket(creds.getBucket_name())
                            .withName(source_img.getKey()));

            //get the url of the current image in case you need it
            //String temp  = s3.getUrl(creds.getBucket_name(), source_img.getKey()).toExternalForm();
            for (S3ObjectSummary target_img : objects) {
                Image target = new Image()
                        .withS3Object(new S3Object()
                                .withBucket(creds.getBucket_name())
                                .withName(target_img.getKey()));
                if(!source.getS3Object().equals(target.getS3Object())) {
                    Future<String> future = taskList.submit(() -> task(source, target, source_img, target_img));
                    allFutures.add(future);
                }
            }
        }
        taskList.shutdown();
        for (Future<String> future : allFutures) {
            try {
                matched.add(future.get());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return matched.toString();
    }

    public static String task(Image source, Image target, S3ObjectSummary source_img, S3ObjectSummary target_img) {
        String res = "";
        //0F because we want all comparison results
        CompareFacesRequest compare = new CompareFacesRequest()
                .withSourceImage(source)
                .withTargetImage(target)
                .withSimilarityThreshold(0f);

        CompareFacesResult compareFacesResult = rekognition.compareFaces(compare);
        List<CompareFacesMatch> facesMatched = compareFacesResult.getFaceMatches();

        for (CompareFacesMatch match : facesMatched) {
            ComparedFace face = match.getFace();
            res = "{" +
                    "\"source_img\":\"" + source_img.getKey() +
                    "\",\"target_img\":" + target_img.getKey() +
                    ",\"similarity\":\"" + match.getSimilarity() +
                    "\",\"confidence\":" + face.getConfidence() +
                    "}";
        }
        return res;
    }

}
